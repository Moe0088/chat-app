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

@Controller  // Marks this class as a Spring MVC controller
public class WelcomeController {

    @Autowired
    private UserService userService;          // Business logic for users

    @Autowired
    private ChannelService channelService;    // Business logic for channels

    @GetMapping("/")                        // Handles GET requests to “/”
    public String displayWelcome(Model model) {
        model.addAttribute("user", new User());                               // Provide an empty User object for the form
        List<Channel> channelCall = channelService.findAllChannels();         // Fetch all available channels
        model.addAttribute("channels", channelCall);                          // Make channel list available to the view
        return "welcome";                                                     // Render “welcome.html”
    }

    @PostMapping("/")                       // Handles form submissions from “/”
    public String postWelcome(
            @ModelAttribute("user") User user,                               // Bind form’s “name” input to User
            @RequestParam(value = "channelId", required = false) Long channelId, // Optional selected channel ID
            Model model,
            HttpServletRequest request) {

        try {
            // Attempt to create or retrieve a User, throws if name invalid
            User savedUser = userService.createUser(user.getName());
            // Store the logged-in user in session for later message posting
            request.getSession().setAttribute("user", savedUser);
        } catch (IllegalArgumentException e) {
            // On validation error (e.g. empty name), re-show welcome page with message
            model.addAttribute("message", e.getMessage());
            model.addAttribute("channels", channelService.findAllChannels());
            return "welcome";
        }

        if (channelId == null) {
            Channel general = channelService
                    .findByName("general")
                    .orElseThrow(() ->
                            new IllegalStateException("Default channel missing")
                    );
            channelId = general.getId();
        }

        // Redirect into the chosen channel
        return "redirect:/channel/" + channelId;
    }

    @PostMapping("/create-channel")         // Handles new-channel form submissions
    public String createChannel(
            @RequestParam("newChannelName") String newChannelName,  // Name for the new channel
            Model model) {

        try {
            // Attempt to create channel, throws if invalid or duplicate
            channelService.createChannel(newChannelName);
        } catch (IllegalArgumentException e) {
            // On error, re-show welcome page with error message
            model.addAttribute("message", e.getMessage());
            model.addAttribute("channels", channelService.findAllChannels());
            model.addAttribute("user", new User()); // reset username field
            return "welcome";
        }

        // After successful creation, reload welcome to show updated list
        return "redirect:/";
    }

    @GetMapping("/users/check")            // AJAX endpoint to check if a username is taken
    @ResponseBody                         // Return raw Boolean in response body
    public Boolean checkUser(@RequestParam("name") String name) {
        // Return true if a user with that name already exists
        return userService.findByName(name.trim()).isPresent();
    }
}
