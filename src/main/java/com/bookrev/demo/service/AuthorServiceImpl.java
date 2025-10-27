package com.bookrev.demo.service;

import com.bookrev.demo.dto.AuthorDto;
import com.bookrev.demo.exception.APIException;
import com.bookrev.demo.exception.ResourceNotFoundException;
import com.bookrev.demo.model.Author;
import com.bookrev.demo.payload.AuthorResponse;
import com.bookrev.demo.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Author author = modelMapper.map(authorDto, Author.class);
        Author authorFromDb = authorRepository.findByAuthorName(author.getAuthorName());
        if(authorFromDb != null){
            throw new APIException("Author with name " + author.getAuthorName() + " already exists");
        }
        Author savedAuthor = authorRepository.save(author);
        return modelMapper.map(savedAuthor, AuthorDto.class);
    }

    @Override
    public AuthorResponse getAllAuthors(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // Determine the sort order (ascending or descending)
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create a pageable object
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        // Fetch the paginated list of genres
        Page<Author> authorPage = authorRepository.findAll(pageDetails);

        // Get the list of genre content
        List<Author> authors = authorPage.getContent();
        if (authors.isEmpty()) {
            throw new APIException("No genres found.");
        }

        // Map the Genre list to GenreDto list
        List<AuthorDto> authorDtos = authors.stream()
                .map(author -> modelMapper.map(author, AuthorDto.class))
                .toList();

        // Build the GenreResponse object
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setContent(authorDtos);
        authorResponse.setPageNumber(authorPage.getNumber());
        authorResponse.setPageSize(authorPage.getSize());
        authorResponse.setTotalElements(authorPage.getTotalElements());
        authorResponse.setTotalPages(authorPage.getTotalPages());
        authorResponse.setLastPage(authorPage.isLast());

        return authorResponse;

    }

    @Override
    public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
        Author authorFromDb = authorRepository.findByAuthorId(authorId);
        if(authorFromDb == null){
            throw new ResourceNotFoundException("Author", "authorId", authorId);
        }
        Author author = modelMapper.map(authorDto, Author.class);
        authorFromDb.setAuthorName(author.getAuthorName());
        authorFromDb.setAuthorImgUrl(author.getAuthorImgUrl());
        authorFromDb.setAuthorBio(author.getAuthorBio());
        authorFromDb.setAuthorDOB(author.getAuthorDOB());
        Author updatedAuthor = authorRepository.save(authorFromDb);
        return modelMapper.map(updatedAuthor, AuthorDto.class);
    }

    @Override
    public AuthorDto deleteAuthor(Long authorId) {
        Author authorFromDb = authorRepository.findByAuthorId(authorId);
        if(authorFromDb == null){
            throw new ResourceNotFoundException("Author", "authorId", authorId);
        }
        authorRepository.delete(authorFromDb);
        return modelMapper.map(authorFromDb, AuthorDto.class);
    }
}
