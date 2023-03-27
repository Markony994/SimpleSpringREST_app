package tech.enfint.inbound.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.enfint.dto.PostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.service.post.PostService;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController  {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = "application/json")
    public List<PostResponseDTO> getPosts()
    {

        return postService.getAllPosts();

    }//public ConcurrentHashMap<UUID, PostResponseDTO> getPosts()

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PostResponseDTO> addPost(@RequestBody PostRequestDTO postRequestDTO) throws URISyntaxException
    {
        PostResponseDTO body = postService.savePost(postRequestDTO);

        return ResponseEntity.created(new URI("http://localhost:8080/posts/" + body.getUuid())).body(body);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public List<PostResponseDTO> getPost(@PathVariable(name = "id")UUID id)
    {
        return postService.getAllPosts();
    }

    @PutMapping(path = "findByID/{id}", produces = "application/json")
    public void updatePost(@PathVariable(name = "id")UUID id, @RequestBody PostRequestDTO postRequestDTO)
    {
        postService.updatePost(postRequestDTO, id);
    }

    @GetMapping(path = "findByCreationDate/{creationDate}", produces = "application/json")
    public List<PostResponseDTO> getPostsByCreationDate(
            @PathVariable(name = "creationDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime creationDate)
    {

        return postService.getPostsByCreationDate(creationDate);
    }

    @GetMapping(path = "/postsByAutor", produces = "application/json")
    public List<PostResponseDTO> getPostsByAutor(@RequestParam(name = "name") String name,
                                                 @RequestParam(name = "surname") String surname)
    {
        return postService.getPostsByAutor(new Autor(name, surname));
    }

}//public class HomeController
