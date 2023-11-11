package com.demo.blog_app.Service;

import com.demo.blog_app.Entity.Comment;
import com.demo.blog_app.Payload.CommentDto;

public interface CommentService {

    CommentDto postComment(Integer postId, CommentDto commentDto);
}
