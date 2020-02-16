package com.supv.challenge.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Relation.
 */
@Entity
@Table(name = "relation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Relation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "person_id", nullable = false)
	private Integer personId;

	@Column(name = "father_id")
	private Integer fatherId;

	public Relation() {
		super();
	}

	public Relation(@NotNull Integer personId, Integer fatherId) {
		super();
		this.personId = personId;
		this.fatherId = fatherId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPersonId() {
		return personId;
	}

	public Relation personId(Integer personId) {
		this.personId = personId;
		return this;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getFatherId() {
		return fatherId;
	}

	public Relation fatherId(Integer fatherId) {
		this.fatherId = fatherId;
		return this;
	}

	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Relation)) {
			return false;
		}
		return id != null && id.equals(((Relation) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "Relation{" + "id=" + getId() + ", personId=" + getPersonId() + ", fatherId=" + getFatherId() + "}";
	}
}
