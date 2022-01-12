package ru.sumbirsoft.chat.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(
            Permission.USER_READ
    )),
    MODERATOR(Set.of(
            Permission.USER_READ,
            Permission.USER_BAN,
            Permission.USER_UNBAN,
            Permission.MESSAGE_DELETE
    )),
    ADMIN(Set.of(
            Permission.USER_READ,
            Permission.USER_WRITE,
            Permission.USER_BAN,
            Permission.USER_UNBAN,
            Permission.USER_APPOINT_MODER,
            Permission.USER_DELETE_MODER,
            Permission.MESSAGE_DELETE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}