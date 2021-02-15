package com.joakimatef.demo.service.security.permission;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation created to delete authority
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('user.delete')")
public @interface UserDeletePermission {
}
