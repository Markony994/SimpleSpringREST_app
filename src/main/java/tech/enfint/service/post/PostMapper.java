package tech.enfint.service.post;

import tech.enfint.dto.CreatePostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.entity.Post;

public class PostMapper
{
    public static Post postDtoToPost(CreatePostRequestDTO createPostRequestDTO)
    {
        return new Post(
                createPostRequestDTO.getText(),
                new Autor(
                        createPostRequestDTO.getAutorName(),
                        createPostRequestDTO.getAutorSurname()
                ));
    }

    public static PostResponseDTO postToPostResponseDto(Post post)
    {
        return new PostResponseDTO(
                post.getText(),
                post.getUuid(),
                post.getAutor().getName(),
                post.getAutor().getSurname(),
                post.getCreationDate());
    }



}
