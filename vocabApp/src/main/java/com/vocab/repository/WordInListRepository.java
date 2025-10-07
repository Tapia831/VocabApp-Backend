package com.vocab.repository;

import com.vocab.model.WordInList;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface WordInListRepository extends MongoRepository<WordInList, String> {
    List<WordInList> findByUserIdAndListId(String userId, String listId);
    Optional<WordInList> findByUserIdAndListIdAndWord(String userId, String listId, String word);
}