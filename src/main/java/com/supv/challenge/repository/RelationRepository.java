package com.supv.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supv.challenge.domain.Relation;

/**
 * Spring Data repository for the Relation entity.
 */
@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {
	
	/**
	 * Return list of List<Relation> by ids.
	 * 
	 * @param ids
	 * @return List<Relation>
	 */
	@Query("SELECT new com.supv.challenge.domain.Relation(personId, fatherId) "
			+ "FROM Relation where personId in(:ids)")
	List<Relation> fetchRelationByIds(List<Integer> ids);
}
