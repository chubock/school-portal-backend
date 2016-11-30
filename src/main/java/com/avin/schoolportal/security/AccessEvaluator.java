package com.avin.schoolportal.security;

import com.avin.schoolportal.domain.User;

import java.io.Serializable;

/**
 * Created by Yubar on 10/24/2016.
 */
public interface AccessEvaluator<T> {
    public boolean hasAccess(User user, T t, String permission);
    public boolean hasAccess(User user, Serializable id, String permission);
}
