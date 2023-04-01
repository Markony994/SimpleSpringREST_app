package tech.enfint.dto;

import jakarta.validation.constraints.Pattern;

public class PostRequestDTO {
    @Pattern(regexp = "^\\S+(\\n.*)*", message = "Text cant be empty!")
    private String text;
    @Pattern(regexp = "^[A-Z]{1}([\\-\\']?[a-z]+)*$",
            message = "Author name must start with capital letter\n" +
                    "Allowed special characters: - and '\n" +
                    "Special characters can't be 2 times in row in name\n" +
                    "Name can't end with special character")
    private String autorName;
    @Pattern(regexp = "^[A-Z]{1}([\\-\\']?[a-z]+)*$",
            message = "Author surname must start with capital letter\n" +
                    "Allowed special characters: - and '\n" +
                    "Special characters can't be 2 times in row in surname\n" +
                    "Surname can't end with special character")
    private String autorSurname;

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
