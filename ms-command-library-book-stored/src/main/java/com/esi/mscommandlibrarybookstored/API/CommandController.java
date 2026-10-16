package com.esi.mscommandlibrarybookstored.API;

import com.example.coreapi.DTO.BookDTO;
import com.example.coreapi.DTO.LibraryDTO;
import com.example.coreapi.commands.AddBookCommand;
import com.example.coreapi.commands.LibraryCreationCommand;
import com.example.coreapi.commands.RemoveBookCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/command")
public class CommandController {

    @Autowired
    CommandGateway commandGateway;

    @PostMapping("/library")
    public CompletableFuture<String> createLibrary(@RequestBody LibraryDTO dto) {
        return commandGateway.send(
            new LibraryCreationCommand(dto.getLibraryId(), dto.getName())
        );
    }

    @PostMapping("/library/{libraryId}/book")
    public CompletableFuture<String> addBook(@PathVariable String libraryId,
                                              @RequestBody BookDTO dto) {
        return commandGateway.send(
            new AddBookCommand(libraryId, dto.getIsbn(), dto.getTitle(), dto.getEditeurId())
        );
    }

    @DeleteMapping("/library/{libraryId}/{isbn}")
    public CompletableFuture<String> removeBook(@PathVariable String libraryId,
                                                 @PathVariable String isbn) {
        return commandGateway.send(new RemoveBookCommand(libraryId, isbn));
    }
}
