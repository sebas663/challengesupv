package com.supv.challenge.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.supervielle.challenge.domain.Statistics} entity.
 */
public class StatisticsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8395682445766612176L;

	private String cantidad_mujeres;

	private String cantidad_hombres;

	private String porcentaje_argentinos;

	public String getCantidad_mujeres() {
		return cantidad_mujeres;
	}

	public void setQuantityWomen(String cantidad_mujeres) {
		this.cantidad_mujeres = cantidad_mujeres;
	}

	public String getCantidad_hombres() {
		return cantidad_hombres;
	}

	public void setQuantityMen(String cantidad_hombres) {
		this.cantidad_hombres = cantidad_hombres;
	}

	public String getPorcentaje_argentinos() {
		return porcentaje_argentinos;
	}

	public void setPorcentaje_argentinos(String porcentaje_argentinos) {
		this.porcentaje_argentinos = porcentaje_argentinos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cantidad_hombres == null) ? 0 : cantidad_hombres.hashCode());
		result = prime * result + ((cantidad_mujeres == null) ? 0 : cantidad_mujeres.hashCode());
		result = prime * result + ((porcentaje_argentinos == null) ? 0 : porcentaje_argentinos.hashCode());
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
		StatisticsDTO other = (StatisticsDTO) obj;
		if (cantidad_hombres == null) {
			if (other.cantidad_hombres != null)
				return false;
		} else if (!cantidad_hombres.equals(other.cantidad_hombres))
			return false;
		if (cantidad_mujeres == null) {
			if (other.cantidad_mujeres != null)
				return false;
		} else if (!cantidad_mujeres.equals(other.cantidad_mujeres))
			return false;
		if (porcentaje_argentinos == null) {
			if (other.porcentaje_argentinos != null)
				return false;
		} else if (!porcentaje_argentinos.equals(other.porcentaje_argentinos))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StatisticsDTO{ cantidad_mujeres='" + getCantidad_mujeres() + "'" + ", cantidad_hombres='"
				+ getCantidad_hombres() + "'" + ", porcentaje_argentinos='" + getPorcentaje_argentinos() + "'" + "}";
	}
}
