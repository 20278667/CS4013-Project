
public class Customer {

	public String name;
	public String userName;
	public int password;
	private boolean isAdmin;
	
	/***
	 * Creates a new customer object with a given name, username and hashed password.
	 * @param Name is the customer name.
	 * @param UserName is the account's username.
	 * @param passwordHashed is the hashed password of the customer.
	 */
	public Customer(String Name, String UserName, int passwordHashed) {
		name = Name;
		userName = UserName;
		password = passwordHashed;
	}
	
	/***
	 * Creates a new customer object with a given name and an unhashed password.
	 * @param Name is the customer name.
	 * @param UserName is the account's username.
	 * @param passwordUnhashed is the plantext password of the customer.
	 */
	public Customer(String Name, String UserName, String passwordUnhashed) {
		this(Name, UserName, passwordUnhashed.hashCode());
	}
	
	/***
	 * Creates a new customer object with a given name, hashed password, and administrator privileges info.
	 * @param Name is the customer name.
	 * @param UserName is the account's username.
	 * @param passwordHashed is the hashed password of the customer.
	 * @param IsAdmin states whether or not the user should have administrator privileges.
	 */
	public Customer(String Name, String UserName, int passwordHashed, boolean IsAdmin) {
		this(Name, UserName, passwordHashed);
		if (IsAdmin) isAdmin = true;
	}
	
	/***
	 * Is a getter for isAdmin. A getter without a setter is used to limit changes to administrator access.
	 * @return True or false whether the account has administator privileges.
	 */
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	/***
	 * Attempts to log in to a given customer account.
	 * @param Name is the name of the customer.
	 * @param attemptPassword is the password the user is trying to enter.
	 * @return True or false whether the attempt is successful or not.
	 */
	public boolean attemptLogin(String UserName, String attemptPassword) {
		if (userName.equals(UserName) && password == attemptPassword.hashCode()) return true;
		else return false;
	}
}
