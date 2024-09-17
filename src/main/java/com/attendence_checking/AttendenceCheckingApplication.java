package com.attendence_checking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"Controller","Models","Repository","Service"})
@SpringBootApplication
public class AttendenceCheckingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendenceCheckingApplication.class, args);
    }

}
