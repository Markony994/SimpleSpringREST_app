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
import tech.enfint.persistence.exception.PostException;
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
            throws PostException, WebExchangeBindException {
        PostResponseDTO body = postService.savePost(postRequestDTO);

        return ResponseEntity.created(URI.create("/posts/" + body.getUuid())).body(body);
    }

    @Logging(logTypes = {FieldType.ERROR})
    @GetMapping(path = {"by{id}", "by{creationDate}", "by{autorID}"}, produces = "application/json")
    public List<PostResponseDTO> getPost(@RequestParam(name = "id", required = false) UUID id,
                                         @RequestParam(name = "creationDate", required = false)
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime creationDate,
                                         @RequestParam(name = "autorID", required = false) UUID autorID)
            throws PostException
    {
        if(id != null)
        {
            return List.of(postService.getPostByUUID(id));
        }
        else if(creationDate != null)
        {
            return postService.getPostsByCreationDate(creationDate);
        }
        else
        {
            return postService.getPostsByAutor(autorID);
        }

    }

    @Logging(logTypes = {FieldType.ERROR})
    @PutMapping(path = "update/{id}", produces = "application/json", consumes = "application/json")
    public void updatePost(@PathVariable(name = "id") UUID id,
                           @RequestBody @Valid PostRequestDTO postRequestDTO)
            throws PostException, WebExchangeBindException {
        postService.updatePost(postRequestDTO, id);
    }

    @Logging(logTypes = {FieldType.ERROR})
    @ExceptionHandler(value = PostException.class)
    public void postExceptionHandler()
    {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND);
    }

}//public class HomeController
