package tech.enfint.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "Post", uniqueConstraints = {
        @UniqueConstraint(columnNames = "UUID")})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String text;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Autor autor;
    private LocalDateTime creationDate;

    public Post()
    {

    }

    public Post(String text, Autor autor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.text = text;
        this.autor = autor;
        creationDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Autor getAutor() {
        return autor;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "uuid=" + uuid +
                ", text='" + text + '\'' +
                ", autor=" + autor +
                ", creationDate=" + creationDate +
                '}';
    }
}//public class Post
