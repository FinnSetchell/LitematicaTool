package com.finndog.litematicatool;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Points Litematica's "tool item" setting at our stick once, so the mod works the moment it is
 * installed.
 *
 * <p>Out of the box Litematica ships {@code toolItemEnabled = false} and {@code toolItem =
 * minecraft:stick}, which means our stick does nothing at all until the player finds and changes two
 * separate settings. In a modpack nobody does, and the mod looks broken. This sets both for them.
 *
 * <p><b>It happens once, ever, and never again.</b> That is what the marker file is for. Litematica's
 * own "has this been changed" test only compares the value against its default, which cannot tell a
 * setting nobody has touched from one a player deliberately set back to the vanilla stick: without a
 * marker we would quietly undo that second player's choice on their next launch. So the first time we
 * reach a decision we write the marker, and from then on we never look at these settings again.
 *
 * <p>A deliberate choice made BEFORE we ever ran is safe too: if the tool item is already something
 * other than the default we change nothing, and still write the marker, so that choice is final.
 *
 * <p>Within the one session that does the work we keep retrying until the value sticks. Litematica
 * loads its config from malilib's deferred initialisation handlers rather than from its own mod
 * initialiser, so there is no point during mod loading that is reliably after it, and a single early
 * write could be silently undone by the config load that follows. The marker is read once at the
 * start of the session precisely so that writing it does not stop those retries.
 *
 * <p>Everything here is reflection on purpose. Litematica is an optional mod with no published API
 * and no Maven artifact, so the mod must build and run whether or not it is present. Nothing in this
 * class touches a Minecraft or loader class, so it is safe to call from any loader on either side.
 */
public final class LitematicaToolItemSetup {
    private static final String CONFIGS_GENERIC = "fi.dy.masa.litematica.config.Configs$Generic";
    private static final String TOOL_ITEM = Constants.MOD_ID + ":" + LitematicaToolCommon.ITEM_NAME;

    private static final String MARKER_TEXT =
            "LitematicaTool has set Litematica's tool item once, and will not touch that setting again.\n"
            + "Delete this file to let it set the tool item up afresh.\n";

    /**
     * How many attempts before giving up, in client ticks. Twenty seconds is far longer than the
     * config can take to load, and stops a Litematica that keeps resetting the value from turning
     * this into a permanent per-tick cost.
     */
    private static final int MAX_ATTEMPTS = 20 * 20;

    private static Path marker;
    /** Whether this ran in some earlier session. Read once, before we write the marker ourselves. */
    private static Boolean ranBefore;
    private static boolean settled;
    private static int attempts;

    private LitematicaToolItemSetup() {}

    /**
     * Where to remember that this has already run. Called by the loader, which is the only part that
     * knows the config directory.
     */
    public static void rememberIn(Path markerFile) {
        marker = markerFile;
    }

    /**
     * Applies the settings if this has never run before. Safe and cheap to call every tick: once
     * there is nothing left to do it latches and returns on a boolean test.
     */
    public static void apply() {
        if (settled) {
            return;
        }

        if (ranBefore == null) {
            ranBefore = marker != null && Files.exists(marker);
        }
        if (ranBefore) {
            settled = true;
            return;
        }

        if (++attempts > MAX_ATTEMPTS) {
            settled = true;
            Constants.LOG.warn("Gave up setting Litematica's tool item; set it to {} by hand if the stick does nothing", TOOL_ITEM);
            return;
        }

        try {
            attempt();
        } catch (ClassNotFoundException absent) {
            // Litematica is not installed. Nothing to configure, and no marker: it may be installed
            // later, and we should still set it up the first time it is.
            settled = true;
        } catch (Throwable failed) {
            // A Litematica version that moved or renamed these settings. Our stick still works for
            // anyone who sets the tool item by hand, so this is not worth more than a warning.
            settled = true;
            Constants.LOG.warn("Could not set Litematica's tool item automatically; set it to {} by hand if the stick does nothing", TOOL_ITEM, failed);
        }
    }

    private static void attempt() throws Exception {
        Class<?> generic = Class.forName(CONFIGS_GENERIC);
        Object toolItem = generic.getField("TOOL_ITEM").get(null);
        Object toolItemEnabled = generic.getField("TOOL_ITEM_ENABLED").get(null);

        if (isModified(toolItem)) {
            // The player already chose a tool item, or our own write from earlier this session has
            // stuck. Either way the decision is made.
            settled = true;
            rememberWeHaveRun();
            return;
        }

        toolItem.getClass().getMethod("setValueFromString", String.class).invoke(toolItem, TOOL_ITEM);

        if (!isModified(toolItemEnabled)) {
            toolItemEnabled.getClass().getMethod("setBooleanValue", boolean.class).invoke(toolItemEnabled, true);
        }

        rememberWeHaveRun();
        Constants.LOG.info("Set Litematica's tool item to {}", TOOL_ITEM);
    }

    /** True once this setting holds something other than Litematica's default. */
    private static boolean isModified(Object config) throws Exception {
        return (Boolean) config.getClass().getMethod("isModified").invoke(config);
    }

    private static void rememberWeHaveRun() {
        if (marker == null) {
            return;
        }
        try {
            Files.createDirectories(marker.getParent());
            Files.write(marker, MARKER_TEXT.getBytes(StandardCharsets.UTF_8));
        } catch (Exception couldNotWrite) {
            // A read-only or unwritable config directory. The settings were still applied; the only
            // cost is that we would look again next launch, so this is not worth more than a warning.
            Constants.LOG.warn("Could not record that Litematica's tool item has been set up ({})", marker, couldNotWrite);
        }
    }
}
