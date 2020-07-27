package principal;

import java.text.ParseException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Presentacion {
	static ExecutorService exec=Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws ParseException {
		String ruta1="pedido_tienda1.txt";
		String ruta2="pedido_tienda2.txt";
		String ruta3="pedido_tienda3.txt";
		
		Future<?> f1=exec.submit((new Tarea("tienda1", ruta1)));
		Future<?> f2=exec.submit((new Tarea("tienda2", ruta2)));	
		Future<?> f3=exec.submit((new Tarea("tienda3", ruta3)));	
		
		while(!f1.isDone()||!f2.isDone()||!f3.isDone()){
			System.out.println("Procesando...");
		}
		System.out.println("Tareas completadas.");
		exec.shutdown();
	}

}
