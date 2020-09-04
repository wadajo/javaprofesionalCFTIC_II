package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Caso;
import util.Utilidades;

public class CsvService extends BaseService {
	String ruta;
	Path path;
	
	public CsvService(String ruta) {
		this.ruta=ruta;
		path=Paths.get(ruta);
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Stream<Caso> getStream() {
		try {
			return Files.lines(path,StandardCharsets.UTF_8)
					.map(Utilidades::stringToCaso);
		} catch (IOException e) {
			e.printStackTrace();
			return Stream.empty();
		}
	}

}
