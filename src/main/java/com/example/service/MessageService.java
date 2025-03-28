package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.*;
import com.example.repository.MessageRepository;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageId(int messageId) {
        return messageRepository.findById(messageId);
    }

    public boolean deleteMessage(int messageId) {
        Optional<Message> existingM = messageRepository.findById(messageId);

        if (existingM.isPresent()) {
            messageRepository.deleteById(messageId);
            return true;
        }
        else {
            return false;
        }
        
    }

    public Message updateMessage(int messageId, String newMessageText) {
        Optional<Message> messageOpt = messageRepository.findById(messageId);

        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            message.setMessageText(newMessageText);
            return messageRepository.save(message);
        }
        else {
            return null;
        }
    }

    public List<Message> getMessagesByUserId(int userId) {
        return messageRepository.findPostedBy(userId);
    }



}
