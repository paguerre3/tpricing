package com.capitol.pricing.models.views;

import com.capitol.pricing.models.Price;
import com.capitol.pricing.models.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class PriceRate extends Price {

    public PriceRate() {
    }

    public PriceRate(int brandId, LocalDateTime startDate, LocalDateTime endDate, long priceList, long productId, float price, Currency currency) {
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceList = priceList;
        this.productId = productId;
        this.price = price;
        this.currency = currency;
    }

    @JsonIgnore
    @Override
    public int getPriority() {
        return super.getPriority();
    }
}
