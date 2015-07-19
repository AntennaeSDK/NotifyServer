package org.antennae.server.notifier;

import org.antennae.config.H2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(H2Config.class)
public class NotifierDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run( NotifierDashboardApplication.class, args);
    }
}
