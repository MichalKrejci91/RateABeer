package com.mkrejci.rateabeer.dao;

import com.mkrejci.rateabeer.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Integer> {
}
