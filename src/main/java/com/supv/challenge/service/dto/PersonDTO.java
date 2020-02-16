package com.supv.challenge.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.supv.challenge.web.rest.validation.Adult;
import com.supv.challenge.web.rest.validation.Sex;

/**
 * A DTO for the {@link com.supv.challenge.domain.Person} entity.
 */
public class PersonDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1946784676315602511L;

	private Long id;

    @NotBlank
    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String secondName;

    @NotBlank
    @Size(max = 30)
    private String lastName;

    @Size(max = 30)
    private String secondLastName;

    @NotBlank
    private String documentType;

    @NotNull
    private Integer documentNumber;

    @NotBlank
    @Pattern(message ="Debe contener tres letras mayusculas el codigo de nacionalidad", regexp = "[A-Z]{3}")
    private String country;

    @NotBlank
    @Sex
    private String sex;

    @NotNull
    @Past()
    @Adult(value = 18)
    private LocalDate birthday;

    @NotBlank
    private String telephoneNumber;

    @Size(max = 50)
    private String email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (personDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", secondLastName='" + getSecondLastName() + "'" +
            ", documentType='" + getDocumentType() + "'" +
            ", documentNumber=" + getDocumentNumber() +
            ", country='" + getCountry() + "'" +
            ", sex='" + getSex() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", telephoneNumber='" + getTelephoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
