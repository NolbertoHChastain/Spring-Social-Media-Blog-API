package com.example.repository;

import com.example.entity.Account;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
