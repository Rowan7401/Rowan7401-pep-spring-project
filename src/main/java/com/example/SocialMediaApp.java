package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import static org.springframework.boot.SpringApplication.run;

/**
 * This is a class that is used to run your application.
 *
 * DO NOT CHANGE ANYTHING IN THIS CLASS
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class SocialMediaApp {
    /**
     * Runs the application
     * @param args The arguments of the program.
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new SpringApplicationBuilder(SocialMediaApp.class)
            .web(WebApplicationType.NONE)
            .run(args);

        // Now you can manually test service or repository methods
        AccountService accountService = context.getBean(AccountService.class);
        MessageService messageService = context.getBean(MessageService.class);

        // Example: Create and save an account
        Account newAccount = new Account();
        newAccount.setUsername("manualTestUser");
        newAccount.setPassword("password123");

        boolean registered = accountService.register(newAccount);
        System.out.println("Saved account ID: " + newAccount.getAccountId() + " worked...?  " + registered );

        // Example: Create a message
        Message msg = new Message();
        msg.setMessageText("Hello from manual test!");
        msg.setPostedBy(newAccount.getAccountId());

        Message savedMsg = messageService.createMessage(msg);
        System.out.println("Saved message ID: " + savedMsg.getMessageId());
        System.out.println("Message text: " + msg.getMessageText());

        // // Creating message with invalid account ID
        // Message badMsg = new Message();
        // badMsg.setMessageText("Hello from manual test!");
        // Integer invalidID = 192038;
        // badMsg.setPostedBy(invalidID);

        // Message attBadMsg = messageService.createMessage(badMsg);
        // System.out.println("Bad message ID: " + attBadMsg.getMessageId());
        // System.out.println("Message text: " + attBadMsg.getMessageText());

        // Updating message
        // System.out.println();
        // Message updated = messageService.updateMessage(2, "Mole Skunk!");
        // System.out.println("Updated message with ID: " + updated.getMessageId() + " to " + updated);

        // Invalid message update
        System.out.println();
        Message badUpdated = messageService.updateMessage(2345, "Mole Skunk!");
        System.out.println("Updated message with ID: " + badUpdated.getMessageId() + " to " + badUpdated);

    }

}
