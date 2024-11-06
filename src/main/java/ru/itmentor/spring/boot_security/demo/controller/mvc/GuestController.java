package ru.itmentor.spring.boot_security.demo.controller.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.DtoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping(value = "/authenticated/guest")
public class GuestController extends AbstractController{


    @Autowired
    protected GuestController(UserService userService, DtoUtils dtoUtils) {
        super(userService, dtoUtils);
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
