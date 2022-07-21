package com.challenger.demo.blob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

@Service
public class BlobService {
    private final LobHandler lobHandler;

    @Autowired
    public BlobService(LobHandler lobHandler) {
        this.lobHandler = lobHandler;
    }
}
