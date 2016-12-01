package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Manager;
import com.avin.schoolportal.domain.SchoolUser;

/**
 * Created by Yubar on 11/30/2016.
 */
public class ManagerDTO extends SchoolUserDTO {

    public ManagerDTO() {
    }

    public ManagerDTO(Manager manager) {
        super(manager);
    }

    @Override
    public Manager convert() {
        Manager manager = new Manager();
        super.convert(manager);
        return manager;
    }
}
