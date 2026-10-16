package com.example.msquerymongo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "library_book_view")
@Data @AllArgsConstructor @NoArgsConstructor
public class LibraryBookView {
    @Id
    private String isbn;
    private String libraryId;
    private String libraryName;
    private String bookTitle;
    private String editeurId;
}
