package co.test.hulk.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.test.hulk.store.exception.ResourceNotFoundException;
import co.test.hulk.store.model.Accesory;
import co.test.hulk.store.repository.AccesoryRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class AccesoryController {
	@Autowired
	private AccesoryRepository accesoryRepository;

	@PersistenceContext
	private EntityManager em;

	@RequestMapping("/accesories")
	public List getAllAccesories() {
		List list = accesoryRepository.findAll();
		return list;
	}

	@GetMapping("/accesories/{id}")
	public ResponseEntity<Accesory> getAccesoryById(@PathVariable(value = "id") Long accesoryId)
			throws ResourceNotFoundException {
		Accesory accesory = accesoryRepository.findById(accesoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Accesory not found for this id :: " + accesoryId));
		return ResponseEntity.ok().body(accesory);
	}

	@PostMapping("/accesories")
	public Accesory createAccesory(@Valid @RequestBody Accesory accesory) {
		return accesoryRepository.save(accesory);
	}

	@PutMapping("/accesories/{id}")
	public ResponseEntity<Accesory> updateAccesory(@PathVariable(value = "id") Long accesoryId,
			@Valid @RequestBody Accesory accesoryDetails) throws ResourceNotFoundException {
		Accesory accesory = accesoryRepository.findById(accesoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Accesory not found for this id :: " + accesoryId));

		accesory.setName(accesoryDetails.getName());
		final Accesory updatedAccesory = accesoryRepository.save(accesory);
		return ResponseEntity.ok(updatedAccesory);
	}

	@DeleteMapping("/accesories/{id}")
	public Map<String, Boolean> deleteAccesory(@PathVariable(value = "id") Long accesoryId)
			throws ResourceNotFoundException {
		Accesory accesory = accesoryRepository.findById(accesoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Accesory not found for this id :: " + accesoryId));

		accesoryRepository.delete(accesory);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
