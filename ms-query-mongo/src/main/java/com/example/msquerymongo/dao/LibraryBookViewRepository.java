package com.example.msquerymongo.dao;

import com.example.msquerymongo.entities.LibraryBookView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryBookViewRepository extends MongoRepository<LibraryBookView, String> {
    List<LibraryBookView> findByLibraryId(String libraryId);
}
