package me.Qball.Wild.Utils;

public enum UsageMode {

    COMMAND_ONLY("wild.wildtp.command.except"),
    SIGN_ONLY("wild.wildtp.sign.except"),
    BOTH;

    private final String permissionExcempt;

    UsageMode(String permissionExcempt) {
        this.permissionExcempt = permissionExcempt;
    }

    UsageMode() {
        this(null);
    }

    public String getPermissionExcempt() {
        return permissionExcempt;
    }

    public UsageMode getMode(String mode){
        try{
            return UsageMode.valueOf(mode);
        }catch (IllegalArgumentException e){
            return BOTH;
        }
    }
}
