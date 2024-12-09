package com.example.TestProject.controller;

import com.example.TestProject.model.Movie;
import com.example.TestProject.repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    // Show the list of all movies
    @GetMapping
    public String listMovies(Model model) {
        List<Movie> movies = movieRepository.findAll(); // Fetch all books from the database
        model.addAttribute("movies", movies); // Add movies to the model
        return "movies-list"; // Return the name of the Thymeleaf template
    }

    // Show form to add a new book
    @PostMapping("/new")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "create_movie"; // This should match your template name for the add form
    }

    // Handle the submission of the new book form
    @PostMapping
    public String addMovie(@ModelAttribute Movie movie) {
        movieRepository.save(movie); // Save the book to the database
        return "redirect:/movies"; // Redirect back to the book list after saving
    }

    // Show form to update an existing book
    @GetMapping("/edit/{id}")
    public String showUpdateMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("movie", movie);
        return "update_movie"; // This should match your template name for the update form
    }

    // Handle the submission of the updated book form
    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        movie.setId(id); // Ensure the ID is set for the update
        movieRepository.save(movie); // Save the updated book to the database
        return "redirect:/movies"; // Redirect back to the book list after updating
    }

    // Show confirmation page for deleting a book
    @GetMapping("/delete/{id}")
    public String showDeleteMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("movie", movie);
        return "delete-movie"; // This should match your template name for the delete confirmation
    }

    // Handle the deletion of the book
    @PostMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieRepository.deleteById(id); // Delete the book from the database
        return "redirect:/movies"; // Redirect back to the book list after deleting
    }
}
