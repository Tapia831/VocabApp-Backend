package com.vocab.controller;

import com.vocab.model.Dictionary;
import com.vocab.repository.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/dictionary")
@CrossOrigin(origins = "*")
public class DictionaryController {
    
    @Autowired
    private DictionaryRepository dictionaryRepository;
    
    @GetMapping("/random")
    public ResponseEntity<?> getRandomWord() {
        try {
            List<Dictionary> allWords = dictionaryRepository.findAll();
            if (allWords.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "No words in dictionary"));
            }
            
            Random random = new Random();
            Dictionary randomWord = allWords.get(random.nextInt(allWords.size()));
            
            return ResponseEntity.ok(randomWord);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch random word"));
        }
    }
    
    @GetMapping("/word/{word}")
    public ResponseEntity<?> getWordDefinition(@PathVariable String word) {
        try {
            Dictionary result = dictionaryRepository.findByWord(word.toLowerCase());
            if (result == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Word not found"));
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch word"));
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> addWord(@RequestBody Dictionary word) {
        try {
            word.setWord(word.getWord().toLowerCase());
            Dictionary saved = dictionaryRepository.save(word);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add word"));
        }
    }
}