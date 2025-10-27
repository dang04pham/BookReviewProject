package com.bookrev.demo.service;

import com.bookrev.demo.dto.AuthorDto;
import com.bookrev.demo.exception.APIException;
import com.bookrev.demo.model.Author;
import com.bookrev.demo.payload.AuthorResponse;
import com.bookrev.demo.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        return null;
    }

    @Override
    public AuthorResponse getAllAuthors(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
        return null;
    }

    @Override
    public AuthorDto deleteAuthor(Long authorId) {
        return null;
    }
}
