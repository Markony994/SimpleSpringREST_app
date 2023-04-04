package tech.enfint.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.enfint.persistence.entity.Autor;
import tech.enfint.persistence.entity.Post;
import tech.enfint.persistence.exception.PostDoesntExistException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class PostRepository {
    @Autowired
    private SessionFactory sessionFactory;
    private final ConcurrentHashMap<UUID, Post> posts = new ConcurrentHashMap<>();


    public Post save(Post post) throws PostDoesntExistException
    {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Post _post;

        if (post.getUuid() == null)
        {
            try
            {
                tx = session.beginTransaction();
                session.persist(post);
                tx.commit();
            }
            catch (HibernateException e)
            {
                if (tx!=null)
                {
                    tx.rollback();
                }

                e.printStackTrace();
            }
            finally
            {
                session.close();
            }

        }
        else if((_post = sessionFactory.getCurrentSession().get(Post.class, post.getUuid())) != null)
        {
            try
            {
                tx = session.beginTransaction();

                _post.setAutor(post.getAutor());
                _post.setText(post.getText());

                session.merge(_post);

                tx.commit();
            }
            catch (HibernateException e)
            {
                if (tx!=null)
                {
                    tx.rollback();
                }

                e.printStackTrace();
            }
            finally
            {
                session.close();
            }

        }
        else
        {
            if(session.isOpen())
            {
                session.close();
            }

            throw new PostDoesntExistException("Post with that uuid doesn't exist");
        }

        return post;
    }

    public List<Post> getAllPosts()
    {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Post> posts = session.createQuery("FROM Post", Post.class).list();
//            List<Autor> autors = session.createQuery("FROM Autor", Autor.class).list();
//
//            for (Iterator iterator = posts.iterator(); iterator.hasNext();){
//                Post post = (Post) iterator.next();
//
//            }


            tx.commit();

            return posts;

        }
        catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return null;
    }

    public Post getPost(UUID uuid) {
        return posts.get(uuid);
    }

    public List<Post> getPostsByCreationDate(LocalDateTime creationDate) {
        return posts.values().stream()
                .filter(post ->
                        post.getCreationDate().equals(creationDate))
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByAutor(Autor autor) {
        return posts.values().stream()
                .filter(post ->
                        post.getAutor().getName().equals(autor.getName()) &&
                                post.getAutor().getSurname().equals(autor.getSurname()))
                .collect(Collectors.toList());
    }

    public Post getPostByUUID(UUID uuid) {

        return posts.get(uuid);

    }

}
