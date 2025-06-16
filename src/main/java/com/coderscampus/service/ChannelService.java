package com.coderscampus.service;

import com.coderscampus.domain.Channel;
import com.coderscampus.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelService {
    @Autowired
    private ChannelRepository channelRepo;

    public Channel createChannel(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Channel cannot be null or empty");
        }
        String trimmed = name.trim();
        // 2) Check for an existing channel with the same name
        Optional<Channel> existing = channelRepo.findByName(trimmed);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("That channel name is already taken");
        }


        Channel channel = new Channel();
        channel.setName(trimmed);
        return channelRepo.save(channel);
    }

    public Channel findChannelById(Long id) {
        return channelRepo.findById(id).orElse(null);
    }

    public List<Channel> findAllChannels() {
        return channelRepo.findAll();
    }

}
