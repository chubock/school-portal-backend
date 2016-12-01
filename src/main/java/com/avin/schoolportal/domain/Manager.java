package com.avin.schoolportal.domain;

import javax.persistence.Entity;

/**
 * Created by Yubar on 11/30/2016.
 */

@Entity(name = "Manager")
public class Manager extends SchoolUser {
    @Override
    public String getUsernamePrefix() {
        return null;
    }
}
