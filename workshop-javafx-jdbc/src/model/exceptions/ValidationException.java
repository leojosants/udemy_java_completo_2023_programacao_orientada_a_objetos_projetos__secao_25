/*-------------------- packages --------------------*/
package model.exceptions;

/*-------------------- imports --------------------*/
import java.util.HashMap;
import java.util.Map;

/*-------------------- class ValidationException --------------------*/
public class ValidationException extends RuntimeException {

	/*-------------------- attributes --------------------*/
	private static final long serialVersionUID = 1L;
	private Map<String, String> erros = instanceateMapHashMap();

	/*-------------------- constructors --------------------*/
	public ValidationException(String message) {
		super(message);
	}

	/*-------------------- getters and setters --------------------*/
	public Map<String, String> getErros() {
		return this.erros;
	}

	/*-------------------- methods --------------------*/
	public void addError(String fieldName, String errorMessage) {
		this.erros.put(fieldName, errorMessage);
	}
	
	private Map<String, String> instanceateMapHashMap() {
		return new HashMap<>();
	}

}
