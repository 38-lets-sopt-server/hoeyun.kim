package org.sopt.domain;

import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String createdAt;
    private BoardType boardType;

    protected Post() {}

    public Post( String title, String content, User user, String createdAt, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.boardType = boardType;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public String getCreatedAt() { return createdAt; }
    public BoardType getBoardType() { return boardType; }

    public void update(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }

    public String getInfo() {
        return "[" + id + "] " + title + " - " + user + " (" + createdAt + ")\n" + content;
    }
}
