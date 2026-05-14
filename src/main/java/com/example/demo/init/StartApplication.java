package com.example.demo.init;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StartApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        User user = repository.findByUsername("admin");

        if(user==null){
            user = new User();
            user.setName("ADMIN");
            user.setUsername("admin");
            user.setPassword("admin123");
            user.getRoles().add("ADMIN");
            repository.save(user);
        }

        user = repository.findByUsername("user");
        if(user==null){
            user = new User();
            user.setName("USER");
            user.setUsername("user");
            user.setPassword("user123");
            user.getRoles().add("USER");
            repository.save(user);

        }
    }
}
