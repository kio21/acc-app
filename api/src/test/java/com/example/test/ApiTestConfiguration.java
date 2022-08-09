package com.example.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ControllerTestConfiguration.class})
public class ApiTestConfiguration {
}
