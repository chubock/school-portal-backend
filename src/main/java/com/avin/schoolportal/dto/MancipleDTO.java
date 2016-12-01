package com.avin.schoolportal.dto;

import com.avin.schoolportal.domain.Manciple;
import com.avin.schoolportal.domain.SchoolUser;

/**
 * Created by Yubar on 11/30/2016.
 */
public class MancipleDTO extends EmployeeDTO {

    public MancipleDTO() {
    }

    public MancipleDTO(Manciple manciple) {
        super(manciple);
    }

    @Override
    public Manciple convert() {
        Manciple manciple = new Manciple();
        super.convert(manciple);
        return manciple;
    }
}
