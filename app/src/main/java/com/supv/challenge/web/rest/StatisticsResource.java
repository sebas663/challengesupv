package com.supv.challenge.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supv.challenge.service.StatisticsService;
import com.supv.challenge.service.dto.StatisticsDTO;

/**
 * REST controller for managing
 * {@link com.supervielle.challenge.domain.Statistics}.
 */
@RestController
@RequestMapping("/api")
public class StatisticsResource {

	private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

	private final StatisticsService statisticsService;

	public StatisticsResource(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	/**
	 * {@code GET  /estadisticas} : get all the statistics.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of statistics in body.
	 */
	@GetMapping("/estadisticas")
	public StatisticsDTO getStatistics() {
		log.debug("REST request to get all Statistics");
		return statisticsService.getStatistics();
	}

}
