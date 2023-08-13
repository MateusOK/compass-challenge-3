package br.com.pb.compass.challange3.repository;

import br.com.pb.compass.challange3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
