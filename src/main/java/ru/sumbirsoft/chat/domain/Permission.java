package ru.sumbirsoft.chat.domain;

/**
 * For more flexibility use permissions instead of roles.
 */

public enum Permission {
    USER_READ("user:read"), USER_WRITE("user:write"),

    USER_BAN("user:ban"), //block user
    USER_UNBAN("user:unban"), //unblock user
    USER_APPOINT_MODER("user:appoint_moder"), //appoint new moderator
    USER_DELETE_MODER("user:delete_moder"), //disappoint moderator

    MESSAGE_DELETE("message:delete"),

    ROOM_DELETE_USER("room:delete_user");

    private final String permission;

    public String getPermission() {
        return permission;
    }

    Permission(String permission) {
        this.permission = permission;
    }
}