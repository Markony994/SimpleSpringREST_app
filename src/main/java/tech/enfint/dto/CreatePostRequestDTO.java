package tech.enfint.dto;

public class CreatePostRequestDTO
{
    private String text;
    private String autorName;
    private String autorSurname;

    public CreatePostRequestDTO(String text, String autorName, String autorSurname)
    {
        this.text = text;
        this.autorName = autorName;
        this.autorSurname = autorSurname;
    }

    public String getText() {
        return text;
    }

    public String getAutorName() {
        return autorName;
    }

    public String getAutorSurname() {
        return autorSurname;
    }



}
