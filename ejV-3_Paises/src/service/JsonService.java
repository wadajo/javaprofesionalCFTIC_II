package service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import com.google.gson.Gson;

import model.Pais;
import model.PaisJson;
import util.Utilidades;

public class JsonService extends BaseService {
	
	String RUTA;
	public JsonService(String ruta) {
		this.RUTA=ruta;		
	}
	
	@Override
	public Stream<Pais> getStream() {
		Gson gson=new Gson()
				.newBuilder()
				.setDateFormat("yyyy-MM-dd")
				.create();
		try (FileReader reader=new FileReader(RUTA);){
			PaisJson[] casos=gson.fromJson(reader, PaisJson[].class);
			return Arrays.stream(casos)
					.map(paisJson->Utilidades.convertirJsonAPais(paisJson));
		} catch (IOException e) {
			
			e.printStackTrace();
			return Stream.empty();
		}
		
	}

}
