package com.udemystrudy.learnspringsecurity.resource;

import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final List<Todo> TODO_LIST = List.of(new Todo("hello", "Learn AWS"),
                new Todo("hello", "Get AWS Certified"));


    @GetMapping("/todos")
    public List<Todo>  retriedAllTodos(){
        return TODO_LIST;
    }

    @GetMapping("/users/{username}/todos")
    @PreAuthorize("hasRole('USER') and #username == authenication.name")
    @PostAuthorize("returnObject.username() == 'hello'")
    @RolesAllowed({"ADMIN", "USER"})
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public Todo  retriedTodosForASpecificUser(@PathVariable String username){
        return TODO_LIST.get(0);
    }

    @PostMapping("/users/{username}/todos")
    public void  creaiteTodosForASpecificUser(@PathVariable String username,
                                              @RequestBody Todo todo){
        logger.info("Creare {} for {}");
    }






}


record Todo (String username, String description) {}