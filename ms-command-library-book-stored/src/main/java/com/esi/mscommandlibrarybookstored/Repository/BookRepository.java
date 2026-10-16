package com.esi.mscommandlibrarybookstored.Repository;

import com.esi.mscommandlibrarybookstored.Aggregate.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, String> {
}
