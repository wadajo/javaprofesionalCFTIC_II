package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DecimalFormat;

public class ClienteCentral2 {

	public static void main(String[] args) throws ClassNotFoundException {
		try (Socket cliente = new Socket("localhost", 8100)) {
			PrintStream out = new PrintStream(cliente.getOutputStream());
			String aBuscar="Andalucía";
			out.println(aBuscar);
			DataInputStream dataDsdServer=new DataInputStream(cliente.getInputStream());
			int casosComunidad=dataDsdServer.readInt();
			System.out.println("\nEn la comunidad de "+aBuscar+" hubo "+casosComunidad+" positivos.");
			double mediaComunidad=dataDsdServer.readDouble();			
			DecimalFormat myFormatter = new DecimalFormat("###.##");
		    String contagios = myFormatter.format(mediaComunidad);
			System.out.println("La media fue de "+contagios+" contagios diarios.");
			String pico = dataDsdServer.readUTF();
			System.out.println("El pico de casos se alcanzó el día "+pico);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
