package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.text.DecimalFormat;

public class ClienteCentral {

	public static void main(String[] args) throws ClassNotFoundException {
		try (Socket cliente = new Socket("localhost", 8100)) {
			PrintStream out = new PrintStream(cliente.getOutputStream());
			String aBuscar="Murcia";
			out.println(aBuscar);
			DataInputStream dataDsdServer=new DataInputStream(cliente.getInputStream());
			int casosComunidad=dataDsdServer.readInt();
			double mediaComunidad=dataDsdServer.readDouble();
			System.out.println("\nEn la comunidad de "+aBuscar+" hubo "+casosComunidad+" casos.");
			DecimalFormat myFormatter = new DecimalFormat("###.##");
		    String contagios = myFormatter.format(mediaComunidad);
			System.out.println("La media fue de "+contagios+" contagios diarios.");
			String pico = dataDsdServer.readUTF();
			System.out.println("El pico de casos se alcanzó el día "+pico);
		} catch (ConnectException co) {
			System.out.println("El servidor no está recibiendo consultas...");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
