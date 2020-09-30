package pl.edu.ug.squat_notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.squat_notes.domain.Chatbox;

import java.util.List;

@Repository
public interface ChatboxRepository extends JpaRepository<Chatbox, Long> {

    List<Chatbox> findAllByUserId(Long id);


}
