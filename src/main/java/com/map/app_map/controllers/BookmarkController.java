package com.map.app_map.controllers;

import com.map.app_map.entities.Bookmark;
import com.map.app_map.repositories.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin(origins = "http://localhost:4200")
public class BookmarkController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @GetMapping
    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> getBookmarkById(@PathVariable Long id) {
        Optional<Bookmark> bookmark = bookmarkRepository.findById(id);
        if (bookmark.isPresent()) {
            return ResponseEntity.ok(bookmark.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Bookmark createBookmark(@RequestBody Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> updateBookmark(@PathVariable Long id, @RequestBody Bookmark bookmarkDetails) {
        Optional<Bookmark> bookmark = bookmarkRepository.findById(id);
        if (bookmark.isPresent()) {
            Bookmark existingBookmark = bookmark.get();
            existingBookmark.setName(bookmarkDetails.getName());
            existingBookmark.setDescription(bookmarkDetails.getDescription());
            Bookmark updatedBookmark = bookmarkRepository.save(existingBookmark);
            return ResponseEntity.ok(updatedBookmark);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
        Optional<Bookmark> bookmark = bookmarkRepository.findById(id);
        if (bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
