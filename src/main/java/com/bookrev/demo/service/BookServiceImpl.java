package com.bookrev.demo.service;

import com.bookrev.demo.dto.BookDto;
import com.bookrev.demo.exception.ResourceNotFoundException;
import com.bookrev.demo.model.Author;
import com.bookrev.demo.model.Book;
import com.bookrev.demo.model.Genre;
import com.bookrev.demo.payload.BookResponse;
import com.bookrev.demo.repository.AuthorRepository;
import com.bookrev.demo.repository.BookRepository;
import com.bookrev.demo.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    public BookDto addBook(BookDto bookDto) {
        List<Author> authors  = authorRepository.findAllById(bookDto.getAuthorIds());
        List<Genre> genres = genreRepository.findAllById(bookDto.getGenreIds());

        if(authors.isEmpty() || genres.isEmpty()){
            throw new ResourceNotFoundException("Author or Genre", "authorId or genreId", "0");
        }

        Book book = new Book();
        book.setBookTitle(bookDto.getBookName());
        book.setBookImgUrl(bookDto.getBookImgUrl());
        book.setAuthors(authors);
        book.setGenres(genres);

        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookDto.class);
    }

    @Override
    public BookResponse getAllBooks(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // Sort direction: "asc" or "desc"
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Page Request
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetch paginated books
        Page<Book> booksPage = bookRepository.findAll(pageable);

        // Map books to DTOs
        List<BookDto> bookDtos = booksPage.getContent()
                .stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();

        // Build and return the BookResponse
        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(bookDtos);
        bookResponse.setPageNumber(booksPage.getNumber());
        bookResponse.setPageSize(booksPage.getSize());
        bookResponse.setTotalElements(booksPage.getTotalElements());
        bookResponse.setTotalPages(booksPage.getTotalPages());
        bookResponse.setLastPage(booksPage.isLast());

        return bookResponse;

    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) {
        // Fetch the book by ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Id", bookId.toString()));

        // Fetch authors and genres from the database
        List<Author> authors = authorRepository.findAllById(bookDto.getAuthorIds());
        List<Genre> genres = genreRepository.findAllById(bookDto.getGenreIds());

        if (authors.isEmpty() || genres.isEmpty()) {
            throw new ResourceNotFoundException("Author or Genre", "authorId or genreId", "0");
        }

        // Update book fields
        book.setBookTitle(bookDto.getBookName());
        book.setBookImgUrl(bookDto.getBookImgUrl());
        book.setAuthors(authors);
        book.setGenres(genres);

        // Save updated book back to the repository
        Book updatedBook = bookRepository.save(book);

        // Map updated book to BookDto and return
        return modelMapper.map(updatedBook, BookDto.class);

    }

    @Override
    public BookDto deleteBook(Long bookId) {
        // Fetch the book by ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "Id", bookId.toString()));

        book.getAuthors().clear();
        book.getGenres().clear();
        bookRepository.save(book);
        // Delete the book
        bookRepository.delete(book);

        // Map deleted book to BookDto and return
        return modelMapper.map(book, BookDto.class);

    }

}
