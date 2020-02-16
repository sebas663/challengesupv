package com.supv.challenge.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supervielle.challenge.domain.Relation} entity.
 */
public class RelationDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8828380783189291208L;

	private Long id;

    @NotNull
    private Integer personId;

    private Integer fatherId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelationDTO relationDTO = (RelationDTO) o;
        if (relationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RelationDTO{" +
            "id=" + getId() +
            ", personId=" + getPersonId() +
            ", fatherId=" + getFatherId() +
            "}";
    }
}
