package tech.enfint.service.post;

import org.springframework.stereotype.Service;
import tech.enfint.dto.PostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.PostRepository;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.entity.Post;
import tech.enfint.persistence.exception.PostDoesntExistException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository repository;
    private final PostMapper postMapper;

    public PostService(PostRepository repository, PostMapper postMapper) {
        this.repository = repository;
        this.postMapper = postMapper;
    }

    public List<PostResponseDTO> getAllPosts() {
        return repository.getAllPosts().stream()
                .map(postMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDTO getPost(UUID uuid) {
        Post _post = repository.getPost(uuid);

        return postMapper.postToPostResponseDto(_post);
    }

    public List<PostResponseDTO> getPostsByCreationDate(LocalDateTime creationDate) {
        List<Post> _posts = repository.getPostsByCreationDate(creationDate);

        return _posts.stream()
                .map(postMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByAutor(Autor autor) {
        List<Post> _posts = repository.getPostsByAutor(autor);

        return _posts.stream()
                .map(postMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDTO savePost(PostRequestDTO postRequestDTO) throws PostDoesntExistException {
        Post _post = repository.save(postMapper.postDtoToPost(postRequestDTO));

        return postMapper.postToPostResponseDto(_post);
    }

    public PostResponseDTO updatePost(PostRequestDTO postRequestDTO, UUID id) throws PostDoesntExistException {
        Post _post = repository.save(postMapper.postDtoToPost(postRequestDTO, id));

        return postMapper.postToPostResponseDto(_post);
    }

    public PostResponseDTO getPostByUUID(UUID uuid) throws PostDoesntExistException {
        Post _post = repository.getPostByUUID(uuid);

        if (_post == null) {
            throw new PostDoesntExistException("Post with that uuid doesn't exist");
        }

        return postMapper.postToPostResponseDto(_post);
    }

}
