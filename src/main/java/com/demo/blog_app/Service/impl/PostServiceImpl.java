package com.demo.blog_app.Service.impl;

import com.demo.blog_app.Entity.Comment;
import com.demo.blog_app.Entity.Post;
import com.demo.blog_app.Exception.MyCustomException;
import com.demo.blog_app.Payload.CommentDto;
import com.demo.blog_app.Payload.PostDto;
import com.demo.blog_app.Payload.PostPageInfo;
import com.demo.blog_app.Repository.PostRepository;
import com.demo.blog_app.Service.PostService;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentServiceImpl impl;
    @Override
    public PostDto createPost(PostDto postDto) {
           Post post = this.mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
       return this.mapToDto(savedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
       postRepository.findById(postDto.getId())
                .orElseThrow(()->new MyCustomException("Post Not Found with id "+postDto.getId()));
        Post post = this.mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return this.mapToDto(savedPost);
    }

    @Override
    public PostDto partialUpdate(Integer id, PostDto postDto) {
       Post post = postRepository.findById(postDto.getId())
                .orElseThrow(()->new MyCustomException("Post Not Found with id "+postDto.getId()));
        if(postDto.getTitle()==null){
            postDto.setTitle(post.getTitle());
        } else if(postDto.getDescription()==null){
            postDto.setDescription(post.getDescription());
        } else if(postDto.getContent()==null){
            postDto.setContent(post.getContent());
        }
        Post post1 =this.mapToEntity(postDto);
       Post post2= postRepository.save(post1);
        return this.mapToDto(post2);
    }

    @Override
    public PostDto getPost(Integer id) {
      Post post =  postRepository.findById(id)
                .orElseThrow(()->new MyCustomException("Post Not Found with id "+ id));
        return  this.mapToDto(post);
    }

    @Override
    public PostPageInfo getAllPost(int pageNo, int pageSize, String sortBy,String sortDir) {
        Sort sort  = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> page = postRepository.findAll(pageable);
        List<Post> content = page.getContent();
        List<PostDto> postDto = content.stream().map(this::mapToDto).collect(Collectors.toList());
        return  PostPageInfo.builder()
                .posts(postDto)
                .getPageNumber(page.getNumber())
                .getPageSize(page.getSize())
                .getTotalElements(page.getTotalElements())
                .getTotalPages(page.getTotalPages())
                .hasPrevious(page.hasPrevious())
                .isLast(page.isLast()).build();

    }

    @Override
    public List<CommentDto> getCommentByPostId(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new MyCustomException("Post Not Found with id "+postId));
        List<Comment> comment = post.getComment();
        List<CommentDto> commentDtos = new ArrayList<>();
        comment.forEach(n-> {
            CommentDto commentDto = impl.mapToCommentDto(n);
            commentDtos.add(commentDto);
        });
        return commentDtos;
    }

    public  Post mapToEntity(PostDto postDto) {
    return mapper.map(postDto,Post.class);
    }
    public PostDto mapToDto(Post post){
       return mapper.map(post,PostDto.class);
    }
}
