package tech.enfint.service.post;

import tech.enfint.dto.CreatePostRequestDTO;
import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.PostRepository;

public class CreatePost
{
    public static PostResponseDTO createPost (CreatePostRequestDTO createPostRequestDTO)
    {
        return PostMapper.postToPostResponseDto(
                PostRepository.save(PostMapper.postDtoToPost(createPostRequestDTO)));
    }

}
