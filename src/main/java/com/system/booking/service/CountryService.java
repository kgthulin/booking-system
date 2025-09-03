package com.system.booking.service;import java.util.Optional;

import com.system.booking.entity.Country;

public interface CountryService {

	Optional<Country> findCountryById(Long id);
}
