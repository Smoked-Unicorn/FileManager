package pruebas;

import clases.IO.FileManager;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileManager fm = new FileManager("/");
		fm.explorarRuta();
		fm.abrirScanner();
		
		while(fm.hasNext()){
			fm.almacenarLinea();
		}
		
		fm.append("Añadido a la linea anterior.");
		fm.addLine("Esta nueva linea lo va a petar!!!");
		fm.alternar(fm.getRuta());
		fm.printBuffer();
		fm.dump();
		fm.close();
		
	}

}
