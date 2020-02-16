package com.supv.challenge.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supv.challenge.service.dto.PersonDTO;

/**
 * Service Interface for managing {@link com.supv.challenge.domain.Person}.
 */
public interface PersonService {

	/**
	 * Save a person.
	 *
	 * @param personDTO the entity to save.
	 * @return the persisted entity.
	 */
	PersonDTO save(PersonDTO personDTO);

	/**
	 * Get all the people.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<PersonDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" person.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PersonDTO> findOne(Long id);

	/**
	 * Delete the "id" person.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get all the people.
	 *
	 * @return the list of entities.
	 */
	List<PersonDTO> findAll();

}
