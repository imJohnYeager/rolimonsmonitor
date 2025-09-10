package com.johnyeager.rtrading.rolimonsmonitor;

import com.johnyeager.rtrading.rolimonsmonitor.model.DiscordNotifier;
import com.johnyeager.rtrading.rolimonsmonitor.model.FiltrosAnuncio;
import com.johnyeager.rtrading.rolimonsmonitor.model.StartupMessageBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // permite agendamento de tarefas
public class RolimonsMonitorApplication {

    public static void main(String[] args) {


        SpringApplication.run(RolimonsMonitorApplication.class, args);
    }
}