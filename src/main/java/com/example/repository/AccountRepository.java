package com.example.repository;

import com.example.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

}
