package ru.urfu.scraperapi.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private OffsetDateTime date;

    @Column(name = "url")
    private String url;

    @Column(name = "author")
    private String author;

    @Column(name = "summary")
    private String summary;

    @Column(name = "topic")
    private String topic;
}
