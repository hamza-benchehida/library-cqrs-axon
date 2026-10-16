package com.esi.mscommandlibrarybookstored.Aggregate;

import com.example.coreapi.commands.AddBookCommand;
import com.example.coreapi.commands.LibraryCreationCommand;
import com.example.coreapi.commands.RemoveBookCommand;
import com.example.coreapi.events.BookAddedEvent;
import com.example.coreapi.events.BookRemovedEvent;
import com.example.coreapi.events.LibraryCreatedEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Entity
@Table(name = "LibraryTable")
@Data @AllArgsConstructor @NoArgsConstructor
public class Library {

    @AggregateIdentifier
    @Id
    private String libraryId;

    private String name;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @AggregateMember
    private List<Book> Books = new ArrayList<>();

    @CommandHandler
    public Library(LibraryCreationCommand cmd) {
        Assert.notNull(cmd.getLibraryId(), "LibraryId should not be null");
        Assert.notNull(cmd.getName(), "name should not be null");
        AggregateLifecycle.apply(new LibraryCreatedEvent(cmd.getLibraryId(), cmd.getName()));
    }

    @CommandHandler
    public void handle(AddBookCommand cmd) {
        Assert.notNull(cmd.getIsbn(), "ISBN should not be null");
        Assert.notNull(cmd.getTitle(), "Title should not be null");
        boolean exists = this.Books.stream()
            .anyMatch(b -> b.getIsbn().equals(cmd.getIsbn()));
        if (exists) throw new RuntimeException("Book ISBN must be unique");
        AggregateLifecycle.apply(new BookAddedEvent(
            cmd.getLibraryId(), cmd.getIsbn(), cmd.getTitle(), cmd.getEditeurId()
        ));
    }

    @CommandHandler
    public void handle(RemoveBookCommand cmd) {
        boolean exists = this.Books.stream()
            .anyMatch(b -> b.getIsbn().equals(cmd.getIsbn()));
        if (!exists) throw new RuntimeException("Book ISBN not found");
        AggregateLifecycle.apply(new BookRemovedEvent(cmd.getLibraryId(), cmd.getIsbn()));
    }

    @EventSourcingHandler
    public void on(LibraryCreatedEvent event) {
        this.libraryId = event.getLibraryId();
        this.name = event.getName();
        this.Books = new ArrayList<>();
    }

    @EventSourcingHandler
    public void on(BookAddedEvent event) {
        Book book = new Book();
        book.setIsbn(event.getIsbn());
        book.setTitle(event.getTitle());
        book.setEditeurId(event.getEditeurId());
        book.setLibrary(this);
        this.Books.add(book);
    }

    @EventSourcingHandler
    public void on(BookRemovedEvent event) {
        this.Books.removeIf(b -> b.getIsbn().equals(event.getIsbn()));
    }
}
