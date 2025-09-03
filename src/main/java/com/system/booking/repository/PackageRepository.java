package com.system.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.booking.entity.AppUser;
import com.system.booking.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

	@Query
	List<Package> findAllByIsPurchased(@Param("isPurchased") Boolean isPurchased);
	
	@Query
	List<Package> findAllByIsPurchasedAndUser(@Param("isPurchased") Boolean isPurchased, 
			@Param("user") AppUser user);
}

