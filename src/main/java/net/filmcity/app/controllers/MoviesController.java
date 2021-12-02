package net.filmcity.app.controllers;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.domain.Rating;
import net.filmcity.app.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MoviesController {

    private final MovieRepository movieRepository;

    @Autowired
    MoviesController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id);
    }

    @PutMapping("/movies/{id}/book")
    public Movie bookMovie(@PathVariable Long id, @RequestParam String customerName) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setBooked(true);
        movie.setRenter(customerName);
        return movieRepository.save(movie);
    }

    @PutMapping("/movies/{id}/return")
    public Movie bookMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setBooked(false);
        movie.setRenter(null);
        return movieRepository.save(movie);
    }

    @PutMapping("/movies/{id}/rating")
    public Movie addRating(@RequestBody Rating rating, @PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setRating(rating.getScore());
        return movieRepository.save(movie);
    }

}






