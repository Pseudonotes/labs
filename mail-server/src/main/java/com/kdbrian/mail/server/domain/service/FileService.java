package com.kdbrian.mail.server.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String bucket( );

    String uploadFile(MultipartFile file) throws IOException;
    byte[] downloadFile(String path);
}
