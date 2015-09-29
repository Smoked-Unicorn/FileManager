
package clases.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileManager {
	
	private File archivo;
	private Scanner sc;
	private PrintWriter pw;
	private String ruta;
	private boolean integridad;
	private boolean leyendo;
	private boolean escribiendo;
	private JFileChooser explorador;
	private static final String[] extensiones = {"txt", "text-only"};
	private String extensionActual = "NULL";
	private Concatenador concat;
	
	public FileManager(final String ruta){
		this.setRuta(ruta);
		this.archivo = new File(ruta);
		concat = new Concatenador();
		leyendo = false;
		escribiendo = false;
		extensionActual = getExtension();
		try{
			integridad = esSoportado();
		}catch(FormatoNoSoportadoException e){
			e.printStackTrace();
			integridad = false;
		}
		if(!integridad){
			System.err.println("ERRORES DETECTADOS. Dispositivo vulnerable.");
			Notify.error("Error en la lectura del archivo.");
		}else{
			System.out.println("Integridad correcta: Iniciando.");
			explorador = new JFileChooser(ruta);
		}
	}
	
	private boolean esSoportado() throws FormatoNoSoportadoException{
		boolean res = false;
		
		for(int i = 0; i < extensiones.length && !res; i++){
			if(extensionActual.equals(String.copyValueOf(getSoportado(i)))){
				res = true;
			}
		}
		if(res == false){
			throw new FormatoNoSoportadoException("Formato no soportado: " + extensionActual);
		}
		return res;
	}
	
	private char[] getSoportado(int index){
		char[] res = new char[extensiones[index].length()];
		
		for(int i = 0; i < res.length; i++){
			res[i] = extensiones[index].charAt(i);	
		}
		
		return res;
	}
	
	private String getExtension(){
		String res = "NULL";
		
		if(!comprobarExistencia(archivo)){
			exit();
		}
		
		try {
			String aComparar = archivo.getCanonicalPath();
			char[] ext;
			aComparar = getExt(aComparar);
			for(int i = 0; i < extensiones.length && res == "NULL"; i++){
				ext = getSoportado(i);
				if(aComparar.equals(String.copyValueOf(ext))){
					res = aComparar;
				}else{
					res = aComparar;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR DE LECTURA: ------------------------> " + res);
			return res;
		}

		return res;
	}
	
	private void exit() {
		System.out.println("Terminado con errores.");
		Runtime.getRuntime().exit(1);
	}

	private String getExt(final String aComparar) {
		String res = "NULL";
		int pos = -1;
		try{	
			if(aComparar == null){
				throw new RuntimeException("Comparando cadena nula");
			}else{
				for(int i = 0; i < aComparar.length(); i++){
					if(aComparar.charAt(i) == '.'){
						pos = i;
					}
				}
				if(pos != -1){
					res = aComparar.substring(pos+1);
					System.out.println("Encontrada extensión: " + res);
				}else{
					System.err.println("Error: en funcion <String getExt> con parametro: " + aComparar + ";\n"
							+ "No se ha encontrado el separador '.' en este caso.");
				}
			}
		}catch(RuntimeException e){
			res = "NULL";
		}
		return res;
	}

	private boolean comprobarExistencia(File file){
		boolean sinFallos = true;
		if(file.exists()){
			if(file.isDirectory()){
				System.out.println("La ruta pertenece a un directorio.");
				integridad = false;
			}else{
				System.out.println("La ruta pertenece a un archivo.");
				integridad = true;
			}
		}else{
			System.err.println("No se encuentra el archivo o ruta especificados.");
			sinFallos = false;
			integridad = false;
		}		
		return sinFallos;
	}

	private boolean comprobarScanner() throws DispositivoInactivoException{
		
		if(escribiendo){
			throw new DispositivoInactivoException("Scanner no se activó porque ya se está escribiendo un archivo.");
		}
		
		if(!leyendo){
			throw new DispositivoInactivoException("Scanner no abierto");
		}
		return leyendo;
	}
	
	private boolean comprobarImpresor() throws DispositivoInactivoException{
		if(leyendo){
			throw new DispositivoInactivoException("Impresor no se activó porque ya se está leyendo un archivo.");
		}
		
		if(leyendo){
			throw new DispositivoInactivoException("Impresor no abierto");
		}
		return escribiendo;
	}
	
	public void explorarRuta(){
		if(integridad == false){
			setRuta("/");
		}
		explorador = new JFileChooser("ruta");
		explorador.setFileFilter(new FileNameExtensionFilter("text-only", "txt"));
		explorador.showOpenDialog(explorador);
		archivo = explorador.getSelectedFile();
		ruta = archivo.getAbsolutePath();
		comprobarExistencia(archivo);
		
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(final String ruta) {
		this.ruta = ruta;
	}
		
	public boolean hasNext() throws DispositivoInactivoException{
		return comprobarScanner() && sc.hasNext();
	}
	
	public String next() throws DispositivoInactivoException{
		comprobarScanner();		
		return sc.next();
	}
	
	public String nextLine(){
		comprobarScanner();
		return sc.nextLine();
	}
	
	public void almacenarLinea() throws DispositivoInactivoException{
		storeLine();
	}
	
	public void storeLine() throws DispositivoInactivoException{
		comprobarScanner();
		concat.cargar(sc.nextLine());
	}
	
	public void almacenar() throws DispositivoInactivoException{
		store();
	}
	
	public void store() throws DispositivoInactivoException{
		comprobarScanner();
		concat.cargar(sc.next());
	}
	
	public int nextInt(){
		comprobarScanner();
		return sc.nextInt();
	}
	
	public void abrirScanner(){
		if(integridad){
			try {
				sc = new Scanner(archivo);
				leyendo = true;
			} catch (FileNotFoundException e) {
				System.err.println("Error logico, integridad correcta, pero el dispositivo falló insesperadamente.");
				e.printStackTrace();
			}
		}else{
			leyendo = false;
			System.err.println("No se ha podido iniciar la lectura debido a que el dispositivo es vulnerable.");
		}
	}
	
	public void cerrarScanner(){
		if(sc != null){
			sc.close();
			leyendo = false;
		}else{
			System.out.println("El scanner no se ha podido cerrar porque no está abierto");
		}
	}
	
	public boolean abrirImpresor(){
		boolean res = !leyendo && integridad;
		if(res){
			try {
				pw = new PrintWriter(archivo);
				escribiendo = true;
			} catch (FileNotFoundException e) {
				System.err.println("Error logico, integridad correcta, pero el dispositivo falló insesperadamente.");
				e.printStackTrace();
			}

		}else{
			System.err.println("No se pudo abrir el impresor (printWriter) porque el dispositivo es vulnerable.");
		}
		return res;
	}
	
	public void cerrarImpresor() {
		if(pw != null){
			pw.close();
			escribiendo = false;
		}else{
			System.err.println("No se puede cerrar el impresor porque no se encuentra abierto.");
		}
	}
	
	public void alternar(final String nuevaRuta){
		try{
			if(comprobarExistencia(new File(nuevaRuta))){
				archivo = new File(nuevaRuta);
			}else{
				throw new FormatoNoSoportadoException("Se ha dado una ruta dinámica incorrecta.");
			}
			
			if(escribiendo){
				cerrarImpresor();
				abrirScanner();
			}
			
			if(leyendo){
				cerrarScanner();
				abrirImpresor();
			}
		}catch(FormatoNoSoportadoException e){
			e.printStackTrace();
			Notify.error("Seleccione manualmente el archivo con el que quería trabajar manualmente o el programa se detendrá.");
		}
	}

	public void println(final String msg){
		print(msg + "\n");
	}
	
	public void print(final String msg){
		if(integridad && !leyendo && comprobarImpresor()){
			pw.print(msg);
		}else{
			System.err.println("Dispositivo vulnerable. La escritura no se pudo llevar a cabo.");
		}
	}
	
	public void dump(){
		volcar();
	}
	
	public void volcar(){
		while(concat.size() > 0){
			pw.println(concat.descargar());
		}
	}
	
	public void nuevaLinea(final String str){
		addLine(str);
	}
	
	public void addLine(final String str){
		concat.cargar(str);
	}
	
	public void adosar(final String str){
		append(str);
	}
	
	public void append(final String str){
		concat.append(str);
	}
	
	public void printBuffer(){
		concat.printContetnt();
	}
	
	public void endl(){
		concat.cargar("");
	}
	
	//Cierra cualquier método de entrada salida que pueda estar abierto.
	public void close(){
		cerrarScanner();
		cerrarImpresor();
	}
	
}










