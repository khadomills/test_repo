/**
 * Abstract class user to store values for different types of users
 * @author Jake
 *
 */
public class User {

	protected String userID;
	
	protected String username;
	
	protected String password;
	
	protected String firstName;
	
	protected String lastName;
	
	protected int addressID;
	
	public User(String userID, String username, String password, String fName, String lName, int addressID) {
		
		this.userID = userID;
		this.username = username;
		this.password = password;
		firstName = fName;
		lastName = lName;
		this.addressID = addressID;
	
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
	
		return userID;
	
	}

	/**
	 * @param userID the userID to set
	 */
	private void setUserID(String userID) {
	
		this.userID = userID;
	
	}

	/**
	 * @return the username
	 */
	private String getUsername() {
	
		return username;
	
	}

	/**
	 * @param username the username to set
	 */
	private void setUsername(String username) {
	
		this.username = username;
	
	}

	/**
	 * @return the password
	 */
	private String getPassword() {
	
		return password;
	
	}

	/**
	 * @param password the password to set
	 */
	private void setPassword(String password) {
	
		this.password = password;
	
	}

	/**
	 * @return the firstName
	 */
	private String getFirstName() {
		
		return firstName;
	
	}

	/**
	 * @param firstName the firstName to set
	 */
	private void setFirstName(String firstName) {
	
		this.firstName = firstName;
	
	}

	/**
	 * @return the lastName
	 */
	private String getLastName() {
		
		return lastName;
	
	}

	/**
	 * @param lastName the lastName to set
	 */
	private void setLastName(String lastName) {
	
		this.lastName = lastName;
	
	}

	/**
	 * @return the addressID
	 */
	private int getAddressID() {
	
		return addressID;
	
	}

	/**
	 * @param addressID the addressID to set
	 */
	private void setAddressID(int addressID) {
		
		this.addressID = addressID;
	
	}
	
	public String toString() {
		
		return firstName;
		
	}	
	
}
