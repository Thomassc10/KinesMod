package com.kines.kinesmod.config;

import com.kines.kinesmod.KinesMod;
import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import net.minecraft.client.Minecraft;

import java.io.File;

public class Config extends Vigilant {

    /*
    Miscellaneous
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Toggle Sprint",
            description = "Simple toggle sprint function.",
            category = "Miscellaneous",
            subcategory = "General"
    )
    public static boolean toggleSprint = true;

    @Property(
            type = PropertyType.TEXT,
            name = "Toggle sprint text",
            description = "Changes the text shown.",
            category = "Miscellaneous",
            subcategory = "General"
    )
    public static String sprintingText = "[Sprinting (toggled)]";

    @Property(
            type = PropertyType.SWITCH,
            name = "Skip Front Camera",
            description = "Skips the front (selfie) camera on F5.",
            category = "Miscellaneous",
            subcategory = "General"
    )
    public static boolean skipFrontCamera = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Filter Messages",
            description = "Removes some chat messages, like watchdog warning, profile messages, fire sales and etc.",
            category = "Miscellaneous",
            subcategory = "Chat"
    )
    public static boolean filterMessages = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Remove scoreboard scores",
            description = "Removes the red scores on scoreboards lines.",
            category = "Miscellaneous",
            subcategory = "Quality of Life"
    )
    public static boolean removeScores = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Remove falling blocks",
            description = "Stops falling blocks from spawning (idk, I lag when a dragon spawns).",
            category = "Miscellaneous",
            subcategory = "Quality of Life"
    )
    public static boolean removeFallingBlocks = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "God Pot Expired",
            description = "Warns you when your god pot is about to expire.",
            category = "Miscellaneous",
            subcategory = "Quality of Life"
    )
    public static boolean godPotExpired = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Leave",
            description = "Automatically sends you to the lobby when you get kicked while joining a server. \n&cThis is a chat macro!",
            category = "Miscellaneous",
            subcategory = "General"
    )
    public static boolean autoLobby = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Hide Dead Entities",
            description = "Hides dead entities tags/armor stand. ",
            category = "Miscellaneous",
            subcategory = "Quality of Life"
    )
    public static boolean hideDeadEntity = false;

    /*
    Overlays
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Remove health",
            description = "Removes the health/hearts overlay above your hot bar.",
            category = "Overlays"
    )
    public static boolean healthOverlay = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Remove food bar",
            description = "Removes the food overlay above your hot bar.",
            category = "Overlays"
    )
    public static boolean foodOverlay = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Remove armor",
            description = "Removes the armor overlay above hearts.",
            category = "Overlays"
    )
    public static boolean armorOverlay = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Remove crosshair",
            description = "Removes the crosshair when on third person.",
            category = "Overlays"
    )
    public static boolean crosshairOverlay = false;

    /*
    Mining
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "CH Wypoints",
            description = "Just some waypoints for the crystal hollows.",
            category = "Mining",
            subcategory = "Waypoints"
    )
    public static boolean chWaypoints = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Mining Speed Boost Ready",
            description = "Shows a title when your mining speed boost is ready.",
            category = "Mining",
            subcategory = "General"
    )
    public static boolean miningSpeedBoost = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Only show while in mines",
            description = "Only shows the mining speed boost warning while in the Dwarven Mines or Crystal Hollows.",
            category = "Mining",
            subcategory = "General"
    )
    public static boolean showOnlyInsideMines = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Hide Sky Mall",
            description = "Hides Sky Mall's new buff message.",
            category = "Mining",
            subcategory = "Sky Mall"
    )
    public static boolean hideSkyMallMessages = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Sky Mall Buff",
            description = "Displays the current Sky Mall's buff on your screen.",
            category = "Mining",
            subcategory = "Sky Mall"
    )
    public static boolean displaySkyMall = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Shorter Buff Names",
            description = "Shortens the Sky Mall's buff name.",
            category = "Mining",
            subcategory = "Sky Mall"
    )
    public static boolean shorterBuff = false;

    /*
    Crimson Isle
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Lord Jawbus Spawned",
            description = "Shows a title when someone fishes up Lord Jawbus.",
            category = "Crimson Isle",
            subcategory = "Fishing"
    )
    public static boolean lordJawbus = false;

    @Property(
            type = PropertyType.SELECTOR,
            name = "Alert Vanquisher Spawn",
            description = "Send coords in chat -> /patcher sendcoords. (Doesn't work if \"Auto Invite&Warp\" is not empty)",
            category = "Crimson Isle",
            subcategory = "Vanquisher",
            options = {"Off", "Party", "All"}
    )
    public static int alertVanquisher = 0;

    @Property(
            type = PropertyType.TEXT,
            name = "Auto Invite&Warp",
            description = "Automatically invite players to your party and warps them to your lobby. " +
                    "Wright the IGNs of the people you want to invite to your party. Leave empty otherwise. (Ex: Minikloon HsFearless ZachPlaysAN)",
            category = "Crimson Isle",
            subcategory = "Vanquisher"
    )
    public static String partyMembers = "";

    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Accept",
            description = "Automatically accepts the party invite from your vanquisher party. It takes the parameters from \"Auto Invite&Warp\". " +
                    "Other people in your group should probably have this too, to prevent the warp from breaking.",
            category = "Crimson Isle",
            subcategory = "Vanquisher"
    )
    public static boolean acceptParty = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Ghast Spawn",
            description = "Alerts you when ghasts start to spawn in the Crimson Isles. (from 9pm to 5am)",
            category = "Crimson Isle",
            subcategory = "General"
    )
    public static boolean alertGhast = false;

    /*
    Kuudra
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Waypoint for Supplies/Fuels",
            description = "Shows a beacon beam for the supplies/fuels locations.",
            category = "Kuudra",
            subcategory = "Supplies"
    )
    public static boolean showSuppliesLocations = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Highlight Safe Spots",
            description = "Draw a box around safe spots for supplies and inside Kuudra.",
            category = "Kuudra",
            subcategory = "General"
    )
    public static boolean showSafeSpots = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Hide Name Tags",
            description = "Stop rendering name tags from mobs during kuudra. (probably good for performance)",
            category = "Kuudra",
            subcategory = "General"
    )
    public static boolean hideKuudraNameTags = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Unfinished Supply Piles",
            description = "Shows a beacon bean on supply piles that are unfinished. (SHIFT + PUNCH)",
            category = "Kuudra",
            subcategory = "Supplies"
    )
    public static boolean unfinishedSupplyPiles = false;

    /*
    The End
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Stop Enderman Teleportation",
            description = "Stop enderman from teleporting when hitting them.",
            category = "End"
    )
    public static boolean stopEndermanTeleport = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Endstone Protector Spawned",
            description = "Shows a title when the Endstone Protector is about to spawn.",
            category = "End"
    )
    public static boolean endstoneProtector = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Move Enderman Tag Downwards",
            description = "Moves enderman tag to see it better.",
            category = "End"
    )
    public static boolean endermanTag = false;

    /*
    Events
     */

