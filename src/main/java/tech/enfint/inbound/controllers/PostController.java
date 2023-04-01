package tech.enfint.inbound.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import tech.enfint.dto.PostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.logging.FieldType;
import tech.enfint.logging.Logging;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.exception.PostDoesntExistException;
import tech.enfint.service.post.PostService;

import java.net.URI;
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

    @Logging(logTypes = {FieldType.ERROR})
    @GetMapping(produces = "application/json")
    public List<PostResponseDTO> getPosts() {
        return postService.getAllPosts();
    }

    @Logging(logTypes = {FieldType.ERROR})
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PostResponseDTO> addPost(@RequestBody @Valid  PostRequestDTO postRequestDTO)
            throws PostDoesntExistException, WebExchangeBindException {
        PostResponseDTO body = postService.savePost(postRequestDTO);

        return ResponseEntity.created(URI.create("/posts/" + body.getUuid())).body(body);
    }

    @Logging(logTypes = {FieldType.ERROR})
    @GetMapping(path = "/{id}", produces = "application/json")
    public List<PostResponseDTO> getPost(@PathVariable(name = "id") UUID id) {
        return postService.getAllPosts();
    }

    @Logging(logTypes = {FieldType.ERROR})
    @PutMapping(path = "update/{id}", produces = "application/json", consumes = "application/json")
    public void updatePost(@PathVariable(name = "id") UUID id,
                           @RequestBody @Valid PostRequestDTO postRequestDTO)
            throws PostDoesntExistException, WebExchangeBindException {
        postService.updatePost(postRequestDTO, id);
    }

    @Logging(logTypes = {FieldType.ERROR})
    @GetMapping(path = "findByCreationDate/{creationDate}", produces = "application/json")
    public List<PostResponseDTO> getPostsByCreationDate(
            @PathVariable(name = "creationDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime creationDate) {
        return postService.getPostsByCreationDate(creationDate);
    }

    @Logging(logTypes = {FieldType.ERROR})
    @GetMapping(path = "findByUUID/{uuid}", produces = "application/json")
    public PostResponseDTO getPostByUUID(
            @PathVariable(name = "uuid") UUID uuid) throws PostDoesntExistException {
        return postService.getPostByUUID(uuid);
    }

    @Logging(logTypes = {FieldType.ERROR})
    @GetMapping(path = "/postsByAutor", produces = "application/json")
    public List<PostResponseDTO> getPostsByAutor(@RequestParam(name = "name") String name,
                                                 @RequestParam(name = "surname") String surname) {
        return postService.getPostsByAutor(new Autor(name, surname));
    }

    @Logging(logTypes = {FieldType.ERROR})
    @ExceptionHandler(value = PostDoesntExistException.class)
    public void postDoesntExistHandler()
    {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND);
    }

}//public class HomeController
