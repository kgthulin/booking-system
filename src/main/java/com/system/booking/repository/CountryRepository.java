package com.system.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.system.booking.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}

