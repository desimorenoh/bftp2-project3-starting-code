package net.filmcity.app.controllers;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MoviesController {

    private MovieRepository movieRepository;
    private String id;
    private Long aLong;
    private String customerName;


    @Autowired
    public MoviesController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/movies/{id}")
    public Movie findMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        return movie;
    }

    @PostMapping("/movies")
    public Movie addMovie (@RequestBody Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    @DeleteMapping("/movies/{id}")
    public Movie deleteMovieById(@PathVariable Long id) {
       Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movieRepository.deleteById(id);
        return movie;
    }


    @PutMapping("/movies/{id}/book?renter={name}")
    public Movie renter(@PathVariable Long id, @RequestParam String customerName) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setBooked(true);
        movie.setRenter(customerName);
        return movieRepository.save(movie);
    }
    @PutMapping("/movies/{id}/return")
    public Movie renter(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
        movie.setBooked(false);
        movie.setRenter(null);
        return movieRepository.save(movie);
    }


}













<<<<<<< HEAD

=======
>>>>>>> 3b3933fae32d881d6211d8031a44db5117449011
