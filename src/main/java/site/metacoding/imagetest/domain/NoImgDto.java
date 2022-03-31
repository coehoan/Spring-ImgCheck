package site.metacoding.imagetest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoImgDto {
    private String title;
    private String content;

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        return post;
    }
}
