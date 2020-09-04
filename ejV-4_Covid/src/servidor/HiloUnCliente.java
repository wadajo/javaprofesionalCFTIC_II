package servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import service.BaseService;
import service.JsonService;

public class HiloUnCliente implements Runnable {
	private Socket conexionParaCliente;
	private final String RUTA="/Users/usuario/Documents/";	
	private Path path;
	
	public HiloUnCliente(Socket individual) {
		this.conexionParaCliente=individual;
		path=Paths.get(RUTA);
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void run() {
		BaseService servicio = new JsonService("datos_ccaas.json");
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		SimpleDateFormat formato = new SimpleDateFormat("dd-MM");
		try (DataOutputStream out = new DataOutputStream(conexionParaCliente.getOutputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(conexionParaCliente.getInputStream()))	) {
			synchronized(reader) {
			String comunidad=reader.readLine();
			int cantidadCasos=servicio.totalPositivosComunidad(comunidad);
			out.writeInt(cantidadCasos);
			double mediaCasos=servicio.mediaPositivosComunidad(comunidad);			
			out.writeDouble(mediaCasos);						
			Date fechaPico=servicio.picoContagios(comunidad);
			out.writeUTF(formato.format(fechaPico));
			Files.writeString(path.resolve("informe_"+comunidad+".txt"), 
					comunidad+System.lineSeparator()
						+"Positivos: "+cantidadCasos+System.lineSeparator()
						+"Media de positivos por día: "+myFormatter.format(mediaCasos)+System.lineSeparator()
						+"Fecha pico de contagios: "+formato.format(fechaPico), 
					StandardCharsets.UTF_8,
					StandardOpenOption.CREATE);			
			}
			} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				conexionParaCliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	}
}
