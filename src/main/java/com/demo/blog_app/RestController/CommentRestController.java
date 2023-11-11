package com.demo.blog_app.RestController;

import com.demo.blog_app.Payload.CommentDto;
import com.demo.blog_app.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/{postId}/comment")
public class CommentRestController {
    @Autowired
    private CommentService service;
    //http://localhost:8080/blog/1/comment/postComment
    @PostMapping("/postComment")
    public ResponseEntity<CommentDto> postComment(@PathVariable("postId") Integer postId,
                                                  @RequestBody CommentDto commentDto) {
     CommentDto dto = service.postComment(postId, commentDto);
     return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
