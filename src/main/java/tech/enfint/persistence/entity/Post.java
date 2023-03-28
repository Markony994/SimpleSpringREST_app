package tech.enfint.persistence.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Post {
    private String text;
    private UUID uuid;
    private Autor autor;
    private LocalDateTime creationDate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Post(String text, Autor autor) {
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
                "text='" + text + '\'' +
                ", uuid=" + uuid +
                ", autor=" + autor +
                ", creationDate=" + creationDate +
                '}';
    }

}//public class Post
