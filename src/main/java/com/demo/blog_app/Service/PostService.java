package com.demo.blog_app.Service;

import com.demo.blog_app.Payload.CommentDto;
import com.demo.blog_app.Payload.PostDto;
import com.demo.blog_app.Payload.PostPageInfo;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    void deletePost(Integer postId);

    PostDto updatePost(PostDto postDto);

    PostDto partialUpdate(Integer id, PostDto postDto);

    PostDto getPost(Integer id);

    PostPageInfo getAllPost(int pageNo, int pageSize, String sortBy,String sortDir);

    List<CommentDto> getCommentByPostId(Integer postId);
}
