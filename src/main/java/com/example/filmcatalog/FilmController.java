package com.example.filmcatalog;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class FilmController {

    private final FilmRepository repository;

    FilmController(FilmRepository repository) {
        this.repository = repository;
    }

    // Get all films
    @GetMapping("/films")
    List<Film> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    // Create a new film
    @PostMapping("/films")
    Film newFilm(@RequestBody Film newFilm) {
        return repository.save(newFilm);
    }

    // Get a single film by ID
    @GetMapping("/films/{id}")
    Film one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    // Update a film by ID
    @PutMapping("/films/{id}")
    Film replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {
        return repository.findById(id)
                .map(film -> {
                    film.setTitle(newFilm.getTitle());
                    film.setDirector(newFilm.getDirector());
                    film.setReleaseYear(newFilm.getReleaseYear());
                    return repository.save(film);
                })
                .orElseGet(() -> {
                    newFilm.setId(id);
                    return repository.save(newFilm);
                });
    }

    // Delete a film by ID
    @DeleteMapping("/films/{id}")
    void deleteFilm(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
