package tech.enfint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostResponseDTO
{
    private String text;
    private UUID uuid;
    private String name;
    private String surname;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    public PostResponseDTO(String text, UUID uuid, String name, String surname, LocalDateTime creationDate)
    {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
