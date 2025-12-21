package com.kdbrian.mail.server.domain.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  ResponseWrapper<T>{
    @NonNull
    T data;
    @NonNull
    String message;
    @NonNull
    Boolean status;
}

