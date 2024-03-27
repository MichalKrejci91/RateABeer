package com.mkrejci.rateabeer.resource;

import com.mkrejci.rateabeer.dao.BeerRepository;
import com.mkrejci.rateabeer.entity.Beer;
import com.mkrejci.rateabeer.exception.BeerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/beers")
public class BeerResource {
    private BeerRepository repository;

    public BeerResource(BeerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Beer>> listAllBeers() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beer> retrieveBeer(@PathVariable int id) {
        Optional <Beer> theBeer = repository.findById(id);

        if(theBeer.isEmpty()) {
            throw new BeerNotFoundException("Beer with ID " + id + " was not found");
        }

        return new ResponseEntity<>(theBeer.get(), HttpStatus.OK);
    }

    /*
    @PostMapping("/add")
    public ResponseEntity<Beer> addBeer(@RequestBody Beer beer) {
        return new ResponseEntity<>(service.save(beer), HttpStatus.CREATED) ;
    }

    @PutMapping("/update")
    public ResponseEntity<Beer> updateBeer(@RequestBody Beer theBeer) {
        Beer updatedBeer = service.update(theBeer);

        return new ResponseEntity<>(updatedBeer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBeer(@PathVariable int id) {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    */
}
