package tech.enfint.persistence;

import org.springframework.stereotype.Component;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PostRepository
{
    private final ConcurrentHashMap<UUID, Post> posts = new ConcurrentHashMap<>();

    public Post save(Post post)
    {
        if(post.getUuid() == null)
        {
            UUID uuid = UUID.randomUUID();
            post.setUuid(uuid);

            posts.put(uuid, post);
        }
        else
        {
            posts.computeIfPresent(post.getUuid(), (k,v) -> post);
        }

        return post;
    }

    public List<Post> getAllPosts()
    {
        return new ArrayList<>(posts.values());
    }

    public Post getPost(UUID uuid)
    {
        return  posts.get(uuid);
    }

    public List<Post> getPostsByCreationDate(LocalDateTime  creationDate)
    {
        return posts.values().stream()
                .filter(post ->
                        post.getCreationDate().equals(creationDate))
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByAutor(Autor autor)
    {
        return posts.values().stream()
                .filter(post ->
                        post.getAutor().getName().equals(autor.getName()) &&
                                post.getAutor().getSurname().equals(autor.getSurname()))
                .collect(Collectors.toList());
    }

    public Post getPostByUUID(UUID uuid)
    {

        return  posts.get(uuid);

    }

}
