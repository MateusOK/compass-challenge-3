package br.com.pb.compass.challange3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class History {

    public History(LocalDateTime date, String status, Post post) {
        this.date = date;
        this.status = status;
        this.post = post;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @Column(name = "history_date")
    private LocalDateTime date;

    @Column(name = "history_status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}