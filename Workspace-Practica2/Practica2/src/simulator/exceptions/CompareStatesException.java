package simulator.exceptions;

public class CompareStatesException extends Exception{
	//Excepcion, salta cuando dos ficheros no coincide, ya sea por masa o por epsilon
	
	private static final long serialVersionUID = 1L;

	public CompareStatesException() { 
		super(); 
	}
	public CompareStatesException(String message){
		super(message); 
	}
	public CompareStatesException(String message, Throwable cause){
		super(message, cause);
	}
	public CompareStatesException(Throwable cause){ super(cause); }
	
	public CompareStatesException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace){
		super(message, cause, enableSuppression, writeableStackTrace);
	}
}