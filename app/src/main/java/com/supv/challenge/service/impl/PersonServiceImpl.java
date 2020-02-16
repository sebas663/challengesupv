package com.supv.challenge.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supv.challenge.domain.Person;
import com.supv.challenge.repository.PersonRepository;
import com.supv.challenge.service.PersonService;
import com.supv.challenge.service.dto.PersonDTO;
import com.supv.challenge.service.mapper.PersonMapper;

/**
 * Service Implementation for managing {@link Person}.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

	private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);

	private final PersonRepository personRepository;

	private final PersonMapper personMapper;

	public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
		this.personRepository = personRepository;
		this.personMapper = personMapper;
	}

	/**
	 * Save a person.
	 *
	 * @param personDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public PersonDTO save(PersonDTO personDTO) {
		log.debug("Request to save Person : {}", personDTO);
		Person person = personMapper.toEntity(personDTO);
		person = personRepository.save(person);
		return personMapper.toDto(person);
	}

	/**
	 * Get all the people.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PersonDTO> findAll(Pageable pageable) {
		log.debug("Request to get all People");
		return personRepository.findAll(pageable).map(personMapper::toDto);
	}

	/**
	 * Get one person by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PersonDTO> findOne(Long id) {
		log.debug("Request to get Person : {}", id);
		return personRepository.findById(id).map(personMapper::toDto);
	}

	/**
	 * Delete the person by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Person : {}", id);
		personRepository.deleteById(id);
	}

	/**
	 * Get all the people.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PersonDTO> findAll() {
		log.debug("Request to get all People");
		List<Person> people = personRepository.findAll();
		return people.stream().map(personMapper::toDto).collect(Collectors.toList());
	}

}
