package com.breno.apicastgroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breno.apicastgroup.entities.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
	
}
