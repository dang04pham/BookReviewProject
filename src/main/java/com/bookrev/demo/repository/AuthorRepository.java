package com.bookrev.demo.repository;

import com.bookrev.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByAuthorId(Long authorId);
}
