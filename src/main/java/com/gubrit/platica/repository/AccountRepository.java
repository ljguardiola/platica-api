package com.gubrit.platica.repository;

import com.gubrit.platica.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

  Optional<Account> findByIdAndUserId(UUID id, UUID userId);

  List<Account> findAllByUserId(UUID userId);

  long deleteByIdAndUserId(UUID id, UUID userId);

}
