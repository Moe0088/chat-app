package com.coderscampus.service;

import com.coderscampus.domain.Channel;
import com.coderscampus.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business‐logic layer for Channel operations.
 * Validates inputs and delegates to the ChannelRepository.
 */
@Service
public class ChannelService {
    // Inject our in-memory repository
    @Autowired
    private ChannelRepository channelRepo;

    /**
     * Create a new channel if the name is valid and not already taken.
     * @param name the desired channel name
     * @return the newly created Channel
     * @throws IllegalArgumentException on invalid or duplicate names
     */
    public Channel createChannel(String name) {
        // 1) Reject null or blank names
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Channel cannot be null or empty");
        }

        // 2) Trim whitespace
        String trimmed = name.trim();

        // 3) Check repository for an existing channel with this name
        Optional<Channel> existing = channelRepo.findByName(trimmed);
        if (existing.isPresent()) {
            // 3a) If found, reject creation to avoid duplicates
            throw new IllegalArgumentException("That channel name is already taken");
        }

        // 4) Build a fresh Channel object
        Channel channel = new Channel();
        channel.setName(trimmed);

        // 5) Save it via the repository (assigns an ID) and return it
        return channelRepo.saveChannel(channel);
    }

    /**
     * Lookup a channel by its ID.
     * @param id the numeric ID
     * @return the Channel or null if none exists
     */
    public Channel findChannelById(Long id) {
        return channelRepo.findChannelById(id);
    }

    /**
     * Fetch the list of all available channels.
     * @return list of channels
     */
    public List<Channel> findAllChannels() {
        return channelRepo.findAllChannels();
    }

    public Optional<Channel> findByName(String name) {
        return channelRepo.findByName(name);
    }
}
