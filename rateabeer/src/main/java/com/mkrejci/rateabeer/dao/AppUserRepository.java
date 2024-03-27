package com.mkrejci.rateabeer.dao;

import com.mkrejci.rateabeer.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
}
