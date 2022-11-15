package com.capitol.pricing.models;

import com.capitol.pricing.models.enums.Currency;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "prices")
public class Price implements Serializable {
    @Column(name = "brand_id", nullable = false)
    int brandId;

    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime endDate;

    // price list is id of model, it can be renamed to id:
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "price_list", nullable = false)
    long priceList;

    @Column(name = "product_id", nullable = false)
    long productId;

    @Column(name = "priority", nullable = false)
    int priority;

    @Column(name = "price", nullable = false)
    float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    Currency currency;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public long getPriceList() {
        return priceList;
    }

    public void setPriceList(long priceList) {
        this.priceList = priceList;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price1 = (Price) o;
        return brandId == price1.brandId && priceList == price1.priceList && productId == price1.productId && priority == price1.priority && Float.compare(price1.price, price) == 0 && Objects.equals(startDate, price1.startDate) && Objects.equals(endDate, price1.endDate) && currency == price1.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, startDate, endDate, priceList, productId, priority, price, currency);
    }
}
