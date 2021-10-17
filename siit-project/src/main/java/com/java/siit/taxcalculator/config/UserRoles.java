package com.java.siit.taxcalculator.config;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.java.siit.taxcalculator.config.UserPermission.*;

public enum UserRoles {
    ADMIN(Sets.newHashSet(USER_READ, USER_WRITE)),
    USER(Sets.newHashSet(CALCULUS_READ,CALCULUS_WRITE));

    private final Set<UserPermission> permissions;

    UserRoles(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}
