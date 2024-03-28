package com.mkrejci.rateabeer;

import com.mkrejci.rateabeer.dao.BeerRepository;
import com.mkrejci.rateabeer.entity.Beer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Fills the Beer DB after the first start of the App
 */
@Component
public class BeerDbRunner implements ApplicationRunner {
    private BeerRepository repository;

    public BeerDbRunner(BeerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(!repository.findAll().isEmpty()) {
            System.out.println("Database of Beers is already filled");
            return;
        }

        System.out.println("Loading objects from external source");
        String url = "https://random-data-api.com/api/v2/beers?size=100";
        RestTemplate template = new RestTemplate();

        Beer[] retrievedBeerList = template.getForObject(url, Beer[].class);

        for(Beer tempBeer : retrievedBeerList) {
            //System.out.println(tempBeer);
            repository.save(tempBeer);
        }
        System.out.println("Objects saved to Beer DB");
    }
}
