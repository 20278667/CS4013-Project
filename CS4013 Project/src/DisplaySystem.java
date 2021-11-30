import java.util.*;

public class DisplaySystem {

	/***
	 * From a given list and Scanner, accept an input and return the object.
	 * @param <T> is the type of the list.
	 * @param list is the list of objects to be outputted.
	 * @param in is the Scanner to read the next input line.
	 * @param allowBack gives a return functionality to the user in case they wish to exist a process early. This adds a null object to the list of choices.
	 * @return An object from the given list according to user input.
	 */
	public static <T> Object choices(ArrayList<T> list, Scanner in, boolean allowBack) {
		char c = 'A';
		for (Object choice : list) {
			System.out.println(c + ") " + choice.toString());
			c++;
		}
		if (allowBack) {
			System.out.println(c + ") Return.");
		}
		char s = in.nextLine().toUpperCase().charAt(0);
		if (s - 'A' == list.size()) return null;
		else if (s - 'A' >= 0 && s - 'A' <= list.size()) return list.get(s - 'A');
		else {
			boolean correctInput = false;
			while (!correctInput) {
				displayIncorrectInput();
				s = in.nextLine().toUpperCase().charAt(0);
				if (s - 'A' <= list.size() + (allowBack ? 1 : 0) && s - 'A' >= 0) correctInput = true;
			}
			if (s - 'A' == list.size()) return null;
			else return list.get(s - 'A');
		}
	}
	
	/***
	 * Text for incorrect input. Used in DisplaySystem.choices().
	 */
	public static void displayIncorrectInput() {
		System.out.println("Input not recognised. Please try again.");
	}
	
	/***
	 * Outputs a chosen hotel in a user readable format.
	 * @param h is the hotel.
	 */
	public static void displayHotel(Hotel h) {
		System.out.println(h.name + " Hotel chosen.");
	}
	
	/***
	 * Outputs a chosen room in a user readable format.
	 * @param r is the room.
	 */
	public static void displayRoom(Room r) {
		System.out.println(r.roomType + " Room chosen.");
	}
	
	/***
	 * Displays the text to be displayed on start up.
	 */
	public static void displayStartText(String name) {
		System.out.println("Welcome " + ((name != "") ? name + ", " : "") + "to the L4 Hotels Reservation System! Enter 'H' to view available commands.");
	}
	
	/***
	 * Displays the available input commands when the Help command is used.
	 * @param isLoggedIn is whether or not a user is logged in.
	 */
	public static void displayHelpText(boolean isLoggedIn, boolean isAdmin) {
		System.out.println("Please enter the appropiate letter to access our system's features. Note some features are limited to users with accounts.");
		if (isAdmin) {
			System.out.println("L: Login or create an account."
					+ "\nA: View analytics."
					+ "\nD: Delete customer accounts."
					+ "\nR: Reserve a hotel room."
					+ "\nV: View existing reservations."
					+ "\nC: Cancel a reservation."
					+ "\nP: View our cancellation policy."
					+ "\nH: View available commands."
					+ "\nQ: Quit this session.");
		}
		else if (isLoggedIn) {
			System.out.println("L: Login or create an account."
					+ "\nR: Reserve a hotel room."
					+ "\nV: View existing reservations."
					+ "\nC: Cancel a reservation."
					+ "\nP: View our cancellation policy."
					+ "\nH: View available commands."
					+ "\nQ: Quit this session.");
		}
		else {
			System.out.println("L: Login or create an account."
					+ "\nP: View our cancellation policy."
					+ "\nH: View available commands."
					+ "\nQ: Quit this session.");
		}
	}
	
	/***
	 * Displays text when there are no reservations for a user.
	 */
	public static void displayNoReservationsError() {
		System.out.println("There are no reservations available.");
	}
	
	/***
	 * Displays the cancellation policy as given by the specification document.
	 */
	public static void displayCancellationPolicy() {
		System.out.println("Advance purchase reservations are non-refundable but are 5% cheaper. For a standard reservation, the full booking value will be charged if the cancellation is received within 48 hours of check-in or if the booking is a ‘no-show’. A standard reservation is fully refundable if cancelled more than 48 hours before the check-in date.");
	}
	
	/***
	 * Text to be displayed when an inaccessible feature attempts to be accessed.
	 * @param isAdminLocked is true when the system feature is locked behind administrator privileges.
	 */
	public static void displayInaccessibleText(boolean isAdminLocked) {
		if (!isAdminLocked) System.out.println("This system feature is currently inaccessible due to limited account privileges.");
		else System.out.println("This system requires administrator privileges. To access this feature, log into an administrator account.");
	}

	/***
	 * Text to be displayed when a date is entered incorrectly.
	 */
	public static void displayDateError() {
		System.out.println("There was an error with the date entered. Please try again.");
	}

	/***
	 * Yes or no confirmation text.
	 */
	public static void displayConfirmation() {
		System.out.println("Are you sure you wish to continue? Enter 'Y' to confirm or 'N' to cancel.");
	}
	
	/***
	 * Cancellation confirmation text.
	 */
	public static void displayCancellation() {
		System.out.println("The reservation has been successfully cancelled.");
	}
}
