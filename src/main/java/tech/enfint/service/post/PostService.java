package tech.enfint.service.post;

import org.springframework.stereotype.Service;
import tech.enfint.dto.PostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.PostRepository;
import tech.enfint.persistence.entity.Post;
import tech.enfint.persistence.exception.PostException;

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
        Post _post = repository.getPostByUUID(uuid);

        return postMapper.postToPostResponseDto(_post);
    }

    public List<PostResponseDTO> getPostsByCreationDate(LocalDateTime creationDate) throws PostException {
        List<Post> _posts = repository.getPostsByCreationDate(creationDate);

        if(_posts.size() == 0)
        {
            throw new PostException("There's no posts created on that date.");
        }

        return _posts.stream()
                .map(postMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByAutor(UUID autorID) throws PostException {
        List<Post> _posts = repository.getPostsByAutorID(autorID);

        if(_posts.size() == 0)
        {
            throw new PostException("There's no author with that id.");
        }

        return _posts.stream()
                .map(postMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    public PostResponseDTO savePost(PostRequestDTO postRequestDTO) throws PostException {
        Post _post = repository.save(postMapper.postDtoToPost(postRequestDTO));

        return postMapper.postToPostResponseDto(_post);
    }

    public PostResponseDTO updatePost(PostRequestDTO postRequestDTO, UUID id) throws PostException {
        Post _post = repository.save(postMapper.postDtoToPost(postRequestDTO, id));

        return postMapper.postToPostResponseDto(_post);
    }

    public PostResponseDTO getPostByUUID(UUID uuid) throws PostException {
        Post _post = repository.getPostByUUID(uuid);

        if (_post == null) {
            throw new PostException("Post with that uuid doesn't exist");
        }

        return postMapper.postToPostResponseDto(_post);
    }

}
