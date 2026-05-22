package org.sopt.domain;

import jakarta.persistence.*;

@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    protected Post() {}

    public Post(String title, String content, User user, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.boardType = boardType;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public User getUser() { return user; }
    public BoardType getBoardType() { return boardType; }

    public void update(String title, String content, BoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }

    public String getInfo() {
        return "[" + id + "] " + title + " - " + user + " (" + getCreatedAt() + ")\n" + content;
    }
}
