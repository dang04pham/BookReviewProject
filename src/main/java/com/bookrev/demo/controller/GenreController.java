package com.bookrev.demo.controller;

import com.bookrev.demo.config.AppConstants;
import com.bookrev.demo.dto.GenreDto;
import com.bookrev.demo.payload.GenreResponse;
import com.bookrev.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/public/genres/getAll")
    public ResponseEntity<GenreResponse> getAllGenres(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_GENRES_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        GenreResponse genreResponse = genreService.getAllGenres(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(genreResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/genres/create")
    public ResponseEntity<GenreDto> addGenre(@RequestBody GenreDto genreDto) {
        GenreDto savedGenreDto = genreService.addGenre(genreDto);
        return new ResponseEntity<>(savedGenreDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/genres/delete")
    public ResponseEntity<GenreDto> deleteGenre(@RequestParam(name = "genreId") Long genreId) {
        GenreDto deletedGenreDto = genreService.deleteGenre(genreId);
        return new ResponseEntity<>(deletedGenreDto, HttpStatus.OK);
    }

    @PutMapping("/admin/genres/update")
    public ResponseEntity<GenreDto> updateGenre(@RequestParam(name = "genreId") Long genreId, @RequestBody GenreDto genreDto) {
        GenreDto updatedGenreDto = genreService.updateGenre(genreId, genreDto);
        return new ResponseEntity<>(updatedGenreDto, HttpStatus.OK);
    }
}
