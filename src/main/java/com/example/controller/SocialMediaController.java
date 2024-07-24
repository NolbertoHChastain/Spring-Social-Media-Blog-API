package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Handler registers new {@code Account} from {@code @RequestBody}
     * 
     * @param newAccount the new {@code Account} to register
     * @return {@code ResponseEntity} containing newly registered {@code Account}
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        Account addedAccount = accountService.register(newAccount);
        return ResponseEntity.ok().body(addedAccount); // 200 - success
    }

}