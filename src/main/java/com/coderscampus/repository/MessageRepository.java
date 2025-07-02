package com.coderscampus.repository;

import com.coderscampus.domain.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory storage for chat messages.
 * Each Message is given a unique ID and kept in a list.
 */
@Repository
public class MessageRepository {
    // Holds all messages in memory
    private List<Message> messages = new ArrayList<>();
    // Counter for assigning unique IDs
    private long messageIdCounter = 1;

    /**
     * Save a new Message if it doesn't already have an ID.
     * @param message the Message to save
     * @return the same Message instance (now with an ID)
     */
    public Message saveMessage(Message message) {
        if (message.getId() == null) {
            message.setId(messageIdCounter++);
            messages.add(message);
        }
        return message;
    }

    /**
     * Return a copy of all stored messages.
     * @return list of saved messages
     */
    public List<Message> findAllMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * Look up a single message by its ID.
     * @param id numeric ID
     * @return the matching Message or null if none found
     */
    public Message findMessageById(long id) {
        for (Message message : messages) {
            if (message.getId().equals(id)) {
                return message;
            }
        }
        return null;
    }

    /**
     * Check whether a message exists with the given ID.
     * @param id numeric ID
     * @return true if found, false otherwise
     */
    public boolean existsMessageById(long id) {
        return findMessageById(id) != null;
    }
}
