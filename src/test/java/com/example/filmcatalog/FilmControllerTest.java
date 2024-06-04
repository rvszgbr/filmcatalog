 package com.example.filmcatalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.BDDMockito.given;

public class FilmControllerTest {

    private MockMvc mockMvc;
    private FilmRepository repository;
    private FilmController controller;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(FilmRepository.class);
        controller = new FilmController(repository);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAll() throws Exception {
        given(repository.findAll()).willReturn(Arrays.asList(new Film(), new Film()));

        mockMvc.perform(MockMvcRequestBuilders.get("/films"))
                .andExpect(status().isOk());
    }

    @Test
    public void testOne() throws Exception {
        Film film = new Film();
        film.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(film));

        mockMvc.perform(MockMvcRequestBuilders.get("/films/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testNewFilm() throws Exception {
        Film film = new Film();
        film.setTitle("A film címe");
        film.setDirector("A film rendezője");
        film.setReleaseYear(2024);
        given(repository.save(any(Film.class))).willReturn(film);

        mockMvc.perform(MockMvcRequestBuilders.post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"A film címe\", \"director\":\"A film rendezője\", \"releaseYear\":2024}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":null, \"title\":\"A film címe\", \"director\":\"A film rendezője\", \"releaseYear\":2024}"));
    }

    @Test
    public void testReplaceFilm() throws Exception {
        Film oldFilm = new Film();
        oldFilm.setId(1L);
        oldFilm.setTitle("Régi film címe");
        oldFilm.setDirector("Régi film rendezője");
        oldFilm.setReleaseYear(2020);

        Film newFilm = new Film();
        newFilm.setTitle("Új film címe");
        newFilm.setDirector("Új film rendezője");
        newFilm.setReleaseYear(2024);

        given(repository.findById(1L)).willReturn(Optional.of(oldFilm));
        given(repository.save(any(Film.class))).willReturn(newFilm);

        mockMvc.perform(MockMvcRequestBuilders.put("/films/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Új film címe\", \"director\":\"Új film rendezője\", \"releaseYear\":2024}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1, \"title\":\"Új film címe\", \"director\":\"Új film rendezője\", \"releaseYear\":2024}"));
    }

    @Test
    public void testDeleteFilm() throws Exception {
        doNothing().when(repository).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/films/1"))
                .andExpect(status().isOk());
    }

}
