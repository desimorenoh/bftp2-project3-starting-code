package net.filmcity.app;

import jdk.jfr.ContentType;
import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MovieRepository movieRepository;


    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    void returnsTheExistingCoders() throws Exception {

        addSampleMovies();

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Jurassic Park")))
                .andExpect(jsonPath("$[0].coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")))
                .andExpect(jsonPath("$[0].director", equalTo("Steven Spielberg")))
                .andExpect(jsonPath("$[0].year", equalTo(1993)))
                .andExpect(jsonPath("$[0].synopsis", equalTo("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.")))
                .andExpect(jsonPath("$[1].title", equalTo("Ratatouille")))
                .andExpect(jsonPath("$[1].coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg")))
                .andExpect(jsonPath("$[1].director", equalTo("Brad Bird")))
                .andExpect(jsonPath("$[1].year", equalTo(2007)))
                .andExpect(jsonPath("$[1].synopsis", equalTo("Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.")))
                .andDo(print());
    }

    @Test
    void allowsToCreateANewMovie() throws Exception {

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Jurassic Park\", " +
                        "\"coverImage\": \"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\"," +
                        "\"director\": \"Steven Spielberg\"," +
                        "\"year\": 1993," +
                        "\"synopsis\":\"A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.\" }")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, hasSize(1));
        assertThat(movies, contains(allOf(
                hasProperty("title", is("Jurassic Park")),
                hasProperty("coverImage", is("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")),
                hasProperty("director", is("Steven Spielberg")),
                hasProperty("year", is(1993)),
                hasProperty("synopsis", is("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."))
        )));
    }

    @Test
    void allowsToModifyAnExistingMovie() throws Exception {

        Movie movie = movieRepository.save(new Movie("Jurassic Park",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spielberg",
                1994,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."));

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": " + movie.getId() + "," +
                        "\"title\": \"Jurassic Park\", " +
                        "\"coverImage\": \"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\"," +
                        "\"director\": \"Steven Spielberg\"," +
                        "\"year\": 1993," +
                        "\"synopsis\":\"A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.\" }")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, hasSize(1));
        assertThat(movies, contains(hasProperty("year", is(1993))));
    }

    @Test
    void allowsToDeleteAnExistingMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spielberg",
                1994,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."));

        mockMvc.perform(delete("/movies/" + movie.getId()))
                .andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, hasSize(0));
    }

    @Test
    void allowsToBookAnExistingMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spielberg",
                1994,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."));

        mockMvc.perform(put("/movies/"+movie.getId()+"/book?customerName=pepita"))
                .andExpect(status().isOk());

        Movie bookedMovie = movieRepository.findById(movie.getId()).get();
        assertThat(bookedMovie.isBooked(), equalTo(true));
        assertThat(bookedMovie.getRenter(), equalTo("pepita"));

    }

    @Test
    void allowsToReturnAMovieThatHasBeenBooked() throws Exception {

        Movie movie = new Movie("Jurassic Park",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spielberg",
                1994,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.");

        movie.setBooked(true);
        movie.setRenter("pepita");

        movie = movieRepository.save(movie);

        mockMvc.perform(put("/movies/"+movie.getId()+"/return"))
                .andExpect(status().isOk());

        Movie bookedMovie = movieRepository.findById(movie.getId()).get();
        assertThat(bookedMovie.isBooked(), equalTo(false));
        assertThat(bookedMovie.getRenter(), equalTo(null));

    }

    @Test
    void allowsToRateAMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                "Steven Spielberg",
                1994,
                "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."));


        mockMvc.perform(
                        put("/movies/"+ movie.getId()+"/rating")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{ \"score\": 4 }"))
                .andExpect(status().isOk());

        Movie updatedMovie = movieRepository.findById(movie.getId()).get();

        assertThat(updatedMovie.getRating(), equalTo(4));

    }

    private void addSampleMovies() {
        List<Movie> movies = List.of(
                new Movie("Jurassic Park",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                        "Steven Spielberg",
                        1993,
                        "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."),
                new Movie("Ratatouille",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg",
                        "Brad Bird",
                        2007,
                        "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.")
        );

        movieRepository.saveAll(movies);
    }

}