package com.system.booking.service;

import java.util.List;
import java.util.Optional;

import com.system.booking.entity.AppUser;
import com.system.booking.entity.Package;

public interface PackageService {

	Optional<Package> findPackageById(Long id);
	List<Package> findAllByIsPurchased(Boolean isPurchased);
	List<Package> findAllByIsPurchasedAndUser(Boolean isPurchased, AppUser user);
	Package createPackage(Package packagge);
	Package updatePackage(Package packagge);
}
