package service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import com.google.gson.Gson;

import model.CaseJson;
import model.Caso;
import util.Utilidades;

public class JsonService extends BaseService {
	
	String RUTA;
	public JsonService(String ruta) {
		this.RUTA=ruta;
		
	}
	
	@Override
	public Stream<Caso> getStream() {
		Gson gson=new Gson()
				.newBuilder()
				.setDateFormat("yyyy-MM-dd")
				.create();
		try (FileReader reader=new FileReader(RUTA);){
			CaseJson[] casos=gson.fromJson(reader, CaseJson[].class);
			return Arrays.stream(casos)
					.map(cj->Utilidades.convertirJsonACaso(cj));
		} catch (IOException e) {
			
			e.printStackTrace();
			return Stream.empty();
		}
		
	}

}
