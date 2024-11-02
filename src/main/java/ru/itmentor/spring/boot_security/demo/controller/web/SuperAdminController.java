package ru.itmentor.spring.boot_security.demo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.service.UserUtilService;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Controller
@RequestMapping(value = "/authenticated/superadmin")
public class SuperAdminController extends AbstractController {

    private UserUtilService userUtilService;
    private RoleService roleService;



    @Autowired
    public SuperAdminController(UserService service, UserUtilService userUtilService, RoleService roleService) {
        super(service); //  прокидываю UserService в общий суперкласс
        this.userUtilService = userUtilService;
        this.roleService = roleService;
    }




    // Добавим функционала, выведем какую-то информацию о системе... :)
    @GetMapping("/system-info")
    public String getSystemInfo(Model model) {
        List<String> systemInfo = new ArrayList<>();
        Properties properties = System.getProperties();
        Runtime runtime = Runtime.getRuntime();

        // Основная информация о системе
        systemInfo.add("┌──────────────────────────────────────────────────────────────────────────────┐");
        systemInfo.add("*****   Основная информация о системе   *****");
        systemInfo.add("Operating System: " + properties.getProperty("os.name"));
        systemInfo.add("OS Version: " + properties.getProperty("os.version"));
        systemInfo.add("OS Architecture: " + properties.getProperty("os.arch"));
        systemInfo.add("Java Version: " + properties.getProperty("java.version"));
        systemInfo.add("Java Vendor: " + properties.getProperty("java.vendor"));
        systemInfo.add("User Home Directory: " + properties.getProperty("user.home"));
        systemInfo.add("User Working Directory: " + properties.getProperty("user.dir"));
        systemInfo.add("User Name: " + properties.getProperty("user.name"));
        systemInfo.add("File Encoding: " + properties.getProperty("file.encoding"));

        // Информация о памяти
        systemInfo.add("├──────────────────────────────────────────────────────────────────────────────┤");
        systemInfo.add("*****   Информация о памяти   *****");
        systemInfo.add("Available Processors: " + runtime.availableProcessors());
        systemInfo.add("Total Memory (MB): " + (runtime.totalMemory() / 1024 / 1024));
        systemInfo.add("Free Memory (MB): " + (runtime.freeMemory() / 1024 / 1024));
        systemInfo.add("Max Memory (MB): " + (runtime.maxMemory() / 1024 / 1024));

        // Дисковое пространство
        systemInfo.add("├──────────────────────────────────────────────────────────────────────────────┤");
        systemInfo.add("*****   Дисковое пространство   *****");
        File root = new File("/");
        systemInfo.add("Disk Total Space (GB): " + (root.getTotalSpace() / 1024 / 1024 / 1024));
        systemInfo.add("Disk Free Space (GB): " + (root.getFreeSpace() / 1024 / 1024 / 1024));
        systemInfo.add("Disk Usable Space (GB): " + (root.getUsableSpace() / 1024 / 1024 / 1024));

        // Время системы
        systemInfo.add("├──────────────────────────────────────────────────────────────────────────────┤");
        systemInfo.add("*****   Время системы   *****");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        systemInfo.add("Current Date and Time: " + sdf.format(new Date(System.currentTimeMillis())));
        systemInfo.add("└──────────────────────────────────────────────────────────────────────────────┘");


        model.addAttribute("systemInfo", systemInfo);
        return "sevice-pages/system-info";
    }
}
