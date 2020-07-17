package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class NotasService {
	String ruta = "prueba.txt";
	
	public void ingresarNota (double nota) {
		try(FileOutputStream fos = new FileOutputStream(ruta, true);
				PrintStream out = new PrintStream(fos)){
			out.println(nota);
		} catch (FileNotFoundException ex){
			System.out.println("El directorio es incorrecto.");
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public double calcularMedia () {
		double suma=0.0;
		int lineas=0;
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br= new BufferedReader(fr)){			
			String nota;
			while ((nota=br.readLine())!=null) {
				suma+=Double.parseDouble(nota);
				lineas++;
			}			
		} catch (IOException e) {
			System.out.println("Se ha producido un error.");
			e.printStackTrace();
		}
		return suma/lineas;
	}
	
	public List<Double> devolverNotas () {
		List<Double> notas = new ArrayList<>();
		try (FileReader fr = new FileReader(ruta);
				BufferedReader br= new BufferedReader(fr)){			
			String nota;
			while ((nota=br.readLine())!=null) {
				notas.add(Double.parseDouble(nota));				
			}			
		} catch (IOException e) {
			System.out.println("Se ha producido un error.");
			e.printStackTrace();
		}
		return notas;
	}
	
	public int calcularAprobados () {
		List<Double> notas = devolverNotas();
		int aprobados=0;
		for (Double n : notas) {
			aprobados=(n>=6)?++aprobados:aprobados;
		}
		return aprobados;
	}
}
