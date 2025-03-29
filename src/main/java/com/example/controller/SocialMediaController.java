package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.*;
import com.example.service.*;

import java.util.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        boolean registered = accountService.register(account);

        if (registered) {
            return ResponseEntity.status(200).body(account);
        }
        else {
            System.out.println("Account failed to register. Clientside error");
            return ResponseEntity.status(409).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Optional<Account> loggedInAcc = accountService.login(account);

        if (loggedInAcc.isPresent()) {
            return ResponseEntity.status(200).body(loggedInAcc.get());
        }
        else {
            System.out.println("Account failed to login. Clientside error.");
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Integer userId =  message.getPostedBy();
            Account user = accountService.getById(userId);

            if (user != null) {
                Message createdMessage = messageService.createMessage(message);
            
                if (createdMessage != null) {
                    return ResponseEntity.status(200).body(createdMessage);
                }
                else {
                    System.out.println("Invalid message attempted to create. Error, please try again.");
                    return ResponseEntity.status(400).body(null);
                }

            }
            else {
                System.out.println("User Id invalid. No user with that ID exists in DB.");
                return ResponseEntity.status(400).body(null);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        try {
            Message message = messageService.getMessageById(messageId);

            if (message != null) {
                return ResponseEntity.status(200).body(message);
            }
            else {
                System.out.println("No message with this ID found. Please try again");
                return ResponseEntity.status(200).body(null);

            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Boolean> deleteMessage(@PathVariable Integer messageId) {
        boolean deleted = messageService.deleteMessage(messageId);

        if (deleted) {
            return ResponseEntity.status(200).body(deleted);
        }
        else {
            System.out.println("No message with this ID found to delete. Please try again.");
            return ResponseEntity.status(200).body(null);

        }
       
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Message> updateMessage(@PathVariable Integer messageId, @RequestBody String newMessageText) {
        try {
            Message updatedMessage = messageService.updateMessage(messageId, newMessageText);
        
            if (updatedMessage != null) {
                if (updatedMessage.getMessageText().length() > 255) {
                    System.out.println("Updated message too long. Please try again with a text less than 255 characters.");
                    return ResponseEntity.status(400).body(null);
                }
                else if (updatedMessage.getMessageText().length() == 0) {
                    System.out.println("Updated message text blank. Please try again with valid input.");
                    return ResponseEntity.status(400).body(null);
                }
                else {
                    return ResponseEntity.status(200).body(updatedMessage);
                }
                
            }
            else {
                System.out.println("There seems to be no message with this ID to update.");
                System.out.println("Please try again");
                return ResponseEntity.status(400).body(null);
            }

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId) {
        try {
            List<Message> messages = messageService.getMessagesByUserId(accountId);

            if (messages.size() == 0) {
                System.out.println("This user seems to have no messages.");
                return ResponseEntity.status(200).body(null);
            }
            else {
                return ResponseEntity.status(200).body(messages);

            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        
        
    } 
}
