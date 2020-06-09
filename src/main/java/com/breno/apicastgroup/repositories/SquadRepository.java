package com.breno.apicastgroup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breno.apicastgroup.entities.Squad;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {
	
}
