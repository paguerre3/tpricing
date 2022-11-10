package com.capitol.pricing.ctrl;

import com.capitol.pricing.exception.ItemNotFoundException;
import com.capitol.pricing.model.Price;
import com.capitol.pricing.repo.PricingRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing")
public class PricingController {

    private final PricingRepo pricingRepo;

    // Injection via constructor instead of autowiring via
    // attribute / method:
    public PricingController(PricingRepo pricingRepo) {
        this.pricingRepo = pricingRepo;
    }

    @GetMapping("/all")
    public List<Price> getAllPrices() {
        return pricingRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable(value = "id") Long priceId)
            throws ItemNotFoundException {
        Price p = pricingRepo.findById(priceId)
                .orElseThrow(() -> new ItemNotFoundException("Price not found for id " + priceId));
        return ResponseEntity.ok().body(p);
    }
}
