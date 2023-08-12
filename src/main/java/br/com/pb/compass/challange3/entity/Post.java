package br.com.pb.compass.challange3.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post{

    @Id
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_body")
    private String body;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<History> history = new ArrayList<>();

}