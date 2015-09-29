package clases.IO;

public class FormatoNoSoportadoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FormatoNoSoportadoException(final String msg){
		super(msg);
	}
	
	public FormatoNoSoportadoException(){
		super();
	}
}
