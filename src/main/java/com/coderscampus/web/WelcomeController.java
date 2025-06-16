package com.coderscampus.web;

import com.coderscampus.domain.Channel;
import com.coderscampus.domain.User;
import com.coderscampus.service.ChannelService;
import com.coderscampus.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class WelcomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChannelService channelService;

    @GetMapping("/")
    public String displayWelcome(Model model) {
        model.addAttribute("user", new User());
        List<Channel> channelCall = channelService.findAllChannels();
        model.addAttribute("channels", channelCall);
        return "welcome";

    }

    @PostMapping("/")
    public String postWelcome(@ModelAttribute("user") User user,
                              @RequestParam(value = "channelId", required = false) Long channelId,
                              Model model, HttpServletRequest request) {
        try {
            User savedUser = userService.createUser(user.getName());
            request.getSession().setAttribute("user", savedUser);
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("channels", channelService.findAllChannels());
            return "welcome";
        }

        if (channelId == null) {
            model.addAttribute("message", "Please select a channel!");
            model.addAttribute("channels", channelService.findAllChannels());
            return "welcome";
        }

        return "redirect:/channel/" + channelId;
    }

    @PostMapping("/create-channel")
    public String createChannel(@RequestParam("newChannelName") String newChannelName, Model model) {
        try {
            channelService.createChannel(newChannelName);
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("channels", channelService.findAllChannels());
            model.addAttribute("user", new User()); // to preserve the form
            return "welcome";
        }

        return "redirect:/";
    }

    @GetMapping("/users/check")
    @ResponseBody
    public Boolean checkUser(@RequestParam("name") String name) {

        return userService.findByName(name.trim()).isPresent();
    }
}
