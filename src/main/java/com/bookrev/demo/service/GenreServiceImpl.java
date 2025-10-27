package com.bookrev.demo.service;

import com.bookrev.demo.dto.GenreDto;
import com.bookrev.demo.exception.APIException;
import com.bookrev.demo.exception.ResourceNotFoundException;
import com.bookrev.demo.model.Genre;
import com.bookrev.demo.payload.GenreResponse;
import com.bookrev.demo.repository.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public GenreResponse getAllGenres(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // Determine the sort order (ascending or descending)
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create a pageable object
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        // Fetch the paginated list of genres
        Page<Genre> genrePage = genreRepository.findAll(pageDetails);

        // Get the list of genre content
        List<Genre> genres = genrePage.getContent();
        if (genres.isEmpty()) {
            throw new APIException("No genres found.");
        }

        // Map the Genre list to GenreDto list
        List<GenreDto> genreDtos = genres.stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .toList();

        // Build the GenreResponse object
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setContent(genreDtos);
        genreResponse.setPageNumber(genrePage.getNumber());
        genreResponse.setPageSize(genrePage.getSize());
        genreResponse.setTotalElements(genrePage.getTotalElements());
        genreResponse.setTotalPages(genrePage.getTotalPages());
        genreResponse.setLastPage(genrePage.isLast());

        return genreResponse;

    }

    @Override
    public GenreDto addGenre(GenreDto genreDto) {
        Genre genre = modelMapper.map(genreDto, Genre.class);
        Genre genreFromDb = genreRepository.findByGenreName(genre.getGenreName());
        if(genreFromDb != null){
            throw new APIException("Genre with name " + genre.getGenreName() + " already exists");
        }
        Genre savedGenre = genreRepository.save(genre);
        return modelMapper.map(savedGenre, GenreDto.class);
    }

    @Override
    public GenreDto deleteGenre(Long genreId) {
        Genre genreFromDb = genreRepository.findById(genreId).
                orElseThrow(() -> new ResourceNotFoundException("Genrre", "genreId", genreId));
        genreRepository.delete(genreFromDb);
        return modelMapper.map(genreFromDb, GenreDto.class);
    }

    @Override
    public GenreDto updateGenre(Long genreId, GenreDto genreDto) {
        Genre genreFromDb = genreRepository.findById(genreId).
                orElseThrow(() -> new ResourceNotFoundException("Genrre", "genreId", genreId));
        Genre genre = modelMapper.map(genreDto, Genre.class);
        genreFromDb.setGenreName(genre.getGenreName());
        Genre updatedGenre = genreRepository.save(genreFromDb);
        return modelMapper.map(updatedGenre, GenreDto.class);
    }

    @Override
    public GenreDto getGenre(Long genreId) {
        return null;
    }
}
