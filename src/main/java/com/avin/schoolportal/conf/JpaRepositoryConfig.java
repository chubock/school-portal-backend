package com.avin.schoolportal.conf;

import com.avin.schoolportal.repository.custom.RepositoryWithRefreshImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by Yubar on 11/24/2016.
 */

@Configuration
@EnableJpaRepositories(basePackages = "com.avin.schoolportal.repository", repositoryBaseClass = RepositoryWithRefreshImpl.class)
public class JpaRepositoryConfig {
}
