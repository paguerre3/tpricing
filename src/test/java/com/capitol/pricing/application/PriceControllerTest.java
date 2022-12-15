package com.capitol.pricing.application;

import com.capitol.pricing.domain.models.exceptions.RestError;
import com.capitol.pricing.domain.models.Currency;
import com.capitol.pricing.domain.models.PriceRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PriceController priceController;

    static MediaType mType;
    static ObjectMapper objMapper;

    @BeforeAll
    public static void init() {
        mType = new MediaType("application", "json", StandardCharsets.UTF_8);
        objMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    /**
     * TEST CASE 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     *
     */
    @Test
    public void whenSearchingForPriceToApplyFromView_thenPriceWithHigherPriorityIsReturned_case1SingleFound() throws Exception {
       Optional<PriceRate> po = searchAndValidatePriceToApply("/api/v1/prices/search/2020-06-14T10:00:00/35455/1",
               status().isOk());
       po.ifPresent(p -> {
           assert p.getPriceList() == 1;
           assert p.getPrice() == 35.50f;
           assert p.equals(getPriceRateToCompare());
       });
    }

    /**
     * TEST CASE 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     *
     */
    @Test
    public void whenSearchingForPriceToApplyFromView_thenPriceWithHigherPriorityIsReturned_case2MultipleFindings() throws Exception {
        Optional<PriceRate> po = searchAndValidatePriceToApply("/api/v1/prices/search/2020-06-14T16:00:00/35455/1",
                status().isOk());
        po.ifPresent(p -> {
            assert p.getPriceList() == 2;
            assert p.getPrice() == 25.45f;
        });
    }

    /**
     * TEST CASE 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     *
     */
    @Test
    public void whenSearchingForPriceToApplyFromView_thenPriceWithHigherPriorityIsReturned_case3SingleFound() throws Exception {
        Optional<PriceRate> po = searchAndValidatePriceToApply("/api/v1/prices/search/2020-06-14T21:00:00/35455/1",
                status().isOk());
        po.ifPresent(p -> {
            assert p.getPriceList() == 1;
            assert p.getPrice() == 35.50f;
        });
    }

    /**
     * TEST CASE 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
     *
     */
    @Test
    public void whenSearchingForPriceToApplyFromView_thenPriceWithHigherPriorityIsReturned_case4SingleFound() throws Exception {
        Optional<PriceRate> po = searchAndValidatePriceToApply("/api/v1/prices/search/2020-06-15T10:00:00/35455/1",
                status().isOk());
        po.ifPresent(p -> {
            assert p.getPriceList() == 3;
            assert p.getPrice() == 30.50f;
        });
    }

    /**
     * TEST CASE 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
     *
     */
    @Test
    public void whenSearchingForPriceToApplyFromView_thenPriceWithHigherPriorityIsReturned_case5SingleFoundDifferentDate() throws Exception {
        Optional<PriceRate> po = searchAndValidatePriceToApply("/api/v1/prices/search/2020-06-16T21:00:00/35455/1",
                status().isOk());
        po.ifPresent(p -> {
            assert p.getPriceList() == 4;
            assert p.getPrice() == 38.95f;
        });
    }



    @Test
    public void whenSearchingForPriceToApplyOutOfScopeFromView_thenPriceNotFound() throws Exception {
        // date out of range (not present in db):
        searchAndValidatePriceToApplyRawContent("/api/v1/prices/search/2013-10-10T21:00:00/35455/1",
                status().isNotFound());
        // product id out of scope (not present in db):
        searchAndValidatePriceToApplyRawContent("/api/v1/prices/search/2020-06-16T21:00:00/00000/1",
                status().isNotFound());
        // brand id out of scope (not present in db):
        searchAndValidatePriceToApplyRawContent("/api/v1/prices/search/2020-06-16T21:00:00/35455/0",
                status().isNotFound());
    }

    @Test
    public void whenSearchingForPriceToApplyIncorrectly_thenInternalError() throws Exception {
        // date out of range (not present in db):
        String rawContent = searchAndValidatePriceToApplyRawContent("/api/v1/prices/search/0/35455/1",
                status().isInternalServerError());
        Map<String, Object> jsonError = objMapper.readValue(rawContent, Map.class);
        RestError errorDisplay = getDisplayErrorToCompare("uri=/api/v1/prices/0");
        assert jsonError.get("details").equals("uri=/api/v1/prices/search/0/35455/1");
        assert jsonError.get("localDate").equals(errorDisplay.getLocalDate().toString());
    }

    private String searchAndValidatePriceToApplyRawContent(final String uri, final ResultMatcher contentStatus) throws Exception {
        return mvc.perform(get(uri).accept(mType).contentType(mType))
                .andExpect(contentStatus)
                .andReturn().getResponse().getContentAsString();
    }

    private Optional<PriceRate> searchAndValidatePriceToApply(final String uri, final ResultMatcher contentStatus) throws Exception {
        String contentAsString = searchAndValidatePriceToApplyRawContent(uri, contentStatus);
        return Optional.of(objMapper.readValue(contentAsString, PriceRate.class));
    }

    private PriceRate getPriceRateToCompare() {
        PriceRate pc = new PriceRate();
        pc.setBrandId(1);
        pc.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        pc.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        pc.setProductId(35455L);
        pc.setPriceList(1);
        pc.setPrice(35.50f);
        pc.setCurrency(Currency.EUR);
        return pc;
    }

    private RestError getDisplayErrorToCompare(final String errorDetails) {
        return new RestError.Builder()
                .setLocalDate(LocalDate.now())
                .setDetails(errorDetails)
                .build();
    }
}
