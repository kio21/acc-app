package com.example.test;

import com.example.controller.TransferController;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(TransferController.class)
public class ControllerTestConfiguration {
}
