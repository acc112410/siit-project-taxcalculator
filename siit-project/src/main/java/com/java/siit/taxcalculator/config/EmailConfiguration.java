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

    private final String host = "smtp.mailtrap.io";
    private final int port = 2525;
    private final String username = "5ff4c04399370e";
    private final String password ="92ebf02e069c56";

}
