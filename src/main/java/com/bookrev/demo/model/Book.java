package com.bookrev.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    private Long bookId;

    private String bookName;
    private String bookImgUrl;


}
