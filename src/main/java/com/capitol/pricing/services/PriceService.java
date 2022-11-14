package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;
import com.capitol.pricing.repositories.PriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService implements Pricing {

    private final PriceRepository priceRepository;

    // Injection via constructor instead of autowiring via
    // attribute / method:
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> getAll() {
        return priceRepository.findAll();
    }

    @Override
    public Price getByPriceList(Long priceList) throws ItemNotFoundException {
        return priceRepository.findByPriceList(priceList).orElseThrow(() -> new ItemNotFoundException("Price not found for priceList/id  " + priceList));
    }
}
