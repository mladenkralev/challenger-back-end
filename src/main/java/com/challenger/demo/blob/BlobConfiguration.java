package com.challenger.demo.blob;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

@Configuration
public class BlobConfiguration {

    @Bean
    public LobHandler defaultLobHandler() {
        return new DefaultLobHandler();
    }
}
