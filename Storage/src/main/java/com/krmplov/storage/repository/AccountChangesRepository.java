package com.krmplov.storage.repository;

import com.krmplov.storage.model.AccountChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountChangesRepository extends JpaRepository<AccountChanges, Long> {
}
