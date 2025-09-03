package com.system.booking.service;import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.booking.entity.Country;
import com.system.booking.repository.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService {
	
	private final CountryRepository countryRepository;
	
	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Optional<Country> findCountryById(Long id) {
		return this.countryRepository.findById(id);
	}
}
