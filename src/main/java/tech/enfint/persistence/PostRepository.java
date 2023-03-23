package tech.enfint.persistence;

import tech.enfint.dto.PostResponseDTO;
import tech.enfint.persistence.entity.Post;
import tech.enfint.service.post.PostMapper;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository
{
    private static final ConcurrentHashMap<UUID, Post> posts = new ConcurrentHashMap<>();

    public static Post save(Post post)
    {
        UUID uuid = UUID.randomUUID();

        post.setUuid(uuid);
        posts.put(uuid, post);
        return post;
    }

    public static ConcurrentHashMap<UUID, PostResponseDTO> getPosts()
    {
        ConcurrentHashMap<UUID, PostResponseDTO> response = new ConcurrentHashMap<>();

        posts.forEach((uuid, post) ->
        {
            response.put(uuid, PostMapper.postToPostResponseDto(post));
        });

        return response;
    }

}
