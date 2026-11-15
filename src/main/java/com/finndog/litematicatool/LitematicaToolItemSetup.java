package com.finndog.litematicatool;

/**
 * Points Litematica's "tool item" setting at our stick, so the mod works the moment it is installed.
 *
 * <p>Out of the box Litematica ships {@code toolItemEnabled = false} and {@code toolItem =
 * minecraft:stick}, which means our stick does nothing at all until the player finds and changes two
 * separate settings. In a modpack nobody does, and the mod looks broken. This sets both for them.
 *
 * <p>A deliberate choice is never overwritten. Each setting is only touched while it still holds
 * Litematica's own default, so a player who picked a different tool item keeps it.
 *
 * <p>That same test is what makes this safe to run from a tick event rather than once at startup.
 * Litematica loads its config from malilib's deferred initialisation handlers, not from its own mod
 * initialiser, so there is no point during mod loading that is reliably after it; and a single early
 * attempt would be silently undone if the config loaded afterwards. So this keeps trying until the
 * setting is no longer at its default, which is true both when the player had already chosen
 * something and when our own write has stuck, and stops there.
 *
 * <p>Everything here is reflection on purpose. Litematica is an optional mod with no published API
 * and no Maven artifact, so the mod must build and run whether or not it is present. Nothing in this
 * class touches a Minecraft class, so it is safe to call from any loader on either side.
 */
public final class LitematicaToolItemSetup {
    private static final String CONFIGS_GENERIC = "fi.dy.masa.litematica.config.Configs$Generic";
    private static final String TOOL_ITEM = Constants.MOD_ID + ":" + LitematicaToolCommon.ITEM_NAME;

    /**
     * How many attempts before giving up, in client ticks. Twenty seconds is far longer than the
     * config can take to load, and stops a Litematica that keeps resetting the value from turning
     * this into a permanent per-tick cost.
     */
    private static final int MAX_ATTEMPTS = 20 * 20;

    private static boolean settled;
    private static int attempts;

    private LitematicaToolItemSetup() {}

    /**
     * Applies the settings if they are still at Litematica's defaults. Safe and cheap to call every
     * tick: once there is nothing left to do it latches and returns on a boolean test.
     */
    public static void apply() {
        if (settled) {
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
            // Litematica is not installed. Nothing to configure, and nothing worth logging.
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
            // Either the player chose this, or it is the value we wrote on an earlier tick. Both
            // mean there is nothing left for us to do.
            settled = true;
            return;
        }

        toolItem.getClass().getMethod("setValueFromString", String.class).invoke(toolItem, TOOL_ITEM);

        if (!isModified(toolItemEnabled)) {
            toolItemEnabled.getClass().getMethod("setBooleanValue", boolean.class).invoke(toolItemEnabled, true);
        }

        Constants.LOG.info("Set Litematica's tool item to {}", TOOL_ITEM);
    }

    /** True once this setting holds something other than Litematica's default. */
    private static boolean isModified(Object config) throws Exception {
        return (Boolean) config.getClass().getMethod("isModified").invoke(config);
    }
}
