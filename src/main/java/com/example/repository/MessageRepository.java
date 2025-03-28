package com.example.repository;

import com.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findPostedBy(int postedBy);

}
