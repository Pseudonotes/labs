package com.kdbrian.mail.server.controller;

import com.kdbrian.mail.server.domain.dto.ResponseWrapper;
import com.kdbrian.mail.server.domain.model.MailMessage;
import com.kdbrian.mail.server.domain.service.MailService;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class RestApiController {


    private final MailService mailService;


    @PostMapping(
            value = "/send",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )

    public ResponseEntity<@NonNull ResponseWrapper<Map<Object, Object>>> sendEmail(
            @ModelAttribute MailMessage mailMessage
    ) throws MessagingException, IOException {
        HashMap<Object, Object> sentMail = mailService.sendMail(mailMessage);

        return ResponseEntity.ok(
                new ResponseWrapper<>(
                        sentMail,
                        "Email sent.",
                        true
                )
        );

    }


}
