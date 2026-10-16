package com.esi.mscommandlibrarybookstored.Aggregate;

import com.example.coreapi.commands.AddBookCommand;
import com.example.coreapi.commands.RemoveBookCommand;
import com.example.coreapi.events.BookAddedEvent;
import com.example.coreapi.events.BookRemovedEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Book {

    @Id
    @EntityId
    private String isbn;

    private String title;
    private String editeurId;

    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    @CommandHandler
    public void handle(AddBookCommand cmd) {
        AggregateLifecycle.apply(new BookAddedEvent(
            cmd.getLibraryId(), cmd.getIsbn(), cmd.getTitle(), cmd.getEditeurId()
        ));
    }

    @CommandHandler
    public void handle(RemoveBookCommand cmd) {
        AggregateLifecycle.apply(new BookRemovedEvent(cmd.getLibraryId(), cmd.getIsbn()));
    }

    @EventSourcingHandler
    public void on(BookAddedEvent event) {
        this.isbn = event.getIsbn();
        this.title = event.getTitle();
        this.editeurId = event.getEditeurId();
    }
}
