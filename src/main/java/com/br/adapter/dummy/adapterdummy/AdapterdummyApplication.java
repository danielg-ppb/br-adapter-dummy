package com.br.adapter.dummy.adapterdummy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
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
        log.info("Finished sending messages to Pulsar.");
    }

}
