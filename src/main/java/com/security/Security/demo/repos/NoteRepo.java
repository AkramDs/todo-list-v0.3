package com.security.Security.demo.repos;

import com.security.Security.demo.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
    Note findByTitle(String title);
    Note findByDescription(String description);
    Note findByAuthor(String author);

    List<Note> findAllByAuthor(String author);
    List<Note> findAllByTitleAndAuthor(String title, String author);
    List<Note> findAllByStatus(String status);

}
