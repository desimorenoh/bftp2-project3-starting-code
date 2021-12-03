package net.filmcity.app.domain;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String coverImage;
    private String director;
    private int year;
    private boolean booked = false;
    private String renter;
    private int rating;

    @Lob
    private String synopsis;

    public Movie() { }

    public Movie(String title, String coverImage, String director, int year, String synopsis) {
        this.title = title;
        this.coverImage = coverImage;
        this.director = director;
        this.year = year;
        this.synopsis = synopsis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }


    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getRenter() {
        return renter;
    }

    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerName() { return customerName; }

    public void setRenter(String renter) { this.renter = renter; }

    public String getRenter() { return renter; }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
