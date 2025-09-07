package com.example.smartsearch.controller;

import com.example.smartsearch.dto.MovieDto;
import com.example.smartsearch.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/init")
    public ResponseEntity<?> initVectorDb() {
        movieService.initVectorStore();
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/search-movie")
    public List<MovieDto> getSimilarSearch(@RequestBody String message) {
        return movieService.searchSimilarMovies(message);
    }

    @GetMapping("/search-movie-top")
    public List<MovieDto> searchSimilarMoviesWithRequest(@RequestBody String message) {
        return movieService.searchSimilarMoviesWithRequest(message, 2);
    }
}
