package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PricingTest {

    @Autowired
    Pricing pricing;

    /**
     * TEST CASE 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     *
     * @throws ItemNotFoundException
     */
    @Test
    public void whenGatheringPreloadedPriceBySearch_thenPriceWithHigherPriorityIsReturned_case1SingleFound() throws ItemNotFoundException {
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 14,
                10, 00, 00);
        Price p = pricing.getByDateWithProductIdAndBrand(searchDate, 35455L, 1);
        assert p.getPriceList() == 1;
        assert p.getPriority() == 0;
        assert p.getPrice() == 35.50f;
    }

    /**
     * TEST CASE 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     *
     * @throws ItemNotFoundException
     */
    @Test
    public void whenGatheringPreloadedPriceBySearch_thenPriceWithHigherPriorityIsReturned_case2MultipleFindings() throws ItemNotFoundException {
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 14,
                16, 00, 00);
        Price p = pricing.getByDateWithProductIdAndBrand(searchDate, 35455L, 1);
        assert p.getPriceList() == 2;
        assert p.getPriority() == 1;
        assert p.getPrice() == 25.45f;
    }

    /**
     * TEST CASE 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     *
     * @throws ItemNotFoundException
     */
    @Test
    public void whenGatheringPreloadedPriceBySearch_thenPriceWithHigherPriorityIsReturned_case3SingleFound() throws ItemNotFoundException {
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 14,
                21, 00, 00);
        Price p = pricing.getByDateWithProductIdAndBrand(searchDate, 35455L, 1);
        assert p.getPriceList() == 1;
        assert p.getPriority() == 0;
        assert p.getPrice() == 35.50f;
    }

    /**
     * TEST CASE 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
     *
     * @throws ItemNotFoundException
     */
    @Test
    public void whenGatheringPreloadedPriceBySearch_thenPriceWithHigherPriorityIsReturned_case4SingleFound() throws ItemNotFoundException {
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 15,
                10, 00, 00);
        Price p = pricing.getByDateWithProductIdAndBrand(searchDate, 35455L, 1);
        assert p.getPriceList() == 3;
        assert p.getPriority() == 1;
        assert p.getPrice() == 30.50f;
    }

    /**
     * TEST CASE 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
     *
     * @throws ItemNotFoundException
     */
    @Test
    public void whenGatheringPreloadedPriceBySearch_thenPriceWithHigherPriorityIsReturned_case5SingleFoundDifferentDate() throws ItemNotFoundException {
        LocalDateTime searchDate = LocalDateTime.of(2020, 06, 16,
                21, 00, 00);
        Price p = pricing.getByDateWithProductIdAndBrand(searchDate, 35455L, 1);
        assert p.getPriceList() == 4;
        assert p.getPriority() == 1;
        assert p.getPrice() == 38.95f;
    }

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

    @Test
    public void whenGatheringNotPreloadedPriceBySearch_thenPriceDoesNotExist() {
        LocalDateTime searchDate = LocalDateTime.of(2013, 10, 10,
                12, 00, 00);
        ItemNotFoundException thrown = assertThrows(
                ItemNotFoundException.class,
                () -> pricing.getByDateWithProductIdAndBrand(searchDate, 1L, 1),
                "Expected ItemNotFoundException during search but it didn't"
        );
        assert thrown.getMessage().contains("Price not found");
    }
}
