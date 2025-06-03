package com.br.adapter.dummy.adapterdummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdapterdummyApplication implements CommandLineRunner {
    @Autowired
    private AdapterDummyService adapterDummyService;

    public static void main(String[] args) {
        SpringApplication.run(AdapterdummyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        adapterDummyService.readJsonAndSend("messages.json");
        System.out.println("Finished sending messages to Pulsar.");
    }

}
