package site.metacoding.imagetest.domain;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private String title;
    private String content;
    private MultipartFile file;

    public Post toEntity(String imgurl) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setImgurl(imgurl);
        return post;
    }
}
