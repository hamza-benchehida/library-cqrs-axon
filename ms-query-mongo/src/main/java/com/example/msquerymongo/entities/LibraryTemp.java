package com.example.msquerymongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "library_temp")
@Data @AllArgsConstructor @NoArgsConstructor
public class LibraryTemp {
    @Id
    private String libraryId;
    private String name;
}
