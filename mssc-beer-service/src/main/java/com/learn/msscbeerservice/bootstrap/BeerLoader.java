package com.learn.msscbeerservice.bootstrap;

import com.learn.msscbeerservice.domain.Beer;
import com.learn.msscbeerservice.repository.BeerRepository;
import com.learn.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "4646464641111";
    public static final String BEER_2_UPC = "8888555552112";
    public static final String BEER_3_UPC = "6464646999999";

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading Beer in DB...");
        if(beerRepository.count() == 0 ) {
            loadBeerObjects();
        }
    }

    private void loadBeerObjects() {
        Beer b1 = Beer.builder()
                .beerName("Mango Beer")
                .beerStyle(BeerStyleEnum.IPA.name())
                .minOnHand(22)
                .quantityToBrew(250)
                .price(new BigDecimal("19.95"))
                .upc(BEER_1_UPC)
                .build();

        Beer b2 = Beer.builder()
                .beerName("Apple Cider Beer")
                .beerStyle(BeerStyleEnum.PALE_ALE.name())
                .minOnHand(15)
                .quantityToBrew(100)
                .price(new BigDecimal("13.95"))
                .upc(BEER_2_UPC)
                .build();

        Beer b3 = Beer.builder()
                .beerName("Pinball Beer")
                .beerStyle(BeerStyleEnum.PALE_ALE.name())
                .minOnHand(30)
                .quantityToBrew(300)
                .price(new BigDecimal("10.95"))
                .upc(BEER_3_UPC)
                .build();

        beerRepository.save(b1);
        beerRepository.save(b2);
        beerRepository.save(b3);
    }
}
