package com.coderscampus.repository;

import com.coderscampus.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A simple in-memory repository for Channel objects.
 * Since we’re not using a database here, we store channels in an ArrayList.
 */
@Repository
public class ChannelRepository {

    // The in-memory list of channels
    private List<Channel> channels = new ArrayList<>();

    // A simple counter to assign unique numeric IDs to new channels
    private long channelIdCounter = 1;

    /**
     * Constructor: on application startup, create one default channel named "general".
     */
    public ChannelRepository() {
        Channel general = new Channel();
        // Assign first ID
        general.setId(channelIdCounter++);
        // Name it "general"
        general.setName("general");
        // Add to our list
        channels.add(general);
    }

    /**
     * Save a channel if it doesn’t already have an ID.
     * @param channel the new channel to save
     * @return the same channel instance (now with an assigned ID)
     */
    public Channel saveChannel(Channel channel) {
        // Only assign an ID and add to list if this is a new channel
        if (channel.getId() == null) {
            channel.setId(channelIdCounter++);
            channels.add(channel);
        }
        return channel;
    }

    /**
     * Retrieve all channels currently in memory.
     * @return a *copy* of the channels list to avoid outside modification
     */
    public List<Channel> findAllChannels() {
        return new ArrayList<>(channels);
    }

    /**
     * Look up a channel by its numeric ID.
     * @param id the channel’s ID
     * @return the matching Channel, or null if not found
     */
    public Channel findChannelById(long id) {
        for (Channel channel : channels) {
            if (channel.getId().equals(id)) {
                return channel;
            }
        }
        return null;
    }

    /**
     * Check if a channel with a given ID exists.
     * @param id the channel’s ID
     * @return true if found, false otherwise
     */
    public Boolean existsById(long id) {
        return findChannelById(id) != null;
    }

    /**
     * Find a channel by its exact name.
     * @param name the channel’s name
     * @return an Optional describing the channel if found
     */
    public Optional<Channel> findByName(String name) {
        return channels.stream()
                .filter(channel -> channel.getName().equals(name))
                .findFirst();
    }
}
