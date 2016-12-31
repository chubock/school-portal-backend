package com.avin.schoolportal.repository;

import com.avin.schoolportal.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Yubar on 12/30/2016.
 */
public interface BookRepository extends JpaRepository<Book, Long> {
}
