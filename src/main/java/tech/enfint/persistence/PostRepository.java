package tech.enfint.persistence;

import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.enfint.persistence.entity.Post;
import tech.enfint.persistence.exception.PostException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
public class PostRepository {
    @Autowired
    private SessionFactory sessionFactory;
    //private final ConcurrentHashMap<UUID, Post> posts = new ConcurrentHashMap<>();

    public Post save(Post post) throws PostException
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

            throw new PostException("Post with that uuid doesn't exist");
        }

        return post;
    }

    public List<Post> getAllPosts()
    {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Post> posts = null;

        try
        {
            tx = session.beginTransaction();
            posts = session.createQuery("FROM Post", Post.class).list();

            tx.commit();
        }
        catch (HibernateException e) {
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

        return posts;
    }

    public List<Post> getPostsByCreationDate(LocalDateTime creationDate) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Post> posts = null;

        try
        {
            tx = session.beginTransaction();
            posts = session.createQuery("FROM Post WHERE creationDate = :date", Post.class)
                    .setParameter("date", creationDate)
                    .list();

            tx.commit();
        }
        catch (HibernateException e) {
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

        return posts;
    }

    public List<Post> getPostsByAutorID(UUID autorID) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Post> posts = null;

        try
        {
            tx = session.beginTransaction();
            posts = session.createQuery("SELECT p FROM Post as p join fetch Autor WHERE p.autor.id = :autorID", Post.class)
                    .setParameter("autorID", autorID)
                    .list();

            tx.commit();
        }
        catch (HibernateException e) {
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

        return posts;
    }

    public Post getPostByUUID(UUID uuid) {

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Post post = null;

        try
        {
            tx = session.beginTransaction();
            var query = session.createQuery("FROM Post WHERE uuid = :uiid ", Post.class);
            query.setParameter("uiid", uuid);

            post = query.uniqueResult();

            tx.commit();
        }
        catch (HibernateException e) {
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

        return post;

    }

}
