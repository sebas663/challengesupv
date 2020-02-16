package com.supv.challenge.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.supervielle.challenge.domain.Relation} entity.
 */
public class RelationShipDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5690930061464738795L;

	private Integer personId;

	private Integer personId2;

	private String relationShipId1;

	private String relationShipId2;

	public RelationShipDTO(Integer personId, Integer personId2, String relationShipId1, String relationShipId2) {
		super();
		this.personId = personId;
		this.personId2 = personId2;
		this.relationShipId1 = relationShipId1;
		this.relationShipId2 = relationShipId2;
	}

	/**
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @return the personId2
	 */
	public Integer getPersonId2() {
		return personId2;
	}

	/**
	 * @param personId2 the personId2 to set
	 */
	public void setPersonId2(Integer personId2) {
		this.personId2 = personId2;
	}

	/**
	 * @return the relationShipId1
	 */
	public String getRelationShipId1() {
		return !relationShipId1.isEmpty() ? "Es " + relationShipId1 + " de " + this.personId2 : "";
	}

	/**
	 * @param relationShipId1 the relationShipId1 to set
	 */
	public void setRelationShipId1(String relationShipId1) {
		this.relationShipId1 = relationShipId1;
	}

	/**
	 * @return the relationShipId2
	 */
	public String getRelationShipId2() {
		return !relationShipId2.isEmpty() ? "Es " + relationShipId2 + " de " + this.personId : "";
	}

	/**
	 * @param relationShipId2 the relationShipId2 to set
	 */
	public void setRelationShipId2(String relationShipId2) {
		this.relationShipId2 = relationShipId2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((personId2 == null) ? 0 : personId2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelationShipDTO other = (RelationShipDTO) obj;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (personId2 == null) {
			if (other.personId2 != null)
				return false;
		} else if (!personId2.equals(other.personId2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RelationShipDTO [personId=" + personId + ", personId2=" + personId2 + ", relationShipId1="
				+ relationShipId1 + ", relationShipId2=" + relationShipId2 + "]";
	}

}
