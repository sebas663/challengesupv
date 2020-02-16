package com.supv.challenge.service.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supv.challenge.domain.Relation;
import com.supv.challenge.repository.RelationRepository;
import com.supv.challenge.service.RelationService;
import com.supv.challenge.service.dto.RelationDTO;
import com.supv.challenge.service.dto.RelationShipDTO;
import com.supv.challenge.service.mapper.RelationMapper;
import com.supv.challenge.util.RelationShipConstants;

/**
 * Service Implementation for managing {@link Relation}.
 */
@Service
@Transactional
public class RelationServiceImpl implements RelationService {

	private final Logger log = LoggerFactory.getLogger(RelationServiceImpl.class);

	private final RelationRepository relationRepository;

	private final RelationMapper relationMapper;

	public RelationServiceImpl(RelationRepository relationRepository, RelationMapper relationMapper) {
		this.relationRepository = relationRepository;
		this.relationMapper = relationMapper;
	}

	/**
	 * Save a relation.
	 *
	 * @param relationDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public RelationDTO save(RelationDTO relationDTO) {
		log.debug("Request to save Relation : {}", relationDTO);
		Relation relation = relationMapper.toEntity(relationDTO);
		relation = relationRepository.save(relation);
		return relationMapper.toDto(relation);
	}

	/**
	 * Get all the relations.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<RelationDTO> findAll() {
		log.debug("Request to get all Relations");
		return relationRepository.findAll().stream().map(relationMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one relation by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<RelationDTO> findOne(Long id) {
		log.debug("Request to get Relation : {}", id);
		return relationRepository.findById(id).map(relationMapper::toDto);
	}

	/**
	 * Delete the relation by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Relation : {}", id);
		relationRepository.deleteById(id);
	}

	/**
	 * Match the "id" person with the "id2" person.
	 * 
	 * @param id
	 * @param id2
	 */
	@Override
	public void matchPerson(Integer id, Integer id2) {
		Relation relations = new Relation();
		relations.setPersonId(id);
		relations.setFatherId(id2);
		relationRepository.save(relations);
	}

	/**
	 * Get the "id" relations.
	 *
	 * @param id  the id of the entity.
	 * @param id2 the id of the entity2.
	 * @return the entity.
	 */
	@Override
	public Optional<RelationShipDTO> findRelation(Integer id, Integer id2) {
		RelationShipDTO relationShipDTO = null;
		List<Relation> relations = relationRepository.fetchRelationByIds(Arrays.asList(id, id2));
		Relation principal = getRelationFromList(relations, id);
		Relation unknow = getRelationFromList(relations, id2);

		if (isNotNullWithFatherID(principal) && isNotNullWithFatherID(unknow)
				&& !hasRelationshipFatherChild(principal, unknow)) {
//			brothers
			if (hasRelationship(principal, unknow)) {
				relationShipDTO = new RelationShipDTO(id, id2, RelationShipConstants.HERMANO,
						RelationShipConstants.HERMANO);
				return Optional.of(relationShipDTO);
			}
			Integer idf = principal.getFatherId();
			Integer iduf = unknow.getFatherId();
			List<Relation> fatherRelations = relationRepository.fetchRelationByIds(Arrays.asList(idf, iduf));
			Relation fatherPrincipal = getRelationFromList(fatherRelations, idf);
			Relation fatherUnknow = getRelationFromList(fatherRelations, iduf);
//			cousins
			if (hasRelationship(fatherPrincipal, fatherUnknow)) {
				relationShipDTO = new RelationShipDTO(id, id2, RelationShipConstants.PRIMO,
						RelationShipConstants.PRIMO);
				return Optional.of(relationShipDTO);
			}

//			principal is uncle of unknow
			if (hasRelationship(principal, fatherUnknow)) {
				relationShipDTO = new RelationShipDTO(id, id2, RelationShipConstants.TIO, "");
				return Optional.of(relationShipDTO);
			}
//			unknow is uncle of principal
			if (hasRelationship(unknow, fatherPrincipal)) {
				relationShipDTO = new RelationShipDTO(id, id2, "", RelationShipConstants.TIO);
				return Optional.of(relationShipDTO);
			}
		}

		relationShipDTO = new RelationShipDTO(id, id2, "", "");
		return Optional.of(relationShipDTO);
	}

	/**
	 * Check if Relation principal, Relation unknow are Father Child.
	 *  
	 * @param principal
	 * @param unknow
	 * @return
	 */
	private boolean hasRelationshipFatherChild(Relation principal, Relation unknow) {
		boolean hasRelationship = false;
		if (isNotNullWithFatherID(principal) && isNotNullWithFatherID(unknow)) {
			if (areEqualsID(principal.getFatherId(), unknow.getPersonId())) {
				hasRelationship = true;
			}
			if (areEqualsID(unknow.getFatherId(), principal.getPersonId())) {
				hasRelationship = true;
			}
		}
		return hasRelationship;
	}

	/**
	 * Check if Relation principal, Relation unknow has Relationship.
	 * 
	 * @param principal
	 * @param unknow
	 * @return
	 */
	private boolean hasRelationship(Relation principal, Relation unknow) {
		boolean hasRelationship = false;
		if (isNotNullWithFatherID(principal) && isNotNullWithFatherID(unknow)) {
			if (areEqualsID(principal.getFatherId(), unknow.getFatherId())) {
				hasRelationship = true;
			}
		}
		return hasRelationship;
	}

	/**
	 * check null entity and existence of fatherID.
	 * 
	 * @param relation
	 * @return boolean
	 */
	private boolean isNotNullWithFatherID(Relation relation) {
		boolean isNotNull = false;
		if (relation != null && relation.getFatherId() != null) {
			isNotNull = true;
		}
		return isNotNull;
	}

	/**
	 * Extract relation by id from list.
	 * 
	 * @param lst
	 * @param personId
	 * @return Relation
	 */
	private Relation getRelationFromList(List<Relation> lst, Integer personId) {
		Relation relation = null;
		if (lst != null && lst.size() > 0) {
			Optional<Relation> rel = lst.stream().filter(predicateByPersonId(personId)).findAny();
			if (rel.isPresent()) {
				relation = rel.get();
			}
		}
		return relation;
	}

	/**
	 * Predicate for filter by id.
	 * 
	 * @param personId
	 * @return boolean
	 */
	private static Predicate<Relation> predicateByPersonId(Integer personId) {
		return x -> areEqualsID(x.getPersonId().intValue(), personId);
	}

	/**
	 * Compare equality for ids.
	 * 
	 * @param id
	 * @param id2
	 * @return boolean
	 */
	private static boolean areEqualsID(Integer id, Integer id2) {
		boolean areEquals = false;
		if (id.intValue() == id2.intValue()) {
			areEquals = true;
		}
		return areEquals;
	}

}
