package com.supv.challenge.service;

import java.util.List;
import java.util.Optional;

import com.supv.challenge.service.dto.RelationDTO;
import com.supv.challenge.service.dto.RelationShipDTO;

/**
 * Service Interface for managing
 * {@link com.supervielle.challenge.domain.Relation}.
 */
public interface RelationService {

	/**
	 * Save a relation.
	 *
	 * @param relationDTO the entity to save.
	 * @return the persisted entity.
	 */
	RelationDTO save(RelationDTO relationDTO);

	/**
	 * Get all the relations.
	 *
	 * @return the list of entities.
	 */
	List<RelationDTO> findAll();

	/**
	 * Get the "id" relation.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<RelationDTO> findOne(Long id);

	/**
	 * Delete the "id" relation.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	/**
	 * Get the "id" relations.
	 *
	 * @param id  the id of the entity.
	 * @param id2 the id of the entity2.
	 * @return the entity.
	 */
	Optional<RelationShipDTO> findRelation(Integer id, Integer id2);

	/**
	 * Match the "id" person with the "id2" person.
	 * 
	 * @param id
	 * @param id2
	 */
	void matchPerson(Integer id, Integer id2);
}
