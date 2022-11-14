package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.InvalidArgumentException;
import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;
import com.capitol.pricing.repositories.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
    public Price getByPriceList(final Long priceList) throws ItemNotFoundException {
        return priceRepository.findByPriceList(priceList).orElseThrow(() -> new ItemNotFoundException("Price not found for priceList/id  " + priceList));
    }

    @Override
    public Price getByDateWithProductIdAndBrand(final LocalDateTime searchDate,
                                                      long productId,
                                                      int brandId) throws ItemNotFoundException, InvalidArgumentException {
        if (Stream.of(searchDate, productId, brandId).anyMatch(Objects::isNull))
                throw new InvalidArgumentException(String.format("Price not found because of missing arguments searchDate %s productId %s and brandId %s", searchDate, productId, brandId));
        return priceRepository.findTop3ByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderByPriorityDesc(searchDate,
                        searchDate, productId, brandId)
                .stream().findFirst().orElseThrow(() -> new ItemNotFoundException(String.format("Price not found for searchDate %s productId %s and brandId %s", searchDate, productId, brandId)));
    }
}
