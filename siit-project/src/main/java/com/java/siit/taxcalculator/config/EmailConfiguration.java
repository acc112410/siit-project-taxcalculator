package com.java.siit.taxcalculator.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Getter
@Setter
public class EmailConfiguration {

    @Value("${smtp.mailtrap.io}")
    private String host;
    @Value("${2525}")
    private int port;
    @Value("${ff260f01528530}")
    private String username;
    @Value("${03b462328e3a89}")
    private String password;

}
