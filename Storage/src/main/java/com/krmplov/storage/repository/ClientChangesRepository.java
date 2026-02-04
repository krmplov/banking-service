package com.krmplov.storage.repository;

import com.krmplov.storage.model.ClientChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChangesRepository extends JpaRepository<ClientChanges, Long> {
}
