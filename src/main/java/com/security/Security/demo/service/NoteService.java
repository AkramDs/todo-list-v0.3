package com.security.Security.demo.service;

import com.security.Security.demo.domain.Note;
import com.security.Security.demo.domain.User;
import com.security.Security.demo.exception.NoteNotFoundException;
import com.security.Security.demo.repos.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    NoteRepo noteRepo;

    public Iterable<Note> findAllNoteByAuthor(String author) throws NoteNotFoundException {
        return noteRepo.findAllByAuthor(author);
    }

}
