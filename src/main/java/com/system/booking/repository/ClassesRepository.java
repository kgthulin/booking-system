package com.system.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.system.booking.entity.Classes;
import com.system.booking.entity.Package;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
	
	@Query
	List<Classes> findAllByPackagge(@Param("packagge") Package packagge);

}
