package tech.enfint.inbound.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.enfint.dto.PostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.exception.PostDoesntExistException;
import tech.enfint.service.post.PostService;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(produces = "application/json")
    public List<PostResponseDTO> getPosts() {
        throw new ArithmeticException();
//        return postService.getAllPosts();

    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PostResponseDTO> addPost(@RequestBody PostRequestDTO postRequestDTO)
            throws URISyntaxException, PostDoesntExistException {
        PostResponseDTO body = postService.savePost(postRequestDTO);

        return ResponseEntity.created(URI.create("/posts/" + body.getUuid())).body(body);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public List<PostResponseDTO> getPost(@PathVariable(name = "id") UUID id) {
        return postService.getAllPosts();
    }

    @PutMapping(path = "update/{id}", produces = "application/json", consumes = "application/json")
    public void updatePost(@PathVariable(name = "id") UUID id,
                           @RequestBody PostRequestDTO postRequestDTO) throws PostDoesntExistException {
        postService.updatePost(postRequestDTO, id);
    }

    @GetMapping(path = "findByCreationDate/{creationDate}", produces = "application/json")
    public List<PostResponseDTO> getPostsByCreationDate(
            @PathVariable(name = "creationDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime creationDate) {
        return postService.getPostsByCreationDate(creationDate);
    }

    @GetMapping(path = "findByUUID/{uuid}", produces = "application/json")
    public PostResponseDTO getPostByUUID(
            @PathVariable(name = "uuid") UUID uuid) throws PostDoesntExistException {
        return postService.getPostByUUID(uuid);
    }

    @GetMapping(path = "/postsByAutor", produces = "application/json")
    public List<PostResponseDTO> getPostsByAutor(@RequestParam(name = "name") String name,
                                                 @RequestParam(name = "surname") String surname) {
        return postService.getPostsByAutor(new Autor(name, surname));
    }

    @ExceptionHandler(value = PostDoesntExistException.class)
    public void postDoesntExistHandler()
    {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND);
    }

}//public class HomeController
