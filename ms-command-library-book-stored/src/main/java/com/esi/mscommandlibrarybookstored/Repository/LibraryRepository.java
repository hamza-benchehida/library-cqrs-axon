package com.esi.mscommandlibrarybookstored.Repository;

import com.esi.mscommandlibrarybookstored.Aggregate.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LibraryRepository extends JpaRepository<Library, String> {
}
