package com.bookrev.demo.service;

import com.bookrev.demo.dto.GenreDto;
import com.bookrev.demo.payload.GenreResponse;

public interface GenreService {
    GenreResponse getAllGenres(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    GenreDto addGenre(GenreDto genreDto);
    GenreDto deleteGenre(Long genreId);
    GenreDto updateGenre(Long genreId, GenreDto genreDto);
    GenreDto getGenre(Long genreId);
}
