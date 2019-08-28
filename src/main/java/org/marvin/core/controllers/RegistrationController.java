package org.marvin.core.controllers;

import org.marvin.core.impls.repositories.AccountRepository;
import org.marvin.core.models.Account;
import org.marvin.core.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

        private AccountRepository accountRepository;

        @GetMapping("/registration")
        public String registration(){
            return "registration";
        }

        @PostMapping("/registration")
        public String registration(Account account){

            Account ant = accountRepository.findByLogin(account.getUsername());

            System.out.println(ant);

            if(ant != null){
                return "registration";
            }

            account.setActive(true);
            account.setRoles(Collections.singletonList(Role.USER));

            accountRepository.createAccount(account);

            return "redirect:/login";
        }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
