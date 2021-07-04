package fr.unice.polytech.cookieFactory.users;

public class Email {
    private String email;
    private String header;
    private String body;
    private String footer;

    public Email(String email, String header, String body, String footer){
        this.email = email;
        this.header = header;
        this.body = body;
        this.footer = footer;
    }
}
