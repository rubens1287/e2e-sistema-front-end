package br.com.pom.jsonplaceholder;

public class Book {

    private Long userId;
    private Long id;
    private String title;
    private boolean status;

    public Book(Long userId, boolean status, String title, Long id) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
