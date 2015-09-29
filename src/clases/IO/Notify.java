package clases.IO;

import javax.swing.JOptionPane;

public class Notify {

	
	/* @author SmokedUnicorn
	 * 	
	 * 	Quickly prompts a message or an option message to the user to notify what is going on.
	 * 	
	 */
	
	private static final String WARN = "Warning";
	private static final String ATTENTION = "Attention";
	private static final String ERROR = "Error";
	private static final String FATAL = "Fatal Error";
	private static final String SUCCESS = "Success";
	private static final String USER_IN = "User input needed";

	
	public static void error(final String msg){
		JOptionPane.showMessageDialog(null, msg, ERROR, JOptionPane.ERROR_MESSAGE);
	}
	
	public static int optionError(final String msg){
		return JOptionPane.showConfirmDialog(null, msg, WARN , JOptionPane.YES_NO_OPTION,  JOptionPane.ERROR_MESSAGE);
	}
	
	public static void info(final String msg){
		JOptionPane.showMessageDialog(null, msg, ATTENTION, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int optionInput(final String msg){
		return JOptionPane.showConfirmDialog(null, msg, USER_IN, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
	
	public static void fatalError(final String msg){
		JOptionPane.showMessageDialog(null, msg, FATAL, JOptionPane.ERROR_MESSAGE);
	}	
	
	public static void operationSucces(final String msg){
		JOptionPane.showMessageDialog(null, msg, SUCCESS, JOptionPane.INFORMATION_MESSAGE);
	}
	
}
