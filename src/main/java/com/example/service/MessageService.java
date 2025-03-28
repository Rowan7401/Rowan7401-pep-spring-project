package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.*;

import java.lang.StackWalker.Option;
import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message) {

        if (message.getMessageText().length() > 255) {
            System.out.println("Message text is too long. Please create message with less that 255 characters");
            return null;
        }
        else if (message.getMessageText().length() == 0) {
            System.out.println("Message text is blank. Please try again with valid message text");
            return null;
        }
        else {
            return messageRepository.save(message);

        }

        
    }

    public List<Message> getAllMessages() {
        List<Message> allMessages = messageRepository.findAll();
        
        if (allMessages.size() == 0) {
            System.out.println("There were no messages on the App.");
            return null;
        }
        else {
            return allMessages;
        }
    }

    public Message getMessageById(Integer messageId) {
        Message existingM = messageRepository.getById(messageId);

        if (existingM == null) {
            System.out.println("No message with that ID found. Try again with another ID.");
            return null;
        }
        else {
            return existingM;
        }
    }

    public boolean deleteMessage(Integer messageId) {
        Message existingM = messageRepository.findById(messageId);

        if (existingM != null) {
            messageRepository.deleteById(messageId);
            return true;
        }
        else {
            System.out.println("No message with that ID to delete.");
            return false;
        }
        
    }

    public Message updateMessage(Integer messageId, String newMessageText) {
        Message existingM = messageRepository.findById(messageId);

        if (existingM != null) {
            existingM.setMessageText(newMessageText);
            return messageRepository.save(existingM);
        }
        else {
            System.out.println("No message with that ID to delete.");
            return null;
        }
    }

    public List<Message> getMessagesByUserId(Integer userId) {
        Account account = accountRepository.findById(userId);

        if (account != null) {
            List<Message> messages = messageRepository.findPostedBy(userId);

            if (messages.size() == 0) {
                System.out.println("This user has no messages.");
                return null;
            }
            else {
                return messages;
            }
            
        }
        else {
            System.out.println("This ID does not exist. Try to search for another user ID");
            return null;
        }

        
        
        
    }



}
