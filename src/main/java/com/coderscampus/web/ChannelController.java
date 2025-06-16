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

@Controller
public class ChannelController {
    @Autowired
    private UserService userService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/channel/{channelId}")
    public String getChannel(@PathVariable Long channelId, Model model) {
        Channel channel = channelService.findChannelById(channelId);
        List<Message> messages = messageService.getAllMessagesByChannelId(channelId);

        model.addAttribute("channel", channel);
        model.addAttribute("messages", messages);
        model.addAttribute("message", new Message());

        return "channel";

    }


    @PostMapping("/channel/{channelId}")
    public String postChannel(@PathVariable Long channelId, @ModelAttribute Message message, HttpServletRequest request) {
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }
        Channel channel = channelService.findChannelById(channelId);
        message.setChannel(channel);

        User user = (User) request.getSession().getAttribute("user");
        message.setUser(user);
        messageService.createMessage(message);
        return "redirect:/channel/" + channelId;
    }

    @GetMapping("/channel/{channelId}/messages")
    @ResponseBody
    public List<Message> getMessages(@PathVariable Long channelId) {
        return messageService.getAllMessagesByChannelId(channelId);
    }


}
