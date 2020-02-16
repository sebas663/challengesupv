package com.supv.challenge.service;

import com.supv.challenge.service.dto.StatisticsDTO;

/**
 * Service Interface for managing {@link com.supervielle.challenge.domain.Statistics}.
 * 
 */
public interface StatisticsService {

	/**
	 * Get the statistics.
	 *
	 * @return the entity.
	 */
	StatisticsDTO getStatistics();

}
