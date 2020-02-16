package com.supv.challenge.service.impl;

import java.util.List;
import java.util.function.Predicate;

import org.hibernate.stat.Statistics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supv.challenge.service.PersonService;
import com.supv.challenge.service.StatisticsService;
import com.supv.challenge.service.dto.PersonDTO;
import com.supv.challenge.service.dto.StatisticsDTO;

/**
 * Service Implementation for managing {@link Statistics}.
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	private final PersonService personService;

	public StatisticsServiceImpl(PersonService personService) {
		this.personService = personService;
	}

	/**
	 * Get the statistics.
	 *
	 * @return the entity.
	 */
	@Override
	public StatisticsDTO getStatistics() {
		StatisticsDTO statisticsDTO = new StatisticsDTO();
		List<PersonDTO> list = personService.findAll();
		if (list != null) {
			statisticsDTO.setQuantityMen(String.valueOf(countByFilter(list, predicateBySex("H"))));
			statisticsDTO.setQuantityWomen(String.valueOf(countByFilter(list, predicateBySex("M"))));
			statisticsDTO.setPorcentaje_argentinos(String.valueOf(
					calculatePercentaje(list.stream().count(), countByFilter(list, predicateByCountry("ARG")))));
		}
		return statisticsDTO;
	}

	/**
	 * Calculate percentaje handler.
	 * 
	 * @param total
	 * @param quantity
	 * @return
	 */
	private double calculatePercentaje(long total, long quantity) {
		double porcentaje = quantity * 100 / total;
		return porcentaje;
	}

	/**
	 * Count from list by filter.
	 * 
	 * @param list
	 * @param predicate
	 * @return
	 */
	private long countByFilter(List<PersonDTO> list, Predicate<PersonDTO> predicate) {
		long quantity = 0;
		if (list != null) {
			quantity = list.stream().filter(predicate).count();
		}
		return quantity;
	}

	/**
	 * Predicate for filter by sex.
	 * 
	 * @param sex
	 * @return
	 */
	public static Predicate<PersonDTO> predicateBySex(String sex) {
		return p -> p.getSex().equalsIgnoreCase(sex);
	}

	/**
	 * Predicate for filter by country.
	 *  
	 * @param country
	 * @return
	 */
	public static Predicate<PersonDTO> predicateByCountry(String country) {
		return p -> p.getCountry().equalsIgnoreCase(country);
	}

}
