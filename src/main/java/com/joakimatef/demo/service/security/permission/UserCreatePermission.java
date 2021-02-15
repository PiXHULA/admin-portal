package com.joakimatef.demo.service.security.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation created to create authority
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('user.create')")
public @interface UserCreatePermission {
}
