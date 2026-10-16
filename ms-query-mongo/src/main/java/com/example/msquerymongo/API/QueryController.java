package com.example.msquerymongo.API;

import com.example.msquerymongo.dao.LibraryBookViewRepository;
import com.example.msquerymongo.entities.LibraryBookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    LibraryBookViewRepository libraryBookViewRepository;

    @GetMapping("/books")
    public List<LibraryBookView> getAllBooks() {
        return libraryBookViewRepository.findAll();
    }

    @GetMapping("/books/{isbn}")
    public LibraryBookView getBookByIsbn(@PathVariable String isbn) {
        return libraryBookViewRepository.findById(isbn).orElse(null);
    }

    @GetMapping("/libraries/{libraryId}/books")
    public List<LibraryBookView> getBooksByLibrary(@PathVariable String libraryId) {
        return libraryBookViewRepository.findByLibraryId(libraryId);
    }
}
