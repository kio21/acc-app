package com.example;

import com.example.controller.TransferController;
import com.example.dto.UserDto;
import com.example.service.MoneyTransferService;
import com.example.service.TransferData;
import com.example.test.ApiTestConfiguration;
import com.example.test.WebSecurityConfigurer;
import com.example.test.WithRegisteredUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;

import static com.example.test.Helper.verifyRequestMapping;
import static com.example.test.WithRegisteredUserSecurityContextFactory.currentUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferController.class)
@ContextConfiguration(classes = {
    WebSecurityConfigurer.class,
    ApiTestConfiguration.class
})
@DisplayName("[private] Transfer controller")
class TransferControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private MoneyTransferService moneyTransferService;

    @Test
    @WithRegisteredUser
    @DisplayName("money transfer success")
    void moneyTransferSuccess() throws Exception {
        verifyRequestMapping(TransferController.class, "/transfer");
        when(moneyTransferService.transfer(any(), any())).thenReturn(true);

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"userId\": \"2\", \"value\": \"100\"}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().json("{\"status\":\"SUCCESS\",\"userFrom\":1,\"userTo\":2,\"value\":100}"));

        ArgumentCaptor<UserDto> userCaptor = ArgumentCaptor.forClass(UserDto.class);
        ArgumentCaptor<TransferData> transferDataCaptor = ArgumentCaptor.forClass(TransferData.class);
        verify(moneyTransferService, only()).transfer(userCaptor.capture(), transferDataCaptor.capture());
        UserDto userArg = userCaptor.getValue();
        TransferData transferDataArg = transferDataCaptor.getValue();

        assertEquals(userArg, currentUser());
        assertEquals(2L, transferDataArg.transferTo());
        assertEquals(new BigDecimal(100), transferDataArg.value());
    }


    @Test
    @WithRegisteredUser
    @DisplayName("money transfer failed")
    void moneyTransferFailed() throws Exception {
        verifyRequestMapping(TransferController.class, "/transfer");
        when(moneyTransferService.transfer(any(), any())).thenReturn(false);

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"userId\": \"1\", \"value\": \"100\"}"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().json("{\"status\":\"FAILED\",\"userFrom\":1,\"userTo\":1,\"value\":100}"));


        ArgumentCaptor<UserDto> userCaptor = ArgumentCaptor.forClass(UserDto.class);
        ArgumentCaptor<TransferData> transferDataCaptor = ArgumentCaptor.forClass(TransferData.class);
        verify(moneyTransferService, only()).transfer(userCaptor.capture(), transferDataCaptor.capture());
        UserDto userArg = userCaptor.getValue();
        TransferData transferDataArg = transferDataCaptor.getValue();

        assertEquals(userArg, currentUser());
        assertEquals(1L, transferDataArg.transferTo());
        assertEquals(new BigDecimal(100), transferDataArg.value());
    }
}
