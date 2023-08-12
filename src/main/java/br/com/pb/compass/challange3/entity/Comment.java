package br.com.pb.compass.challange3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
public class Comment {

    @Id
    @Column(name = "comments_id")
    private Long id; // Use the same name for the ID as in the API response

    @Column(name = "comments_name")
    private String name;

    @Column(name = "comments_body", length = 270)
    private String body;

    @Column(name = "comments_email")
    private String email;

    private Long postId;

}