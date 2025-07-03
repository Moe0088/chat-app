package com.coderscampus.web;

import com.coderscampus.domain.Channel;
import com.coderscampus.domain.Message;
import com.coderscampus.domain.User;
import com.coderscampus.service.ChannelService;
import com.coderscampus.service.MessageService;
import com.coderscampus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller  // Marks this class as a Spring MVC controller
public class ChannelController {
    @Autowired
    private UserService userService;            // To look up the logged-in user

    @Autowired
    private ChannelService channelService;      // To fetch channel data

    @Autowired
    private MessageService messageService;      // To create and fetch messages

    @GetMapping("/channel/{channelId}")       // View a specific channel’s chat page
    public String getChannel(
            @PathVariable Long channelId,      // ID from the URL
            Model model) {

        Channel channel = channelService.findChannelById(channelId);                 // Load channel
        List<Message> messages = messageService.getAllMessagesByChannelId(channelId); // Load that channel’s messages

        model.addAttribute("channel", channel);        // Make channel available to view
        model.addAttribute("messages", messages);      // Make message list available
        model.addAttribute("message", new Message());  // Empty Message object for the post form

        return "channel";  // Render “channel.html”
    }

    @PostMapping("/channel/{channelId}")           // Handle new message submissions
    public String postChannel(
            @PathVariable Long channelId,
            @ModelAttribute Message message,        // Bind textarea content to Message.content
            HttpServletRequest request) {

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {

           return "redirect:/channel/" + channelId;
        }

        Channel channel = channelService.findChannelById(channelId);  // Look up the channel
        message.setChannel(channel);                                 // Associate message with channel

        // Pull the logged-in user from session and assign as sender
        User user = (User) request.getSession().getAttribute("user");
        message.setUser(user);

        messageService.createMessage(message);  // Persist in memory
        return "redirect:/channel/" + channelId; // Redirect back to the same channel page
    }

    @GetMapping("/channel/{channelId}/messages") // AJAX: return raw JSON list of messages
    @ResponseBody
    public List<Message> getMessages(@PathVariable Long channelId) {
        // Fetch and return for client-side polling
        return messageService.getAllMessagesByChannelId(channelId);
    }
}
