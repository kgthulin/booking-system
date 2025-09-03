package com.system.booking.service;

import java.util.List;
import java.util.Optional;

import com.system.booking.entity.Classes;
import com.system.booking.entity.Package;


public interface ClassesService {

	Optional<Classes> findClassesById(Long id);
	List<Classes> findAllByPackagge(Package packagge);
	Classes createClasses(Classes classes);
	Classes updateClasses(Classes classes);
}
