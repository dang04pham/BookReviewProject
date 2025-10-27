package com.bookrev.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "authors")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_img")
    private String authorImgUrl;

    @Column(name = "author_bio")
    private String authorBio;

    @Column(name = "author_dob")
    private String authorDOB;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}
