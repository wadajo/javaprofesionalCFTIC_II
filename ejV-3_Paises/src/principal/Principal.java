package principal;

import model.Pais;
import service.BaseService;
import service.JsonService;

public class Principal {

	public static void main(String[] args) {
		BaseService service  = new JsonService("paises_orig.json");
		String region="Asia";
		
		System.out.println("En el archivo hay "+service.contarPaises()+" países.\n");
		/*
		service.paisesEnRegion(region)
			.forEach(p->System.out.println(p.getNombre()+"--"+"Población: "+p.getPoblacion()+" habitantes."));
	
		System.out.println("\nEl país más poblado es: "
				+service.paisMasPoblado().getNombre()
				+" con "+service.paisMasPoblado().getPoblacion()+" habitantes.");
		
		Long habitantes=100000000L;
		System.out.println("\nCon más de "+habitantes+" de habitantes hay "
				+service.paisesConMasHabitantesQue(habitantes)
				+" países.");
		
		service.paisesCuyoNombreContenga("Island")
			.forEach(p->System.out.println(p.getNombre()));
		String nuevoCódigo="ar";
		Double[] coordenadas=service.coordenadasDesdeAlpha2Code(nuevoCódigo);
		System.out.println("Las coordenadas de ese país son "
		+coordenadas[0]+" y "+coordenadas[1]);
		*/
		System.out.println("\nLa población total de la región "+region+" es "+
				service.poblacionTotalRegion(region)+" habitantes.");
		System.out.println("\nLa media de la región "+region+" es "+
				service.poblacionMediaRegion(region).intValue()+" habitantes x país.");
		/*System.out.println("\nPaíses en ");
				service.paisesPorRegion()
					.forEach((k,v)->{
						System.out.println("\nRegion: "+k);
						System.out.println("País: ");v.forEach(p->System.out.println(p.getNombre()));
					});*/
		System.out.println("\nPoblación de ");
		service.poblacionDeRegionesMap()
				.forEach((k,v)->{
					System.out.println("\nRegión: "+k);
					System.out.println("Habitantes: "+v);
				});
	}

}
