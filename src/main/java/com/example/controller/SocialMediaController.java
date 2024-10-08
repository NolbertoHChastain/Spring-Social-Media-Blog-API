package com.example.controller;

import java.util.List;
import javax.security.sasl.AuthenticationException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController // @Controller + @ResponseBody (default to all handlers)
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Handler registers new {@code Account}
     * 
     * @param newAccount the new {@code Account} to register
     * @return {@code ResponseEntity} containing newly registered {@code Account}
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        Account addedAccount = accountService.register(newAccount);
        return ResponseEntity.ok().body(addedAccount); // 200 - success
    }

    /**
     * handler verifies login given {@code account}
     * 
     * @param account the {@code Account} to verify
     * @return {@code ResponseEntity} containing existing {@code Account}
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException {
        Account existingAccount = accountService.login(account);
        return ResponseEntity.ok().body(existingAccount);
    }

    /**
     * handler adds new {@code Message}
     * 
     * @param message to add
     * @return added {@code Message}
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        return ResponseEntity.ok().body(messageService.addMessage(message));
    }

    /**
     * handler gets all {@code Message} records
     * 
     * @return all {@code Message} records
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok().body(messageService.getAllMessages());
    }


    /**
     * Get {@code Message} by {@code messageId}
     * 
     * @param messageId
     * @return the {@code Message} given by {@code messageId}
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.ok().body(messageService.getMessageById(messageId));
    }

    /**
     * Delete {@code Message} by {@code messageId}
     * @param messageId
     * @return updated rows (1) if existed, otherwise empty
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMesageById(@PathVariable Integer messageId) {
        int rowsDeleted = messageService.deleteMessageById(messageId);
        return rowsDeleted == 1 ? ResponseEntity.ok().body(1) : ResponseEntity.ok().build();
    }

    /**
     * Patch {@code messageText} for existing {@code Message} record, given {@code messageId}
     * 
     * @param messageId
     * @param message
     * @return updated rows (1), otherwise empty
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessageTextbyId(@PathVariable Integer messageId, @RequestBody Message message) {
        message.setMessageId(messageId);
        int updatedRows = messageService.updateMessageTextById(message);
        return ResponseEntity.ok().body(updatedRows);
    }


    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesPostedBy(@PathVariable Integer accountId) {
        return ResponseEntity.ok().body(messageService.getAllMessagesPostedBy(accountId));
    }
}