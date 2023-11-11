package com.demo.blog_app.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class MyCustomException extends RuntimeException{
    public MyCustomException(String message){
        super(message);
    }
}
