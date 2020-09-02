package util;

import model.Pais;
import model.PaisJson;

public class Utilidades {
	public static Pais convertirJsonAPais (PaisJson p) {
		return new Pais(
				p.getName(),
				p.getCapital(),
				p.getArea(),
				p.getPopulation(),
				p.getRegion(),
				p.getAlpha2Code(),
				p.getLatlng());
	}
}
