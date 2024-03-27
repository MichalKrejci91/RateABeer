package com.mkrejci.rateabeer.dao;

import com.mkrejci.rateabeer.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
