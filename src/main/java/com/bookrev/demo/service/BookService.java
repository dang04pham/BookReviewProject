package com.bookrev.demo.service;

import com.bookrev.demo.dto.BookDto;
import com.bookrev.demo.payload.BookResponse;

public interface BookService {
    BookDto addBook(BookDto bookDto);
    BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    BookDto updateBook(Long bookId, BookDto bookDto);
    BookDto deleteBook(Long bookId);
}
