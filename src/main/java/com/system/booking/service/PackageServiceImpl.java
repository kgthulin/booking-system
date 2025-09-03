package com.system.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.booking.entity.AppUser;
import com.system.booking.entity.Package;
import com.system.booking.repository.PackageRepository;

@Service
public class PackageServiceImpl implements PackageService {
	
	private final PackageRepository packageRepository;
	
	public PackageServiceImpl(PackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}

	@Override
	public Optional<Package> findPackageById(Long id) {
		return this.packageRepository.findById(id);
	}
	
	@Override
	public List<Package> findAllByIsPurchased(Boolean isPurchased) {
		return this.packageRepository.findAllByIsPurchased(isPurchased);
	}

	@Override
	public List<Package> findAllByIsPurchasedAndUser(Boolean isPurchased, AppUser user) {
		return this.packageRepository.findAllByIsPurchasedAndUser(isPurchased, user);
	}

	@Override
	public Package createPackage(Package packagge) {
		return this.packageRepository.save(packagge);
	}

	@Override
	public Package updatePackage(Package packagge) {
		return this.packageRepository.saveAndFlush(packagge);
	}

}
