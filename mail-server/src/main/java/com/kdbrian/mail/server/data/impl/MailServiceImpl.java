package com.kdbrian.mail.server.data.impl;

import com.kdbrian.mail.server.domain.model.MailMessage;
import com.kdbrian.mail.server.domain.service.FileService;
import com.kdbrian.mail.server.domain.service.MailService;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final FileService fileService;


    public HashMap<Object, Object> sendMail(MailMessage message) throws MessagingException, IOException {
        MimeMessage senderMimeMessage = javaMailSender.createMimeMessage();
        senderMimeMessage.setSubject(message.getTitle());
        senderMimeMessage.setText(message.getBody());
        senderMimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(message.getSendTo()));
        senderMimeMessage.setFrom();
        HashMap<Object, Object> map = new HashMap<>() {
        };

        if (message.getFile() != null && !message.getFile().isEmpty()) {
            log.info("file: {} {}", message.getFile() != null, message.getFile().getOriginalFilename());


            //call our file service, uploads and save metadata
            String fileUrl = fileService.uploadFile(
                    message.getFile()
            );
            senderMimeMessage.setContent(
                    new MimeMultipart(
                            new FileDataSource(fileUrl)
                    )
            );

            map.put(message.getFile().getName(), fileUrl);
        }


        javaMailSender.send(senderMimeMessage);

        String formatted = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(senderMimeMessage.getSentDate());
        int messageSize = senderMimeMessage.getSize();


        map.put("sentAt", formatted);
        map.put("size", messageSize);

        return map;
    }

}
