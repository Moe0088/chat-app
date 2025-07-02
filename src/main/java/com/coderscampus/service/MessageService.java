package com.coderscampus.service;

import com.coderscampus.domain.Message;
import com.coderscampus.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for creating and fetching chat messages.
 */
@Service
public class MessageService {
    // Injects our in-memory repository
    @Autowired
    private MessageRepository messageRepo;

    /**
     * Validates and saves a new Message.
     * @param message the Message to create
     * @return the saved Message with timestamp and ID
     * @throws IllegalArgumentException if content, user, or channel is missing
     */
    public Message createMessage(Message message) {
        // 1) Content must be non-null and not blank
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        // 2) Must have a sender (user) and a channel context
        if (message.getUser() == null || message.getChannel() == null) {
            throw new IllegalArgumentException("User ID and Channel ID cannot be null");
        }
        // 3) Stamp with current time
        message.setTimestamp(LocalDateTime.now());
        // 4) Delegate save to repository (assigns ID)
        return messageRepo.saveMessage(message);
    }

    /**
     * Fetch all messages belonging to a given channel.
     * @param channelId the ID of the channel
     * @return list of messages filtered by channelId
     * @throws IllegalArgumentException if channelId is null
     */
    public List<Message> getAllMessagesByChannelId(Long channelId) {
        if (channelId == null) {
            throw new IllegalArgumentException("Channel ID cannot be null");
        }
        // Filter the in-memory list for only those messages whose channel ID matches
        return messageRepo.findAllMessages()
                .stream()
                .filter(msg -> msg.getChannel().getId().equals(channelId))
                .collect(Collectors.toList());
    }
}
