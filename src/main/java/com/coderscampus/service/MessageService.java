package com.coderscampus.service;

import com.coderscampus.domain.Channel;
import com.coderscampus.domain.Message;
import com.coderscampus.repository.ChannelRepository;
import com.coderscampus.repository.MessageRepository;
import com.coderscampus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepo;

    public Message createMessage(Message message) {
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        if (message.getUser() == null || message.getChannel() == null) {
            throw new IllegalArgumentException("User ID and Channel ID cannot be null");
        }
        message.setTimestamp(LocalDateTime.now());

        return messageRepo.save(message);
    }

    public List<Message> getAllMessagesByChannelId(Long channelId) {
        if (channelId == null) {
            throw new IllegalArgumentException("Channel ID cannot be null");
        }
        return messageRepo.findByChannelId(channelId);

    }
}
