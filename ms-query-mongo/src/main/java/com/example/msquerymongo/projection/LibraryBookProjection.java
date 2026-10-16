package com.example.msquerymongo.projection;

import com.example.coreapi.events.BookAddedEvent;
import com.example.coreapi.events.BookRemovedEvent;
import com.example.coreapi.events.LibraryCreatedEvent;
import com.example.msquerymongo.dao.LibraryBookViewRepository;
import com.example.msquerymongo.dao.LibraryTempRepository;
import com.example.msquerymongo.entities.LibraryBookView;
import com.example.msquerymongo.entities.LibraryTemp;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibraryBookProjection {

    @Autowired
    LibraryTempRepository libraryTempRepository;

    @Autowired
    LibraryBookViewRepository libraryBookViewRepository;

    @EventHandler
    public void on(LibraryCreatedEvent event) {
        libraryTempRepository.save(
            new LibraryTemp(event.getLibraryId(), event.getName())
        );
    }

    @EventHandler
    public void on(BookAddedEvent event) {
        String libraryName = libraryTempRepository
            .findById(event.getLibraryId())
            .map(lib -> lib.getName())
            .orElse("Unknown");

        libraryBookViewRepository.save(new LibraryBookView(
            event.getIsbn(),
            event.getLibraryId(),
            libraryName,
            event.getTitle(),
            event.getEditeurId()
        ));
    }

    @EventHandler
    public void on(BookRemovedEvent event) {
        libraryBookViewRepository.deleteById(event.getIsbn());
    }
}
