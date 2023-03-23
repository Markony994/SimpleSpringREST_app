package tech.enfint.inbound.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import tech.enfint.dto.CreatePostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.PostRepository;
import tech.enfint.service.post.CreatePost;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class HomeController
{
    @GetMapping(path = "/", produces = "application/json")
    public ConcurrentHashMap<UUID, PostResponseDTO> getPosts()
    {
        ConcurrentHashMap<UUID, PostResponseDTO> posts = PostRepository.getPosts();

        return PostRepository.getPosts();
    }//public ConcurrentHashMap<UUID, PostResponseDTO> getPosts()

    @PostMapping(path = "/addPost",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAddPost(@RequestParam("text") String text,
                                   @RequestParam("autorName") String autorName,
                                   @RequestParam("autorSurname") String autorSurname)
    {

        CreatePost.createPost(new CreatePostRequestDTO(text, autorName, autorSurname));

        return new ModelAndView("forward:/");
    }

}//public class HomeController
