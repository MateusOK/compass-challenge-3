package br.com.pb.compass.challange3.repository;

import br.com.pb.compass.challange3.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
