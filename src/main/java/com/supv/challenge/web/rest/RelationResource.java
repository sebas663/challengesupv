package com.supv.challenge.web.rest;

import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supv.challenge.service.RelationService;
import com.supv.challenge.service.dto.RelationShipDTO;
import com.supv.challenge.util.HeaderUtil;
import com.supv.challenge.util.ResponseUtil;
import com.supv.challenge.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing
 * {@link com.supervielle.challenge.domain.Relation}.
 */
@RestController
@RequestMapping("/api")
public class RelationResource {

	private final Logger log = LoggerFactory.getLogger(RelationResource.class);

	private static final String ENTITY_NAME = "supervielleRelation";

	@Value("${supv.clientApp.name}")
	private String applicationName;

	private final RelationService relationService;

	public RelationResource(RelationService relationService) {
		this.relationService = relationService;
	}

	/**
	 * {@code POST  /personas/:id1/padre/:id2} : Create a new Match person father.
	 *
	 * @param id  the id of the person to match.
	 * @param id2 the id of the person2 to match.
	 * @return the {@link ResponseEntity} with status {@code 201 (Matched)} and with
	 *         body the new personDTO, or with status {@code 400 (Bad Request)} if
	 *         the person has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/personas/{id}/padre/{id2}")
	public ResponseEntity<Void> matchPerson(@PathVariable Integer id, @PathVariable Integer id2)
			throws URISyntaxException {
		log.debug("REST request to match Person : {} with Person : {}", id, id2);
		if (id == null || id2 == null) {
			throw new BadRequestAlertException("Cannot match person without ID", ENTITY_NAME, "id.canot.be.null");
		}
		relationService.matchPerson(id, id2);
		return ResponseEntity.noContent().headers(
				HeaderUtil.createEntityMatchedAlert(applicationName, false, ENTITY_NAME, id.toString(), id2.toString()))
				.build();
	}

	/**
	 * {@code GET  /relaciones/:id1/:id2} : get relation.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of people in body.
	 */
	@GetMapping("/relaciones/{id1}/{id2}")
	public ResponseEntity<RelationShipDTO> getRelation(@PathVariable(value = "id1") Integer id,
			@PathVariable(value = "id2") Integer id2) {
		log.debug("REST request to get relation : {} with id : {}", id, id2);
		if (id == null || id2 == null) {
			throw new BadRequestAlertException("Cannot get relation without ID", ENTITY_NAME, "id.canot.be.null");
		}
		Optional<RelationShipDTO> relationShipDTO = relationService.findRelation(id, id2);
		return ResponseUtil.wrapOrNotFound(relationShipDTO);
	}
}
