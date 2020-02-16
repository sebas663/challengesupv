package com.supv.challenge.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supv.challenge.service.PersonService;
import com.supv.challenge.service.dto.PersonDTO;
import com.supv.challenge.util.HeaderUtil;
import com.supv.challenge.util.PaginationUtil;
import com.supv.challenge.util.ResponseUtil;
import com.supv.challenge.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link com.supv.challenge.domain.Person}.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

	private final Logger log = LoggerFactory.getLogger(PersonResource.class);

	private static final String ENTITY_NAME = "superviellePerson";

	@Value("${supv.clientApp.name}")
	private String applicationName;

	private final PersonService personService;

	public PersonResource(PersonService personService) {
		this.personService = personService;
	}

	/**
	 * {@code POST  /personas} : Create a new person.
	 *
	 * @param personDTO the personDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new personDTO, or with status {@code 400 (Bad Request)} if
	 *         the person has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/personas")
	public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personDTO) throws URISyntaxException {
		log.debug("REST request to save Person : {}", personDTO);
		if (personDTO.getId() != null) {
			throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
		}
		PersonDTO result = personService.save(personDTO);
		return ResponseEntity
				.created(new URI("/api/personas/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /personas} : Updates an existing person.
	 *
	 * @param personDTO the personDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated personDTO, or with status {@code 400 (Bad Request)} if
	 *         the personDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the personDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/personas")
	public ResponseEntity<PersonDTO> updatePerson(@Valid @RequestBody PersonDTO personDTO) throws URISyntaxException {
		log.debug("REST request to update Person : {}", personDTO);
		if (personDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PersonDTO result = personService.save(personDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, personDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /personas} : get all the people.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of people in body.
	 */
	@GetMapping("/personas")
	public ResponseEntity<List<PersonDTO>> getAllPeople(Pageable pageable) {
		log.debug("REST request to get a page of People");
		Page<PersonDTO> page = personService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /personas/:id} : get the "id" person.
	 *
	 * @param id the id of the personDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the personDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/personas/{id}")
	public ResponseEntity<PersonDTO> getPerson(@PathVariable Long id) {
		log.debug("REST request to get Person : {}", id);
		Optional<PersonDTO> personDTO = personService.findOne(id);
		return ResponseUtil.wrapOrNotFound(personDTO);
	}

	/**
	 * {@code DELETE  /personas/:id} : delete the "id" person.
	 *
	 * @param id the id of the personDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/personas/{id}")
	public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
		log.debug("REST request to delete Person : {}", id);
		personService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

}
