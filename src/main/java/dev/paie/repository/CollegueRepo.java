package dev.paie.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.paie.entity.Collegue;

public interface CollegueRepo extends JpaRepository<Collegue, Integer>{

	@Query("select c.matricule from Collegue c where c.nom=:nom")
	List<String> findByNom(@Param("nom") String nom);

	Optional<Collegue> findByMatricule(String matricule);
	
	@Query("delete from Collegue c where c.matricule=:matricule")
	void deleteCollegueParMatricule (@Param("matricule")String matricule);
}

