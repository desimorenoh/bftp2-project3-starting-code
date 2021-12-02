package net.filmcity.app;

import net.filmcity.app.domain.Movie;
import net.filmcity.app.repositories.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
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
    void returnsTheExistingMovies() throws Exception {

        addSampleMovies();

        final ResultActions resultActions = mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].title", equalTo("Jurassic Park")))
                .andExpect(jsonPath("$[0].coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")))
                .andExpect(jsonPath("$[0].director", equalTo("Steven Spielberg")))
                .andExpect(jsonPath("$[0].year", equalTo(1993)))
                .andExpect(jsonPath("$[0].genre", equalTo("Science Fiction")))
                .andExpect(jsonPath("$[0].rating", equalTo(9)))
                .andExpect(jsonPath("$[0].synopsis", equalTo("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.")))
                .andExpect(jsonPath("$[1].title", equalTo("Ratatouille")))
                .andExpect(jsonPath("$[1].coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg")))
                .andExpect(jsonPath("$[1].director", equalTo("Brad Bird")))
                .andExpect(jsonPath("$[1].year", equalTo(2007)))
                .andExpect(jsonPath("$[1].genre", equalTo("Fantasy")))
                .andExpect(jsonPath("$[1].rating", equalTo(7)))
                .andExpect(jsonPath("$[1].synopsis", equalTo("Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.")))
                .andDo(print());
    }

    private void addSampleMovies() {
        List<Movie> movies = List.of(
                new Movie("Jurassic Park",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg",
                        "Steven Spielberg",
                        1993,
                        "Science Fiction",
                        9,
                        "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.",
                        "",
                        "false"
                ),
                new Movie("Ratatouille",
                        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/npHNjldbeTHdKKw28bJKs7lzqzj.jpg",
                        "Brad Bird",
                        2007,
                        "Fantasy",
                        7,
                        "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat.",
                        "",
                        "false"
                )
        );

        movieRepository.saveAll(movies);
    }


   @Test
  void allowsToCreateANewMovie() throws Exception {
          mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Jurassic Park\", \"coverImage\": \"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\", \"director\": \"Steven Spielberg\", \"year\": \"1993\", \"genre\": \"Science Fiction\", \"rating\": \"9\", \"synopsis\": \"A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.\"}")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, contains(allOf(
                hasProperty("title", is("Jurassic Park")),
                hasProperty("coverImage", is("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")),
                hasProperty("director", is("Steven Spielberg")),
                hasProperty("year", is(1993)),
                hasProperty("genre", is("Science Fiction")),
                hasProperty("rating", is(9)),
                hasProperty("synopsis", is("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."))
        )));
    }

    @Test
    void allowsToFindAMovieById() throws Exception {

        Movie movie = movieRepository.save(new Movie("Jurassic Park", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg", "Steven Spielberg", 1993, "Science Fiction", 9, "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.", "", "false"));

        mockMvc.perform(get("/movies/" + movie.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", equalTo("Jurassic Park")))
                .andExpect(jsonPath("$.coverImage", equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")))
                .andExpect(jsonPath("$.director", equalTo("Steven Spielberg")))
                .andExpect(jsonPath("$.year", equalTo(1993)))
                .andExpect(jsonPath("$.genre", equalTo("Science Fiction")))
                .andExpect(jsonPath("$.rating", equalTo(9)))
                .andExpect(jsonPath("$.synopsis", equalTo("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.")));

    }

    @Test
    void allowsToDeleteAMovieById() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg", "Steven Spielberg", 1993, "Science Fiction", 9, "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.", "", "false"));

        mockMvc.perform(delete("/movies/"+ movie.getId()))
                .andExpect(status().isOk());


        List<Movie> movies = movieRepository.findAll();
        assertThat(movies, not(contains(allOf(
                hasProperty("title", is("Jurassic Park")),
                hasProperty("coverImage", is("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg")),
                hasProperty("director", is("Steven Spielberg")),
                hasProperty("year", is(1993)),
                hasProperty("genre", is("Science Fiction")),
                hasProperty("rating", is(9)),
                hasProperty("synopsis", is("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."))
        ))));
    }

    @Test
    void returnsAnErrorIfTryingToGetAMovieThatDoesNotExist() throws Exception {
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void returnsAnErrorIfTryingToDeleteAMovieThatDoesNotExist() throws Exception {
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void allowsToModifyAMovie() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg", "Steven Spielberg", 1993, "Science Fiction", 9, "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.", "", "false"));

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + movie.getId() + "\", \"title\": \"Jurassic Park\", \"coverImage\": \"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\", \"director\": \"Steven Spielberg\", \"year\": \"1993\", \"genre\": \"Science Fiction\", \"rating\": \"9\", \"synopsis\": \"A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.\" }")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();

        assertThat(movies, hasSize(1));
        assertThat(movies.get(0).getTitle(), equalTo("Jurassic Park"));
        assertThat(movies.get(0).getCoverImage(), equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg"));
        assertThat(movies.get(0).getDirector(), equalTo("Steven Spielberg"));
        assertThat(movies.get(0).getYear(), equalTo(1993));
        assertThat(movies.get(0).getGenre(), equalTo("Science Fiction"));
        assertThat(movies.get(0).getRating(), equalTo(9));
        assertThat(movies.get(0).getSynopsis(), equalTo("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."));
    }


    @Test
    void returnsAnErrorWhenTryingToModifyAMovieThatDoesNotExist() throws Exception {
        addSampleMovies();

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + -1 + "\", \"title\": \"Jurassic\", \"coverImage\": \"https://www.thesmoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\", \"director\": \"Steven Moreno\", \"year\": \"1992\", \"genre\": \"Science\", \"rating\": \"8\", \"synopsis\": \"A unhealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.\" }")
        ).andExpect(status().isNotFound());
    }

    @Test
    void allowsToModifyAMovieWhenIsBooked() throws Exception {
        Movie movie = movieRepository.save(new Movie("Jurassic Park", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg", "Steven Spielberg", 1993, "Science Fiction", 9, "A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.", "", "false"));

        mockMvc.perform(put("/movies/1/book?customerName=desi")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"" + movie.getId() + "\", \"title\": \"Jurassic Park\", \"coverImage\": \"https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg\", \"director\": \"Steven Spielberg\", \"year\": \"1993\", \"genre\": \"Science Fiction\", \"rating\": \"9\", \"synopsis\": \"A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA.\" }")
        ).andExpect(status().isOk());

        List<Movie> movies = movieRepository.findAll();

        assertThat(movies, hasSize(1));
        assertThat(movies.get(0).getTitle(), equalTo("Jurassic Park"));
        assertThat(movies.get(0).getCoverImage(), equalTo("https://www.themoviedb.org/t/p/w600_and_h900_bestv2/oU7Oq2kFAAlGqbU4VoAE36g4hoI.jpg"));
        assertThat(movies.get(0).getDirector(), equalTo("Steven Spielberg"));
        assertThat(movies.get(0).getYear(), equalTo(1993));
        assertThat(movies.get(0).getGenre(), equalTo("Science Fiction"));
        assertThat(movies.get(0).getRating(), equalTo(9));
        assertThat(movies.get(0).getSynopsis(), equalTo("A wealthy entrepreneur secretly creates a theme park featuring living dinosaurs drawn from prehistoric DNA."));
        assertThat(movies.get(0).getCustomerName(), equalTo("desi"));
    }


}

