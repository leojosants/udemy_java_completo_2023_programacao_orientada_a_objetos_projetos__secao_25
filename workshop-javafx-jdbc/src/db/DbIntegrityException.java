/*-------------------- packages --------------------*/
package db;

/*-------------------- class DbIntegrityException --------------------*/
public class DbIntegrityException extends RuntimeException {

	/*-------------------- attributes --------------------*/
	private static final long serialVersionUID = 1L;

	/*-------------------- constructors --------------------*/
	public DbIntegrityException(String msg) {
		super(msg);
	}
}