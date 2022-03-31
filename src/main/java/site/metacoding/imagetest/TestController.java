package site.metacoding.imagetest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.imagetest.domain.NoImgDto;
import site.metacoding.imagetest.domain.Post;
import site.metacoding.imagetest.domain.PostDto;
import site.metacoding.imagetest.domain.PostRepository;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final PostRepository postRepository;

    @GetMapping({ "/main", "/" })
    public String home() {
        return "main";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> postEntity = (List<Post>) postRepository.findAll();
        int postEntitySize = postEntity.size();

        for (int i = 0; i < postEntitySize; i++) {
            if (postEntity.get(i).getImgurl() == null) {
                postEntity.get(i).setImgChk(false);
            } else
                postEntity.get(i).setImgChk(true);
        }

        System.out.println("FindeAll : " + postEntity);
        System.out.println("FindeAll : " + postEntity.get(0).getImgurl());

        // boolean imgCheck;
        // if (postEntity.get(3).equals(null)) {
        // imgCheck = false;
        // } else {
        // imgCheck = true;
        // }

        model.addAttribute("post", postEntity);
        return "list";
    }

    @GetMapping("/writeForm")
    public String howriteFormme() {
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(PostDto PostDto, Model model, NoImgDto NoImgDto) {

        String requestFileName = PostDto.getFile().getOriginalFilename();

        if (requestFileName.isEmpty()) {
            // System.out.println("이미지없음 : " + NoImgDto);
            Post postEntity = postRepository.save(NoImgDto.toEntity());
            // System.out.println("이미지없는 Entity : " + postEntity);
            model.addAttribute("post", postEntity);
        } else {
            // System.out.println("이미지있음 : " + PostDto);
            UUID uuid = UUID.randomUUID();
            String imgurl = uuid + "_" + requestFileName;

            try {
                Path filePath = Paths.get("src/main/resources/static/upload/" + imgurl);
                Files.write(filePath, PostDto.getFile().getBytes());

                Post postEntity = postRepository.save(PostDto.toEntity(imgurl));
                // System.out.println("이미지있는 Entity : " + postEntity);
                model.addAttribute("post", postEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "main";
        }

        return "main";
    }
}
