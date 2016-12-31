package com.avin.schoolportal.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Created by Yubar on 11/30/2016.
 */

@Entity(name = "Manciple")
public class Manciple extends Employee {
    @Override
    @Transient
    public String getUsernamePrefix() {
        return "0";
    }
}
