package com.example.app.controller.C04NaverAPI.sms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SMSDto {
    String to;
    String content;
}
