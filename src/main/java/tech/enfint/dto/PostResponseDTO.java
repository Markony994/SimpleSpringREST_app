package tech.enfint.dto;

import java.util.Date;
import java.util.UUID;

public class PostResponseDTO
{
    private String text;
    private UUID uuid;
    private String name;
    private String surname;
    private Date creationDate;

    public PostResponseDTO(String text, UUID uuid, String name, String surname, Date creationDate) {
        this.text = text;
        this.uuid = uuid;
        this.name = name;
        this.surname = surname;
        this.creationDate = creationDate;
    }

    public String getText() {
        return text;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
