package com.jevprentice.web;

import com.jevprentice.model.PersistedResource;
import com.jevprentice.model.User;
import com.jevprentice.model.UserDto;
import com.jevprentice.service.PersistedResourceService;
import com.jevprentice.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@Slf4j
public class HomeController {

    private static final GrantedAuthority[] USER_AUTH_LIST = new GrantedAuthority[]{new SimpleGrantedAuthority("USER")};

    private final UserService userService;
    private final PersistedResourceService resourceService;

    @Autowired
    public HomeController(
            @NonNull final UserService userService,
            @NonNull final PersistedResourceService resourceService
    ) {
        this.userService = userService;
        this.resourceService = resourceService;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register(final Model model) {
        final UserDto dto = new UserDto();
        model.addAttribute("user", dto);
        return "register";
    }

    @PostMapping("register")
    public ModelAndView createUser(@ModelAttribute("user") @Valid final UserDto dto) {
        final User user = new User(dto.getUsername(), dto.getPassword(), USER_AUTH_LIST);
        userService.createUser(user);
        log.info(String.format("Registered new user: %s.", user.getUsername()));
        return new ModelAndView("index", "user", dto);
    }

    @PostMapping("update_user_profile_details")
    public ModelAndView updateUserDetails(@ModelAttribute("user") @Valid final UserDto dto) {
        userService.changePassword(dto.getPassword(), dto.getPassword());
        return new ModelAndView("user/profile", "user", dto);
    }

    @GetMapping("profile")
    public ModelAndView profile(final Model model, final WebRequest request) {
        final String remoteUser = WebUtil.getRemoteUser(request);
        final User user = userService.loadUserByUsername(remoteUser);
        resourceService.getUserProfilePicture(user)
                .ifPresent(resource -> model.addAttribute("profilePictureBytes", resource.getFileBytes()));
        model.addAttribute("username", remoteUser);
        return new ModelAndView("user/profile", "user", model);
    }

    @PostMapping("update_user_profile_picture")
    public ModelAndView updateUserProfile(
            @NonNull final Model model,
            @NonNull final WebRequest request,
            @RequestParam("file") @NonNull final MultipartFile file
    ) {
        final String remoteUser = WebUtil.getRemoteUser(request);
        try {
            resourceService.createUserProfilePicture(
                    new PersistedResource(file.getOriginalFilename(), file.getContentType(), file.getBytes().clone()),
                    userService.loadUserByUsername(remoteUser)
            );
            model.addAttribute("username", remoteUser);
            return new ModelAndView("user/profile", "user", model);
        } catch (final IOException e) {
            throw new RuntimeException("Error persisting resource: " + file.getName(), e);
        }
    }

    @GetMapping(value = "/get_user_profile_picture", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getUserProfilePicture(final Model model, final WebRequest request) throws IOException {
        final String remoteUser = WebUtil.getRemoteUser(request);
        final User user = userService.loadUserByUsername(remoteUser);
        return resourceService.getUserProfilePicture(user)
                .orElse(WebUtil.getDefaultProfilePicture())
                .getFileBytes();
    }

    //TODO Something for the users to do
    //TODO Something for the admins to do
}
