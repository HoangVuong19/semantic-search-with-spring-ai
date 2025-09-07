package com.example.smartsearch.repository;

import com.example.smartsearch.dto.MovieDto;
import com.example.smartsearch.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT new com.example.smartsearch.dto.MovieDto(m.id, m.name, m.mainLeads, m.description) FROM Movie m")
    List<MovieDto> findAllMovie();


}
