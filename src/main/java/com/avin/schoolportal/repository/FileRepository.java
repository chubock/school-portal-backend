package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.File;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Yubar on 12/11/2016.
 */
public interface FileRepository extends CrudRepository<File, Long> {
}
