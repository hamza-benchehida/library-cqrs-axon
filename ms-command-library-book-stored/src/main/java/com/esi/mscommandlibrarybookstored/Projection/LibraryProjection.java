package com.esi.mscommandlibrarybookstored.Projection;

import com.example.coreapi.events.BookAddedEvent;
import com.example.coreapi.events.BookRemovedEvent;
import com.example.coreapi.events.LibraryCreatedEvent;
import com.esi.mscommandlibrarybookstored.Aggregate.Book;
import com.esi.mscommandlibrarybookstored.Aggregate.Library;
import com.esi.mscommandlibrarybookstored.Repository.BookRepository;
import com.esi.mscommandlibrarybookstored.Repository.LibraryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryProjection {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

    @EventHandler
    public void on(LibraryCreatedEvent event) {
        Library library = new Library();
        library.setLibraryId(event.getLibraryId());
        library.setName(event.getName());
        libraryRepository.save(library);
    }

    @EventHandler
    public void on(BookAddedEvent event) {
        Library library = libraryRepository.findById(event.getLibraryId())
            .orElseThrow(() -> new RuntimeException("Library not found"));
        Book book = new Book();
        book.setIsbn(event.getIsbn());
        book.setTitle(event.getTitle());
        book.setEditeurId(event.getEditeurId());
        book.setLibrary(library);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookRemovedEvent event) {
        bookRepository.deleteById(event.getIsbn());
    }
}
