package com.avin.schoolportal.specification;

import com.avin.schoolportal.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Yubar on 12/4/2016.
 */
public class ViolationSpecification extends AbstractSpecification<Violation> {

    private final Manciple manciple;
    private final SimpleDateFormat dateFormatter;

    public ViolationSpecification(Map<String, String> params, Manciple manciple, String dateShortFormat) {
        super(params, manciple.getSchool());
        this.manciple = manciple;
        dateFormatter = new SimpleDateFormat(dateShortFormat);
    }

    @Override
    protected void fetchProperties(Root<Violation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        root.fetch(Violation_.manciple);
    }

    @Override
    protected List<Predicate> additionalPredicates(Root<Violation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get(Violation_.manciple), manciple));
        return predicates;
    }

    @Override
    Predicate createPredicate(Root<Violation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, String key) {
        String value = params.get(key);
        switch (key) {
            case "filter.type":
                return criteriaBuilder.equal(root.get(Violation_.type), ViolationType.valueOf(value));
            case "filter.date":
                try {
                    return criteriaBuilder.equal(root.get(Violation_.date), dateFormatter.parse(value));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            case "filter.comment":
                return criteriaBuilder.like(root.get(Violation_.comment), "%" + value + "%");
            default:
                return null;
        }
    }

}
