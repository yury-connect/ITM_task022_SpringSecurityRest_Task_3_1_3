package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.itmentor.spring.boot_security.demo.constants.Constants.PASSWORD_PLACE_HOLDER;


@Controller
@RequestMapping(value = "/authenticated/guest")
public class GuestController extends AbstractController{

    @Autowired
    protected GuestController(UserService userService) {
        super(userService);
    }

    @GetMapping()
    public String showGuestInfoPage(Model model) {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy 'at' HH:mm:ss");
        String formattedDate = formatter.format(new Date(currentTimeMillis));
        String message = "Current date and time: " + formattedDate;

        model.addAttribute("current_time", message);
        return "guest-pages/guest_info_page";
    }
}
