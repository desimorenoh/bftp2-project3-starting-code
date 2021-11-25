package net.filmcity.app.controllers;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {

    private final MovieRepository movieRepository;
    private Object MovieNotFoundException;

    @Autowired
    MoviesController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies")
    public Movie addMovie (@RequestBody Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    @DeleteMapping("/movies{index}")
    public Movie deleteCoderByIndex(@PathVariable int index) {
        return MovieRepository.deleteByIndex(index).orElseThrow(MovieNotFoundException::new);
    }

    @PutMapping("/movies")
    public Movie updateCoderByName(@RequestBody Movie movie) {
        return MovieRepository.update(movie);
    }

}









}