package com.bookrev.demo.service;

import com.bookrev.demo.dto.AuthorDto;
import com.bookrev.demo.payload.AuthorResponse;

public interface AuthorService {
    AuthorDto addAuthor(AuthorDto authorDto);
    AuthorResponse getAllAuthors(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    AuthorDto updateAuthor(Long authorId, AuthorDto authorDto);
    AuthorDto deleteAuthor(Long authorId);
}
