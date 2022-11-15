package com.capitol.pricing.controllers;

import com.capitol.pricing.exceptions.InvalidArgumentException;
import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;
import com.capitol.pricing.services.Pricing;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
public class PriceController {
    private final Pricing pricing;

    // Injection of "interface" via constructor instead of autowiring via
    // attribute / method:
    public PriceController(Pricing pricing) {
        this.pricing = pricing;
    }

    @GetMapping("/all")
    public List<Price> getAllPrices() {
        return this.pricing.getAll();
    }

    @GetMapping("/{priceList}")
    public ResponseEntity<Price> getPriceById(@PathVariable(value = "priceList") Long priceList)
            throws ItemNotFoundException {
        // price list can be interpreted as priceId
        return ResponseEntity.ok().body(this.pricing.getByPriceList(priceList));
    }

    @GetMapping("/search/{dateToApply}/{productId}/{brandId}")
    public ResponseEntity<Price> searchPriceToApply(@PathVariable(value = "dateToApply")
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                        LocalDateTime dateToApply,
                                                    @PathVariable(value = "productId") long productId,
                                                    @PathVariable(value = "brandId")  int brandId)
            throws ItemNotFoundException, InvalidArgumentException {
        return ResponseEntity.ok().body(this.pricing.getByDateWithProductIdAndBrand(dateToApply,
                productId, brandId));
    }
}
