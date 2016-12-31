package com.avin.schoolportal.conf;

import com.avin.schoolportal.repository.custom.ExtendedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Yubar on 11/24/2016.
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.avin.schoolportal.repository", repositoryBaseClass = ExtendedRepositoryImpl.class)
public class JpaRepositoryConfig {
}
