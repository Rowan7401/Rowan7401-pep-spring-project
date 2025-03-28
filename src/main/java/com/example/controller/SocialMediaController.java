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
@RequestMapping("/api")
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
            return ResponseEntity.ok(account);
        }
        else {
            System.out.println("Account failed to register. Clientside error");
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Optional<Account> loggedInAcc = accountService.login(account);

        if (loggedInAcc.isPresent()) {
            return ResponseEntity.ok(loggedInAcc.get());
        }
        else {
            System.out.println("Account failed to login. Clientside error.");
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageId(messageId);

        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        }
        else {
            System.out.println("No message with this ID found. Please try again");
            return ResponseEntity.notFound().build();

        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Message> updateMessage(@PathVariable int messageId, @RequestBody String newMessageText) {
        Message updatedMessage = messageService.updateMessage(messageId, newMessageText);
        
        if (updatedMessage != null) {
            return ResponseEntity.ok(updatedMessage);
        }
        else {
            System.out.println("There seems to be no message with this ID to update. Or, updated text is invalid.");
            System.out.println("Please try again");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByUserId(accountId);
        return ResponseEntity.ok(messages);
    } 
}
