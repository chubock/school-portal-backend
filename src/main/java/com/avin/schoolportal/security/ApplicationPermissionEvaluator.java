package com.avin.schoolportal.security;

import com.avin.schoolportal.domain.User;
import com.avin.schoolportal.dto.AbstractDTO;
import com.avin.schoolportal.repository.UserRepository;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Yubar on 10/23/2016.
 */

@Component
public class ApplicationPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ApplicationContext context;

    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {

        if (AbstractDTO.class.isAssignableFrom(target.getClass()))
            target = ((AbstractDTO)target).convert();

        AccessEvaluator accessEvaluator;

        if (target instanceof HibernateProxy)
            accessEvaluator = getAccessEvaluator(target.getClass().getSuperclass().getSimpleName());
        else
            accessEvaluator = getAccessEvaluator(target.getClass().getSimpleName());

        if (accessEvaluator == null)
            return false;

        User user = null;
        if (! authentication.getPrincipal().equals("anonymousUser"))
            user = userRepository.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());

        return accessEvaluator.hasAccess(user, target, (String) permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable id, String type, Object permission) {

        AccessEvaluator accessEvaluator = getAccessEvaluator(type);

        if (accessEvaluator == null)
            return false;

        User user = null;
        if (! authentication.getPrincipal().equals("anonymousUser"))
            user = userRepository.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());

        return accessEvaluator.hasAccess(user, id, (String) permission);
    }

    private AccessEvaluator getAccessEvaluator(String type) {
        return (AccessEvaluator) context.getBean(getBeanName(type));
    }

    private String getBeanName(String type) {
        String beanName;
        if (Character.isUpperCase(type.charAt(0))) {
            beanName = Character.toLowerCase(type.charAt(0)) + type.substring(1) + "AccessEvaluatorBean";
        } else {
            beanName = type + "AccessEvaluatorBean";
        }
        return beanName;
    }
}
