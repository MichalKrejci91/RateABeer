package com.mkrejci.rateabeer.resource;

import com.mkrejci.rateabeer.dao.AppUserRepository;
import com.mkrejci.rateabeer.dao.BeerRepository;
import com.mkrejci.rateabeer.dao.RatingRepository;
import com.mkrejci.rateabeer.entity.AppUser;
import com.mkrejci.rateabeer.entity.Beer;
import com.mkrejci.rateabeer.entity.Rating;
import com.mkrejci.rateabeer.exception.RatingNotFoundException;
import com.mkrejci.rateabeer.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Resource for creating App users & ratings
 */
@RestController
@RequestMapping("/users")
public class AppUserResource {
    private BeerRepository beerRepository;
    private AppUserRepository appUserRepository;
    private RatingRepository ratingRepository;

    public AppUserResource(BeerRepository beerRepository, AppUserRepository appUserRepository, RatingRepository ratingRepository) {
        this.beerRepository = beerRepository;
        this.appUserRepository = appUserRepository;
        this.ratingRepository = ratingRepository;
    }

    // == App user management methods ==
    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> listAllUsers() {
        return new ResponseEntity<>(appUserRepository.findAll(), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> retrieveUser(@PathVariable int id) {
        Optional<AppUser> theUser = appUserRepository.findById(id);

        if(theUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " was not found");
        }

        return new ResponseEntity<>(theUser.get(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
        return new ResponseEntity<>(appUserRepository.save(user), HttpStatus.CREATED) ;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        appUserRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // == Ratings management methods ==
    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<Rating>> getRatingsForUser(@PathVariable int id){
        Optional<AppUser> theUser = appUserRepository.findById(id);

        if(theUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " was not found");
        }

        return new ResponseEntity<>(theUser.get().getRatings(), HttpStatus.OK);
    }

    @GetMapping("/ratings/{id}/{ratingId}")
    public ResponseEntity<Rating> retrieveRatingForUser(@PathVariable int id, @PathVariable int ratingId) {
        Optional<AppUser> theUser = appUserRepository.findById(id);
        Optional<Rating> theRating = ratingRepository.findById(id);

        if(theUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " was not found");
        }

        if(theRating.isEmpty()) {
            throw new RatingNotFoundException("Rating with ID "+ ratingId + " for User with ID " + id + " was not found");
        }

        return new ResponseEntity<>(theRating.get(), HttpStatus.OK);
    }

    @PostMapping("/ratings/{userId}/{beerId}")
    public ResponseEntity<Rating> createRatingForUser(@PathVariable int userId, @PathVariable int beerId, @RequestBody @Valid Rating rating) {
        Optional<AppUser> theUser = appUserRepository.findById(userId);
        Optional<Beer> theBeer = beerRepository.findById(beerId);

        if(theUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " was not found");
        }

        if(theBeer.isEmpty()) {
            throw new RatingNotFoundException("Rating with ID " + beerId + " was not found");
        }

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        rating.setTimestamp(timestamp);
        rating.setAppUser(theUser.get());
        rating.setBeer(theBeer.get());

        Rating savedRating = ratingRepository.save(rating);

        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @PutMapping("/ratings/{ratingId}")
    public ResponseEntity<Rating> updateRating(@PathVariable int ratingId ,@RequestBody Rating newRating) {
        Optional<Rating> originalRating = ratingRepository.findById(ratingId);

        if(originalRating.isEmpty()) {
            throw new RatingNotFoundException("Rating with ID " + newRating.getId() + " was not found");
        }

        newRating.setId(originalRating.get().getId());
        newRating.setBeer(originalRating.get().getBeer());

        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        newRating.setTimestamp(timestamp);

        newRating.setAppUser(originalRating.get().getAppUser());

        Rating updatedRating = ratingRepository.save(newRating);

        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/ratings/delete/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable int id) {
        ratingRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
