package com.demo.blog_app.Payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPageInfo {
    private List<PostDto> posts;
   private int getPageNumber;
   private long getPageSize;
    private int getTotalPages;
    private long getTotalElements;
    private  boolean hasPrevious;
    private  boolean isLast;

}
