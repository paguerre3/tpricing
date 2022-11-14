package com.capitol.pricing.services;

import com.capitol.pricing.exceptions.ItemNotFoundException;
import com.capitol.pricing.models.Price;

import java.util.List;

public interface Pricing {
    List<Price> getAll();

    // price list is interpreted as price id:
    Price getByPriceList(Long priceList) throws ItemNotFoundException;
}
