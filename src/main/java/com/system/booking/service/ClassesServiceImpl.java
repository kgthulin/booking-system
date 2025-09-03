package com.system.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.system.booking.entity.Classes;
import com.system.booking.entity.Package;
import com.system.booking.repository.ClassesRepository;

@Service
public class ClassesServiceImpl implements ClassesService {
	
	private final ClassesRepository classesRepository;
	
	public ClassesServiceImpl(ClassesRepository classesRepository) {
		this.classesRepository = classesRepository;
	}

	@Override
	public Optional<Classes> findClassesById(Long id) {
		return this.classesRepository.findById(id);
	}
	
	@Override
	public List<Classes> findAllByPackagge(Package packagge) {
		return this.classesRepository.findAllByPackagge(packagge);
	}

	@Override
	public Classes createClasses(Classes classes) {
		return this.classesRepository.save(classes);
	}

	@Override
	public Classes updateClasses(Classes classes) {
		return this.classesRepository.saveAndFlush(classes);
	}

}
