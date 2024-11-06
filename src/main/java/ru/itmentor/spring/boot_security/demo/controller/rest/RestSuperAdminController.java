package ru.itmentor.spring.boot_security.demo.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


@RestController
@RequestMapping(value = "/api/authenticated/superadmin")
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
public class RestSuperAdminController {


    // Добавим функционала, выведем какую-то информацию о системе... :)
    @Operation(summary = "Получение информации о системе, на которой запущено приложение (GET)")
    @GetMapping("/system_info")
    public ResponseEntity<List<String>> getSystemInfo() {
        List<String> systemInfo = new ArrayList<>();
        Properties properties = System.getProperties();
        Runtime runtime = Runtime.getRuntime();

        // Основная информация о системе
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
        systemInfo.add("");
        systemInfo.add("*****   Информация о памяти   *****");
        systemInfo.add("Available Processors: " + runtime.availableProcessors());
        systemInfo.add("Total Memory (MB): " + (runtime.totalMemory() / 1024 / 1024));
        systemInfo.add("Free Memory (MB): " + (runtime.freeMemory() / 1024 / 1024));
        systemInfo.add("Max Memory (MB): " + (runtime.maxMemory() / 1024 / 1024));

        // Дисковое пространство
        systemInfo.add("");
        systemInfo.add("*****   Дисковое пространство   *****");
        File root = new File("/");
        systemInfo.add("Disk Total Space (GB): " + (root.getTotalSpace() / 1024 / 1024 / 1024));
        systemInfo.add("Disk Free Space (GB): " + (root.getFreeSpace() / 1024 / 1024 / 1024));
        systemInfo.add("Disk Usable Space (GB): " + (root.getUsableSpace() / 1024 / 1024 / 1024));

        // Время системы
        systemInfo.add("");
        systemInfo.add("*****   Время системы   *****");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        systemInfo.add("Current Date and Time: " + sdf.format(new Date(System.currentTimeMillis())));

        return ResponseEntity.ok()
                .body(systemInfo);
    }
}
