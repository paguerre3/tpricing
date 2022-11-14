package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PricingTest {

    @Autowired
    Pricing pricing;

    @Test
    public void whenLoadingInitialData_thenRequiredValuesExist() {
        List<Price> pl = pricing.getAll();
        assert pl.size() == 4;
        assert pl.stream().filter(p -> p.getPriceList() == 1).count() == 1;
        assert pl.stream().filter(p -> p.getPriceList() == 2).count() == 1;
        assert pl.stream().filter(p -> p.getPriceList() == 3).count() == 1;
        assert pl.stream().filter(p -> p.getPriceList() == 4).count() == 1;
    }

    @Test
    public void whenGatheringPreloadedPriceByList_thenPriceExists() throws ItemNotFoundException {
        assert pricing.getByPriceList(1L).getPriceList() == 1;
        assert pricing.getByPriceList(3L).getPriceList() == 3;
    }

    @Test
    public void whenGatheringNotPreloadedPriceByList_thenPriceDoesNotExist() {
        ItemNotFoundException thrown = assertThrows(
                ItemNotFoundException.class,
                () -> pricing.getByPriceList(5L),
                "Expected ItemNotFoundException but it didn't"
        );

        assert thrown.getMessage().contains("Price not found");
    }
}
