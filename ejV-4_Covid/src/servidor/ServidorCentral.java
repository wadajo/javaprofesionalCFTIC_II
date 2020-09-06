package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorCentral {

	public static void main(String[] args) {
		System.out.println("Esperando pedidos de las comunidades...");
		ExecutorService exec = Executors.newCachedThreadPool();		
	try (ServerSocket servidor = new ServerSocket(8100)) {
		servidor.setSoTimeout(7000);
		while (!servidor.isClosed()) {
		Socket sc=servidor.accept();		
		System.out.println("Llamada recibida desde el 8100.");		
		exec.submit(new HiloUnCliente(sc));		
		}		
	} catch (SocketTimeoutException so) {
		System.out.println("Se ha agotado el tiempo de atender consultas.");
		exec.shutdown();
	} catch (IOException e) {
		e.printStackTrace();
	} 
	}
}
