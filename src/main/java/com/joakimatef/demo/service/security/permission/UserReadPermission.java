package com.joakimatef.demo.service.security.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('user.read','user.admin.read')")
public @interface UserReadPermission {
}
