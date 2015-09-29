package clases.IO;

public class DispositivoInactivoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DispositivoInactivoException(final String msg){
		super(msg);
	}
	
	public DispositivoInactivoException(){
		super();
	}
}
