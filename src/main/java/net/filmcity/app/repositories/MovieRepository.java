package net.filmcity.app.repositories;

import net.filmcity.app.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie update(Movie movie);

   public Optional<Movie> deleteByIndex(int index){
       if (isValidIndex(index)) {
           return Optional.of(movies.remove(index));
       }
       return Optional.empty();
   }


}
