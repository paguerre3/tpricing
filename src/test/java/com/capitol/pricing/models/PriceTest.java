package com.capitol.pricing.models;

import com.capitol.pricing.models.enums.Currency;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class PriceTest {

    @Test
    public void whenModifyingOriginalObject_thenCloneShouldNotChange() {
        LocalDateTime sd = LocalDateTime.now();
        Price p = new Price();
        p.setBrandId(1);
        p.setStartDate(sd);
        p.setEndDate(sd.plus(7, ChronoUnit.DAYS));
        p.setPriceList(1);
        p.setProductId(1234);
        p.setPriority(1);
        p.setPrice(10.55f);
        p.setCurrency(Currency.EUR);
        Price pClone = (Price) SerializationUtils.clone(p);
        assert p.equals(pClone);
        assert p.hashCode() == pClone.hashCode();
        p.setPriceList(2);
        assert pClone.getPriceList() == 1;
        assert !p.equals(pClone);
        assert p.hashCode() != pClone.hashCode();
    }
}
