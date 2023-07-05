package com.rahulpateldev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    @GetMapping("/posts")
    public String getPosts() {
        return "Post fetched successfully!!";
    }

}
