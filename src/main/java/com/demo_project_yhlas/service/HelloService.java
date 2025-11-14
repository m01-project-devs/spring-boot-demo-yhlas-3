package com.demo_project_yhlas.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String generateGreeting(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return "Hello " + name.trim();
    }
}
