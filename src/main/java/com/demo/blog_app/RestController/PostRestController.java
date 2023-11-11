package com.demo.blog_app.RestController;

import com.demo.blog_app.Constants.Pageable;
import com.demo.blog_app.Payload.CommentDto;
import com.demo.blog_app.Payload.PostDto;
import com.demo.blog_app.Payload.PostPageInfo;
import com.demo.blog_app.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/blog/post")
public class PostRestController {
    @Autowired
    private PostService postService;
    //http://localhost:8080/blog/post/create
    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/blog/post/12/delete
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted with id"+postId, HttpStatus.OK);
    }
    //http://localhost:8080/blog/post/update
    @PutMapping("/update")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto){
       PostDto dto = postService.updatePost(postDto);
       return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    //http://localhost:8080/blog/post/12/partialUpdate
    @PutMapping("/{postId}/partialUpdate")
    public ResponseEntity<PostDto>  partialUpdate(@PathVariable("postId") Integer id,
                                                  @RequestBody PostDto postDto){
                  PostDto dto =  postService.partialUpdate(id, postDto);
                  return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    //http://localhost:8080/blog/post/1/getPost
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{postId}/getPost")
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer id){
       PostDto postDto = postService.getPost(id);
        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }
    //http://localhost:8080/blog/post/getAllPosts?pageNo=1&pageSize=3&sortBy=id&sortDir=desc
    @GetMapping("/getAllPosts")
    public ResponseEntity<PostPageInfo> getAllPost
    (@RequestParam(value = "pageNo",defaultValue =Pageable.DEFAULT_PAGE_NUMBER,required = false ) int pageNo,
     @RequestParam(value = "pageSize",defaultValue =Pageable.DEFAULT_PAGE_SIZE,required = false) int pageSize,
     @RequestParam(value = "sortBy",defaultValue = Pageable.DEFAULT_SORT_By,required = false) String sortBy,
     @RequestParam(value = "sortDir",defaultValue = Pageable.DEFAULT_SORT_DIR,required = false) String sortDir){
        PostPageInfo post = postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(post, HttpStatus.FOUND);
    }

    //http://localhost:8080/blog/post/1/getComment
    @GetMapping("/{postId}/getComment")
    public ResponseEntity<?> getCommentByPostId(@PathVariable("postId") Integer postId){
        List<CommentDto> comments =postService.getCommentByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