    // GREAT SPOOK
    @Property(
            type = PropertyType.SWITCH,
            name = "Primal Fear Spawned",
            description = "Shows a title when a Primal Fear spawns.",
            category = "Events",
            subcategory = "Great Spook"
    )
    public static boolean primalFearAlert = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Solve Math Teacher",
            description = "Solves the equation for you.",
            category = "Events",
            subcategory = "Great Spook"
    )
    public static boolean calculateMath = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Copy Result to Clipboard",
            description = "Copies the result from the math equation to clipboard.",
            category = "Events",
            subcategory = "Great Spook"
    )
    public static boolean copyMathToClipboard = true;

    @Property(
            type = PropertyType.SWITCH,
            name = "Primal Fear Timer",
            description = "Shows a timer for when you can spawn a Primal Fear.",
            category = "Events",
            subcategory = "Great Spook"
    )
    public static boolean primalFearTimer = false;

    // DIANA
    @Property(
            type = PropertyType.SELECTOR,
            name = "Alert Inquisitor",
            description = "Sends coords in chat when you dig up an Inquisitor. (/patcher sendcoords)",
            category = "Events",
            subcategory = "Diana",
            options = {"Off", "Party", "All"}
    )
    public static int alertInquisitor = 0;

    /*
    Slayers
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Highlight Slayer Minibosses",
            description = "Draw a box around slayer minibosses.",
            category = "Slayers",
            subcategory = "General"
    )
    public static boolean highlightSlayerMini = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Voidgloom Lazer Timer",
            description = "Shows a timer for the lazer phase of the boss.",
            category = "Slayers",
            subcategory = "Voidgloom Seraph"
    )
    public static boolean lazerPhaseTimer = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Highlight Eye Things",
            description = "Draws a box around the eye damaging things from the boss.",
            category = "Slayers",
            subcategory = "Voidgloom Seraph"
    )
    public static boolean highlightEyeThings = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Re-heated Gummy Polar Bear Timer",
            description = "Shows the time left in your Re-heated Gummy Polar Bear.",
            category = "Slayers",
            subcategory = "Inferno Demonlord"
    )
    public static boolean gummyTimer = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Wisp's Ice-Flavored Water Timer",
            description = "Shows the time left in your Wisp's Ice-Flavored Water potion. (Only works if you splash yourself)",
            category = "Slayers",
            subcategory = "Inferno Demonlord"
    )
    public static boolean wispTimer = false;

    /*
    Garden
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Highlight Pests (Scuffed)",
            description = "Draw a box around pests.",
            category = "Garden",
            subcategory = "Pests"
    )
    public static boolean highlightPest = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Milestone Level",
            description = "Shows the milestone level as stack size while in the milestones menu.",
            category = "Garden",
            subcategory = "General"
    )
    public static boolean milestonesLevel = false;

    /*@Property(
            type = PropertyType.SWITCH,
            name = "Line to Pest Location",
            description = "Draw a line towards the pest location.",
            category = "Garden",
            subcategory = "Pests"
    )
    public static boolean drawLineToPest = true;*/

    /*
    HUD
     */

    @Property(
            type = PropertyType.SWITCH,
            name = "Show server TPS",
            description = "Tries to estimate the current server's TPS. (20 TPS = no lag)",
            category = "HUD",
            subcategory = "Performance"
    )
    public static boolean showTps = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show client FPS",
            description = "Displays the clients FPS.",
            category = "HUD",
            subcategory = "Performance"
    )
    public static boolean showFps = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Location Coords",
            description = "Shows the player's current coordinates.",
            category = "HUD",
            subcategory = "General"
    )
    public static boolean showCoords = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Y Coord",
            description = "Shows the Y coordinate too, instead of just X and Z.",
            category = "HUD",
            subcategory = "General"
    )
    public static boolean showYCoord = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Lobby Date",
            description = "Shows the current lobby's date.",
            category = "HUD",
            subcategory = "General"
    )
    public static boolean showLobbyDate = false;

    /*
    Bestiary
     */

    @Property(
            type = PropertyType.TEXT,
            name = "Highlight Mob",
            description = "Highlights a mob type based on the input given. (Ex: Zombie, IronGolem, Spider...)",
            category = "Bestiary",
            subcategory = "Highlight Mob"
    )
    public static String highlightMob = "";

    @Property(
            type = PropertyType.TEXT,
            name = "Highlight Armor Stand",
            description = "Highlights the Armor Stand above the mob that contains the name given.",
            category = "Bestiary",
            subcategory = "Highlight Armor Stand"
    )
    public static String highlightStand = "";

    @Property(
            type = PropertyType.SWITCH,
            name = "Highlight Matcho",
            description = "Draw a box around the Matcho mob in the Crimson Isles.",
            category = "Bestiary",
            subcategory = "General"
    )
    public static boolean highlightMatcho = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Highlight Broodmother ",
            description = "Draw a box around the Broodmother in the Spider's Den.",
            category = "Bestiary",
            subcategory = "General"
    )
    public static boolean highlightBroodmother = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Broodmother Alive",
            description = "Alerts you if Broodmother is alive based on your tab list.",
            category = "Bestiary",
            subcategory = "General"
    )
    public static boolean broodmotherAlive = false;

    /*
    Gui
     */

    @Property(
            type = PropertyType.BUTTON,
            name = "Edit Gui Locations",
            description = "Opens the gui to edit the features' locations. (Or just use /kines edit)",
            category = "Gui",
            subcategory = "Edit Gui",
            placeholder = "Edit"
    )
    private void button() {
        Minecraft.getMinecraft().thePlayer.closeScreen();
        KinesMod.editGui = 1;
    }

    @Property(
            type = PropertyType.SWITCH,
            name = "Skill Level",
            description = "Shows the skill level as stack size while in the skills menu.",
            category = "Gui",
            subcategory = "General"
    )
    public static boolean skillLevel = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Tuning Points",
            description = "Shows the amount of point in each tuning.",
            category = "Gui",
            subcategory = "General"
    )
    public static boolean tuningPoints = false;

    public Config() {
        super(new File("./config/kines.toml"), "KinesMod (" + KinesMod.VERSION + ")");
        initialize();
        addDependency("sprintingText", "toggleSprint");
        addDependency("showOnlyInsideMines", "miningSpeedBoost");
        addDependency("copyMathToClipboard", "calculateMath");
        addDependency("showYCoord", "showCoords");
        addDependency("shorterBuff", "displaySkyMall");
    }
}
