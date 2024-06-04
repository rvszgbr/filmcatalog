package com.example.filmcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmTest {

    private Film film;

    @BeforeEach
    public void setUp() {
        film = new Film();
    }

    @Test
    public void testId() {
        Long idValue = 1L;
        film.setId(idValue);
        assertEquals(idValue, film.getId());
    }

    @Test
    public void testTitle() {
        String titleValue = "A film címe";
        film.setTitle(titleValue);
        assertEquals(titleValue, film.getTitle());
    }

    @Test
    public void testDirector() {
        String directorValue = "A film rendezője";
        film.setDirector(directorValue);
        assertEquals(directorValue, film.getDirector());
    }

    @Test
    public void testReleaseYear() {
        int releaseYearValue = 2024;
        film.setReleaseYear(releaseYearValue);
        assertEquals(releaseYearValue, film.getReleaseYear());
    }
}
