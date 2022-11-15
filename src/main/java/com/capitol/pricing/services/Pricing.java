package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.MissingArgumentException;
import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface Pricing {
    List<Price> getAll();

    // price list is interpreted as price id:
    Price getByPriceList(Long priceList) throws ItemNotFoundException;

    Price getByDateWithProductIdAndBrand(final LocalDateTime searchDate,
                                         long productId,
                                         int brandId) throws ItemNotFoundException, MissingArgumentException;
}
