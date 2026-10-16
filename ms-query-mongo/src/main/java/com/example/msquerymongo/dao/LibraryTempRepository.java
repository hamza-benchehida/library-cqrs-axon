package com.example.msquerymongo.dao;

import com.example.msquerymongo.entities.LibraryTemp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryTempRepository extends MongoRepository<LibraryTemp, String> {
}
