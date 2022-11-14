package com.capitol.pricing.controllers;

import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.exceptions.RestError;
import com.capitol.pricing.models.Price;
import com.capitol.pricing.services.Pricing;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @MockBean
    Pricing pricing;

    @Autowired
    MockMvc mvc;

    @Autowired
    PriceController priceController;

    static MediaType mType;
    static ObjectMapper objMapper;
    static Price p1;
    static RestError e1;


    @BeforeAll
    public static void init() {
        mType = new MediaType("application", "json", StandardCharsets.UTF_8);
        objMapper = JsonMapper.builder().build();
        p1 = new Price();
        p1.setPriceList(1);
        p1.setPrice(7.55f);
        e1 = new RestError.Builder()
                .setTimestamp(LocalDate.now())
                .setDetails("uri=/api/v1/prices/0")
                .build();
    }

    @Test
    public void whenGatheringPriceFoundByList_thenPriceDetailsAreReturned() throws Exception {
        given(pricing.getByPriceList(1L)).willReturn(p1);
        mvc.perform(get("/api/v1/prices/1").accept(mType).contentType(mType))
                .andExpect(status().isOk())
                .andExpect(content().json(objMapper.writeValueAsString(p1)));
    }


    @Test
    public void whenGatheringPriceNotPresentByList_thenNotFoundErrorOccurs() throws Exception {
        given(pricing.getByPriceList(0L)).willThrow(ItemNotFoundException.class);
        // error details handled by advice:
        String contentAsString = mvc.perform(get("/api/v1/prices/0").accept(mType).contentType(mType))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        Map<String, Object> json = objMapper.readValue(contentAsString, Map.class);
        assert json.get("details").equals(e1.getDetails());
        assert json.get("timestamp").equals(e1.getTimestamp().toString());
    }
}
