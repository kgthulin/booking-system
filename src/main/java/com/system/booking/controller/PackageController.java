package com.system.booking.controller;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.booking.entity.AppUser;
import com.system.booking.entity.Classes;
import com.system.booking.entity.Country;
import com.system.booking.entity.Package;
import com.system.booking.exception.ResourceNotFoundException;
import com.system.booking.service.ClassesService;
import com.system.booking.service.CountryService;
import com.system.booking.service.PackageService;
import com.system.booking.service.UserService;

@RequestMapping("api/packages/")
@RestController
public class PackageController {
	
	private final PackageService packageService;
	private final UserService userService;
	private final ClassesService classesService;
	private final CountryService countryService;

	public PackageController(PackageService packageService, UserService userService, ClassesService classesService, CountryService countryService) {
		this.packageService = packageService;
		this.userService = userService;
		this.classesService = classesService;
		this.countryService = countryService;
	}
	
	@PostMapping("/users/{userId}")
    public ResponseEntity<Package> createPackage(@PathVariable("userId") Long id, @RequestParam Long countryId, 
    		@RequestBody Package packagge) {
		Country country = this.countryService.findCountryById(countryId).orElseThrow(() -> new ResourceNotFoundException());
		AppUser user = this.userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException());
		Package newPackage = new Package(packagge.getName(), false, false, packagge.getPrice(), 
				packagge.getCredit(), packagge.getExpiredDate(), country, user);
    	this.packageService.createPackage(newPackage);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(newPackage);
    }
	
    @GetMapping("/available")
    public ResponseEntity<List<Package>> findAllAvailablePackages() {
    	List<Package> packageList = this.packageService.findAllByIsPurchased(false);
    	return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(packageList);
    }
    
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Package>> findAllPurchasedPackages(@PathVariable("userId") Long id) {
    	AppUser user = this.userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException());
    	List<Package> packageList = this.packageService.findAllByIsPurchasedAndUser(true, user);
    	return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(packageList);
    }
    
    @GetMapping("/{packageId}/classes")
    public ResponseEntity<List<Classes>> findAllAvailableClassesByPackage(@PathVariable("packageId") Long id) {
    	Package packagge = this.packageService.findPackageById(id).orElseThrow(() -> new ResourceNotFoundException());
    	List<Classes> classesList = this.classesService.findAllByPackagge(packagge);
    	List<Classes> responseList = classesList.stream().filter(c -> c.getAvailable() > 0).toList();
    	return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(responseList);
    }

}
