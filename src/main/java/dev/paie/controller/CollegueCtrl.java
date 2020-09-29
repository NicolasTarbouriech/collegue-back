package dev.paie.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.paie.dto.CreerCollegueRequestDto;
import dev.paie.dto.CreerCollegueResponseDto;
import dev.paie.entity.Collegue;
import dev.paie.service.CollegueService;

@CrossOrigin
@RestController
@RequestMapping("collegues")
public class CollegueCtrl {

	private CollegueService collegueService;

	public CollegueCtrl(CollegueService collegueService) {
		super();
		this.collegueService = collegueService;
	}

	// GET /collegues
	@GetMapping
	public List<String> rechercherMatriculesParNom(@RequestParam String nom) {
		return this.collegueService.rechercherParNom(nom);
	}
	

	@GetMapping("photos")
	public List<CollegueGalerie> findAllGalerie() {
		return collegueService.ListAllCollegue().stream().map(c -> new CollegueGalerie(c.getMatricule(), c.getPhotoUrl()))
				.collect(Collectors.toList());
	}


	// GET /collegues/????
		@GetMapping("{matricule}")
		public ResponseEntity<?> rechercherParMatricule(@PathVariable String matricule) {
			Optional<Collegue> optCol = this.collegueService.rechercherParMat(matricule);
			
			if(optCol.isPresent()) {
				return ResponseEntity.ok(optCol.get());
			} else {
				return ResponseEntity.notFound().build();
			}
			
		}

	// POST /collegues
	@PostMapping
	public CreerCollegueResponseDto creerNouveauCollegue(@RequestBody @Valid CreerCollegueRequestDto dto) {
		Collegue collegueCree = this.collegueService.creerCollegue(dto);

		return new CreerCollegueResponseDto(collegueCree.getMatricule(), collegueCree.getEmail());
	}
	
	// Supprimer un collegue par le matricule
	@DeleteMapping
	public void deleteCollegueParMatricule(@RequestParam String matricule) {
		collegueService.deleteCollegue(matricule);
	}

}
