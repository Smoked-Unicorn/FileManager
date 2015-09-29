package clases.IO;

public class Concatenador {
	private int size;
	private String[] buffer;
	private static final int INICIO = 10;
	private static final int INCREMENTO = 5;
	
	public Concatenador(){
		size = 0;
		buffer = new String[INICIO];
	}
	
	public boolean cabe(){
		return size < buffer.length;
	}
	
	public void agrandar(){
		String[] aux = new String[buffer.length + INCREMENTO];
		
		for(int i = 0; i < size; i++){
			aux[i] = buffer[i];
		}
		
		buffer = aux;
	}
	
	public void cargar(final String str){
		if(!cabe()){
			agrandar();
		}
		
		buffer[size] = str;
		size++;
	}
	
	public String descargar(){
		String str = null;
		if(size > 0){
			
			str = buffer[0];
			size--;
			
			for(int i = 0; i < size; i++){
				buffer[i] = buffer[i+1];
			}
			
		}else{
			System.err.println("Error, buffer vacío.");
		}
		return str;
	}
	
	public int size(){
		return size;
	}
	
	public void append(final String str){
		buffer[size-1] = new String(buffer[size-1] + str);
	}

	public void printContetnt(){
		for(int i = 0; i < size; i++){
			System.out.println(buffer[i]);
		}
	}
}


