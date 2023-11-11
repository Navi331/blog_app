package com.demo.blog_app.Service.impl;

import com.demo.blog_app.Entity.Comment;
import com.demo.blog_app.Entity.Post;
import com.demo.blog_app.Exception.MyCustomException;
import com.demo.blog_app.Payload.CommentDto;
import com.demo.blog_app.Repository.CommentRepository;
import com.demo.blog_app.Repository.PostRepository;
import com.demo.blog_app.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository repository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PostRepository postRepository;
    @Override
    public CommentDto postComment(Integer postId, CommentDto commentDto) {
        Post post =  postRepository.findById(postId)
                .orElseThrow(()->new MyCustomException("Post Not Found with id "+ postId));
            Comment comment = this.mapToCommentEntity(commentDto);
            comment.setPost(post);
           Comment savedComment =   repository.save(comment);
        return mapToCommentDto(savedComment);
    }

    public Comment mapToCommentEntity(CommentDto commentDto) {
       return mapper.map(commentDto,Comment.class);
    }
    public CommentDto mapToCommentDto(Comment comment) {
        return mapper.map(comment,CommentDto.class);
    }
}
