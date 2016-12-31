package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 11/30/2016.
 */
public class MancipleSpecification extends SchoolUserSpecification<Manciple> {

    public MancipleSpecification(Map<String, String> params, School school) {
        super(params, school);
    }
}
