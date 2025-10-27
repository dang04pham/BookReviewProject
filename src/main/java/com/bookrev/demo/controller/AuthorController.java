package com.bookrev.demo.controller;

import com.bookrev.demo.config.AppConstants;
import com.bookrev.demo.dto.AuthorDto;
import com.bookrev.demo.payload.AuthorResponse;
import com.bookrev.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/admin/authors/create")
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto savedAuthorDto = authorService.addAuthor(authorDto);
        return new ResponseEntity<>(savedAuthorDto, HttpStatus.OK);
    }

    @GetMapping("/public/authors/getAll")
    public ResponseEntity<AuthorResponse> getAllAuthors(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_AUTHORS_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        AuthorResponse authorResponse = authorService.getAllAuthors(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(authorResponse, HttpStatus.OK);
    }

    public ResponseEntity<AuthorDto> updateAuthor(@RequestParam(name = "authorId") Long authorId, @RequestBody AuthorDto authorDto) {
        AuthorDto updatedAuthorDto = authorService.updateAuthor(authorId, authorDto);
        return new ResponseEntity<>(updatedAuthorDto, HttpStatus.OK);
    }

    public ResponseEntity<AuthorDto> deleteAuthor(@RequestParam(name = "authorId") Long authorId) {
        AuthorDto deletedAuthorDto = authorService.deleteAuthor(authorId);
        return new ResponseEntity<>(deletedAuthorDto, HttpStatus.OK);
    }
}