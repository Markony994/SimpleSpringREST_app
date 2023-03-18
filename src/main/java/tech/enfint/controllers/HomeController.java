package tech.enfint.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.enfint.model.Autor;
import tech.enfint.model.Post;

import java.util.ArrayList;

@RestController
public class HomeController
{
    private static ArrayList<Post> posts;

    @GetMapping(path = "/", produces = "application/json")
    public ArrayList<Post> getPosts()
    {
        posts = new ArrayList<>();

        Autor a1 = new Autor("Marko", "Marković");
        Post p1 = new Post("Post1", a1);

        Autor a2 = new Autor("Milos", "Milošević");
        Post p2 = new Post("Post2", a2);

        Autor a3 = new Autor("Svetozar", "Svetozarević");
        Post p3 = new Post("Post2", a3);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        return posts;
    }//public ArrayList<Post> getPosts()

    @GetMapping(path = "/addPost",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ArrayList<Post> getAddPost(@RequestBody String text, @RequestBody Autor autor)
    {
        Autor a = new Autor(autor.getName(), autor.getSurname());
        Post p = new Post(text, a);
        posts.add(p);

        return posts;
    }

}//public class HomeController
