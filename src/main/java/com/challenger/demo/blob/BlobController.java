package com.challenger.demo.blob;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class BlobController {

    private final BlobRepository streamingFileRepository;
    private final BlobService lobCreator;

    @Autowired
    public BlobController(BlobRepository streamingFileRepository, BlobService lobCreator) {
        this.streamingFileRepository = streamingFileRepository;
        this.lobCreator = lobCreator;
    }

    @Transactional
    @RequestMapping(value = "/blobs", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> store(@RequestPart("file") MultipartFile multipartFile) throws IOException, SQLException, URISyntaxException {
        log.info("Persisting new file: {}", multipartFile.getOriginalFilename());
        StreamingFileRecord streamingFileRecord = new StreamingFileRecord(multipartFile.getOriginalFilename(), BlobProxy.generateProxy(multipartFile.getInputStream(), multipartFile.getSize()));

        streamingFileRecord = streamingFileRepository.save(streamingFileRecord);

        log.info("Persisted {} with id: {}", multipartFile.getOriginalFilename(), streamingFileRecord.getId());
        return ResponseEntity.created(new URI("http://localhost:8080/blobs/" + streamingFileRecord.getId())).build();
    }

    @Transactional
    @RequestMapping(value = "/blobs/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public  ResponseEntity<byte[]> load(@PathVariable("id") long id, HttpServletResponse response) throws SQLException, IOException {
        log.info("Loading file id: {}", id);
        StreamingFileRecord record = streamingFileRepository.findById(id).get();

//        response.addHeader("Content-Disposition", "attachment; filename=" + record.getName());
        log.info("Sent file id: {}", id);
        byte[] bytes = StreamUtils.copyToByteArray(record.getData().getBinaryStream());
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }

}
