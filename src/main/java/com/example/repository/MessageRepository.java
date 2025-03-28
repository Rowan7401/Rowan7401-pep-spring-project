package com.example.repository;

import com.example.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m FROM Message m WHERE m.postedBy = :postedBy")
    List<Message> findPostedBy(@Param("postedBy") Integer postedBy);

    @Query("SELECT m FROM Message m")
    List<Message> findAll();

    @Query("SELECT m FROM Message m WHERE m.messageId = :messageId")
    Message findById(@Param("messageId") Integer messageId);

    Message save(Message message);

    

}
