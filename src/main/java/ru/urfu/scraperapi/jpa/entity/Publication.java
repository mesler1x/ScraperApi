package ru.urfu.scraperapi.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;

    @Column(nullable = false)
    private String text;

    private String url;

    private String author;

    @OneToOne(mappedBy = "publication")
    private Summary summary;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private Site site;

    @Column(name = "published_at")
    private Long publishedAt;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "is_fake")
    private Boolean isFake;
}
