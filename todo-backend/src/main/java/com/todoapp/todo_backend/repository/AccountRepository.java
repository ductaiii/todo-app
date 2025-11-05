package com.todoapp.todo_backend.repository;

import com.todoapp.todo_backend.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  // Custom query methods if needed

  @Query("select distinct a from Account a left join fetch a.tasks left join fetch a.comments")
  List<Account> findAllWithChildren();

  Optional<Account> findByUsername(String username);
  Optional<Account> findByEmail(String email);
}
