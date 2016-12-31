package com.avin.schoolportal.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Created by Yubar on 11/30/2016.
 */

@Entity(name = "Manager")
public class Manager extends SchoolUser {
    @Override
    @Transient
    public String getUsernamePrefix() {
        return null;
    }
}
