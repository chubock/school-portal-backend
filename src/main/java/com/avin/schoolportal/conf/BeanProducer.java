package com.avin.schoolportal.conf;

import com.avin.schoolportal.security.ApplicationPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

/**
 * Created by Yubar on 10/23/2016.
 */

@Component
public class BeanProducer {

    @Bean
    public ApplicationPermissionEvaluator permissionEvaluator() {
        return new ApplicationPermissionEvaluator();
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/schoolportal");
        driverManagerDataSource.setUsername("school-portal");
        driverManagerDataSource.setPassword("school-portal");
        return driverManagerDataSource;
    }

}
