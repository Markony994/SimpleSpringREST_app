package tech.enfint.service.post;

import org.springframework.stereotype.Component;
import tech.enfint.dto.PostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.entity.Post;

import java.util.UUID;

@Component
public class PostMapper {

    public Post postDtoToPost(PostRequestDTO postRequestDTO) {
        return new Post(
                postRequestDTO.getText(),
                new Autor(
                        postRequestDTO.getAutorName(),
                        postRequestDTO.getAutorSurname()
                ));
    }

    public Post postDtoToPost(PostRequestDTO postRequestDTO, UUID id) {
        var postResult = postDtoToPost(postRequestDTO);
        postResult.setUuid(id);

        return postResult;
    }

    public PostResponseDTO postToPostResponseDto(Post post) {
        return new PostResponseDTO(
                post.getAutor().getUuid(),
                post.getText(),
                post.getUuid(),
                post.getAutor().getName(),
                post.getAutor().getSurname(),
                post.getCreationDate());
    }

}
