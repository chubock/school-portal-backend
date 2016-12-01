package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Employee;
import com.avin.schoolportal.validationgroups.EmployeeRegistration;
import com.avin.schoolportal.validationgroups.SchoolRegistration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yubar on 10/23/2016.
 */

public abstract class EmployeeDTO extends SchoolUserDTO {

    public EmployeeDTO() {
    }

    public EmployeeDTO(Employee employee) {
        super(employee);
    }

    @Override
    public abstract Employee convert();
}
