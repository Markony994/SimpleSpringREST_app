package tech.enfint.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Autor", uniqueConstraints = {
        @UniqueConstraint(columnNames = "UUID")})
public class Autor
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;
    private String name;
    private String surname;

    public Autor()
    {

    }

    public Autor(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

    @Override
    public String toString() {
        return "Autor{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
