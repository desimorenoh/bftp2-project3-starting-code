package net.filmcity.app.repositories;

import net.filmcity.app.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SampleDataLoader {

    private final MovieRepository movieRepository;

    @Autowired
    public SampleDataLoader(MovieRepository movieRepository){

        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void addSampleMovies() {
        if (movieRepository.findAll().isEmpty()) {
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
                            "Remy, a resident of Paris, appreciates good food and has quite a sophisticated palate. He would love to become a chef so he can create and enjoy culinary masterpieces to his heart's delight. The only problem is, Remy is a rat."),
                    new Movie("Cruella",
                            "https://lumiere-a.akamaihd.net/v1/images/image_7ff71125.jpeg?region=0%2C0%2C540%2C810",
                            "Craig Gillespie",
                            2021,
                            "Set in London during the punk rock movement of the 1970s, the film revolves around Estella Miller, an aspiring fashion designer, as she explores the path that will lead her to become a notorious up-and-coming fashion designer known as Cruella de Vil."
                    ),
                    new Movie("Mean Girls",
                            "https://images-na.ssl-images-amazon.com/images/I/71eQtET-kmL._RI_.jpg",
                            "Mark Waters",
                            2004,
                            "Lindsay Lohan stars as Cady Heron, a 16 year old homeschooled girl who not only makes the mistake of falling for Aaron Samuels (Jonathan Bennett), the ex-boyfriend of queenbee Regina George (Rachel McAdams), but also unintentionally joins The Plastics, led by Regina herself."
                    ),
                    new Movie("Lady Bird",
                            "https://play-lh.googleusercontent.com/qh6m6We5w7325ttmO1qcA0Zmtlm2UG4JUux6VSBsjvxG9azJ6KHdBsobAviSMcTOzTULPQ",
                            "Greta Gerwig",
                            2017,
                            "Christine 'Lady Bird' McPherson (Saoirse Ronan) is a senior at a Catholic high school in Sacramento, California in 2002. She longs to attend a prestigious college in 'a city with culture'."
                    ),
                    new Movie("Suffragette",
                            "https://musicart.xboxlive.com/7/b81f2600-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080",
                            "Sarah Gavron",
                            2015,
                            "Inspired by true events, Suffragette movingly explores the passion and heartbreak of those who risked all they had for women's right to vote – their jobs, their homes, their children, and even their lives."
                    ),
                    new Movie("On the basis of sex",
                            "https://m.media-amazon.com/images/I/71TuUvNkS4L._SL1500_.jpg",
                            "Mimi Leder",
                            2018,
                            "On the Basis of Sex is inspired by the true story of a young Ruth Bader Ginsburg – then a struggling attorney and new mother – who faces adversity and numerous obstacles in her fight for equal rights throughout her career."
                    ),
                    new Movie("La vita è bella",
                            "https://m.media-amazon.com/images/I/51tWTZJrHYL._SY445_.jpg",
                            "Roberto Benigni",
                            1997,
                            "Benigni interpreta a Guido Orefice, un judío italiano dueño de una librería, que debe emplear su fértil imaginación para proteger a su pequeño hijo de los horrores de un campo de concentración nazi."
                    )

            );

            movieRepository.saveAll(movies);
        }
    }
}

