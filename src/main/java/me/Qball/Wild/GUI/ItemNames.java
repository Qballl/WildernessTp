package me.Qball.Wild.GUI;

public enum ItemNames {
    BACK("Back"), CLOSE("Close"), ADD_A_POTION_OR_WORLD("Add"), MESSAGES("Messages"), SET("Set"), HOOKS("Hooks"), POST_COMMANDS("PostCommands"),
    BLOCKED_COMMANDS("BlockedCommands"), BIOME_BLACKLIST("Blacklisted_Biomes"), CANCEL_MESSAGE("CancelMsg"), WORLD_MESSAGE("WorldMsg"),
    USED_COMMAND_MESSAGE("UsedCmd"), REFUND_MESSAGE("RefundMsg"), WAIT_WARMUP_MESSAGE("WaitMsg"), WAIT("Wait"), SOUNDS("Sound"),
    WORLD("Worlds"), POTION("Potions"), COOLDOWN_MESSAGE("Cooldownmsg"), NO_PERM("NoPerm"), No_BREAK("NoBreak"), COSTMSG("Costmsg"),
    NOSUIT("No Suitable Location"), TELEPORT("Teleport"), DISTANCE("Distance"), DO_RETRY("Retry"), RETRIES("Retries"), COST("Cost"),
    COOLDOWN("Cooldown"), MINZ("MinZ"), MAXZ("MaxZ"), MINX("MinX"), MAXX("MaxX");

    private String set;

    ItemNames(String set) {
        this.set = set;
    }

    public String getSet() {
        return set;
    }
}
