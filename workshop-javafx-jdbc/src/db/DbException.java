/*-------------------- packages --------------------*/
package db;

/*-------------------- class DbException --------------------*/
public class DbException extends RuntimeException {
	
	/*-------------------- attributes --------------------*/
	private static final long serialVersionUID = 1L;

	/*-------------------- constructors --------------------*/
	public DbException(String msg) {
		super(msg);
	}
}