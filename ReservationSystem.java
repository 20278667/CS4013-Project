import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReservationSystem {

	final LocalDate errorDate = LocalDate.MIN;
	
	ArrayList<Reservation> reservations;
	ArrayList<Hotel> hotels;
	ArrayList<Customer> customers;
	Scanner in;
	
	Customer account = null;
	boolean firstStartUp = true;
	boolean givenInput = true;
	
	/***
	 * Creates new reservation system and sets up available rooms and hotels.
	 */
	public ReservationSystem() throws IOException {
		hotels = FileSystem.readHotels();
		customers = FileSystem.readCustomers();
		reservations = FileSystem.readReservations(hotels);
		in = new Scanner(System.in);
	}
	
	/***
	 * Starts a new reservation system.
	 * @throws IOException from writing new reservations to file.
	 */
	public void start() throws IOException {
		while (true) {
			if (firstStartUp) {
				DisplaySystem.displayStartText("");
				DisplaySystem.displayHelpText(false, false);
				firstStartUp = false;
			}
			else if (givenInput){
				System.out.println();
				DisplaySystem.displayStartText((account == null) ? "" : account.name);
			}
			givenInput = false;
			String input = in.nextLine().toUpperCase();
			
			switch (input) {
				case "L":
					givenInput = true;
					Login();
					break;
				case "A":
					givenInput = true;
					if (account == null || !account.getIsAdmin()) DisplaySystem.displayInaccessibleText(true);
					else //TODO - analytics
					break;
				case "D":
					givenInput = true;
					if (account == null || !account.getIsAdmin()) DisplaySystem.displayInaccessibleText(true);
					else //TODO - delete user
					break;
				case "R":
					givenInput = true;
					if (account == null) DisplaySystem.displayInaccessibleText(false);
					else startReservation();
					break;
				case "V":
					givenInput = true;
					if (account == null) DisplaySystem.displayInaccessibleText(false);
					else viewReservations();
					break;
				case "C":
					givenInput = true;
					if (account == null) DisplaySystem.displayInaccessibleText(false);
					else cancelReservation();
					break;
				case "P":
					givenInput = true;
					DisplaySystem.displayCancellationPolicy();
					break;
				case "H":
					givenInput = true;
					DisplaySystem.displayHelpText((account == null) ? false : true, (account == null) ? false : account.getIsAdmin());
					break;
				case "Q":
					givenInput = true;
					System.out.println("Thank you for using the L4 Hotels Reservation System!");
					in.close();
					return;
			}
		}
	}
	
	/***
	 * The process for cancelling a reservation.
	 * @throws IOException if reservations.csv cannot be found upon successful cancellation.
	 */
	public void cancelReservation() throws IOException {
		ArrayList<Reservation> userReservations = getReservations();
		if (userReservations == null) return;
		System.out.println("Which reservation would you like to cancel?");
		Reservation R = (Reservation) DisplaySystem.choices(userReservations, in, true);
		if (R == null) { return; }
		if (!account.getIsAdmin()) {
			if (R.type.equals("S")) { 
				if (ChronoUnit.DAYS.between(LocalDate.now(), R.CheckInDate) <= 2) {
					System.out.println("You will still be charged fully for this reservation if you cancel it now, in accordance with our cancellation policy.");
					DisplaySystem.displayConfirmation();
					char agreement = in.nextLine().toUpperCase().charAt(0);
					if (agreement == 'Y') {
						cancel(R);
					}
				}
				else {
					System.out.println("You will receive a full refund if you cancel this reservation now, in accordance with our cancellation policy.");
					DisplaySystem.displayConfirmation();
					char agreement = in.nextLine().toUpperCase().charAt(0);
					if (agreement == 'Y') {
						cancel(R);
					}
				}
			}
			else System.out.println("We are unable to cancel this reservation in accordance with our cancellation policy.");
		}
		else {
			DisplaySystem.displayConfirmation();
			char agreement = in.nextLine().toUpperCase().charAt(0);
			if (agreement == 'Y') {
				cancel(R);
			}
		}
	}
	
	
	/***
	 * This is the process that flags a reservation R as being cancelled.
	 * @throws IOException if reservations.csv does not exist.
	 */
	public void cancel(Reservation R) throws IOException {
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).equals(R)) {
				reservations.get(i).cancelled = true;
				FileSystem.cancelReservation(R);
				reservations.remove(i);
				DisplaySystem.displayCancellation();
				return;
			}
		}
	}
	
	/***
	 * Process that displays reservation information.
	 */
	public void viewReservations() {
		ArrayList<Reservation> userReservations = getReservations();
		if (userReservations == null) return;
		System.out.println("Please select a reservation to view its details.");
		Reservation R = (Reservation) DisplaySystem.choices(userReservations, in, true);
		if (R == null) return;
		else {
			System.out.println(R.fullDetails());
		}
	}
	
	
	/***
	 * Process that returns a list of reservations by user, if any are available. Called in viewReservations() and cancelReservations().
	 * @return List of reservations by the current logged in user, null if not applicable.
	 * */
	public ArrayList<Reservation> getReservations() {
		if (reservations.size() == 0) { DisplaySystem.displayNoReservationsError(); return null; }
		ArrayList<Reservation> userReservations = new ArrayList<Reservation>();
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).name.equals(account.name) || account.getIsAdmin()) {
				userReservations.add(reservations.get(i));
			}
		}
		if (userReservations.size() == 0) { DisplaySystem.displayNoReservationsError(); return null; }
		else return userReservations;
	}
	
	
	/***
	 * The process for creating a new reservation.
	 * @throws IOException from FileSystem writing upon successful reservation creation.
	 */
	public void startReservation() throws IOException {
		System.out.println("Please select a hotel to reserve a room in.");
		Hotel h = (Hotel) DisplaySystem.choices(hotels, in, true);
		if (h == null) return;
		else {
			DisplaySystem.displayHotel(h);
			System.out.println("Please select a room type to reserve.");
			Room r = (Room) DisplaySystem.choices(h.rooms, in, true);
			if (r == null) return;
			else {
				DisplaySystem.displayRoom(r);
				System.out.println("Please enter your preferred check in date.");
				LocalDate checkInDate = inputDate();
				if (checkInDate != errorDate) {
					System.out.println("Please enter your preferred check out date.");
					LocalDate checkOutDate = inputDate();
					if (checkOutDate != errorDate && checkOutDate.isAfter(checkInDate)) {
						System.out.println("Would you like to make a standard reservation (S) or an advance purchase (AP) reservation? See our cancellation policy for details.");
						String purchaseType = in.nextLine().toUpperCase();
						Reservation R = new Reservation(account.name, checkInDate, checkOutDate, purchaseType, h, r, reservations.size() + 1, false);
						if(isReserved(R)) {
							System.out.println("Unfortunately, this room is booked out during this time. Please try again at a different time.");
						}
						else {
							System.out.println("There is room for your reservation. Your reservation amounts to €" + reservationCost(R) + " for your stay of " + ChronoUnit.DAYS.between(checkInDate, checkOutDate) + " days.");
							DisplaySystem.displayConfirmation();
							char agreement = in.nextLine().toUpperCase().charAt(0);
							if (agreement == 'Y') {
								reservations.add(R);
								FileSystem.writeReservation(R);
								System.out.println("Thank you for making a reservation. Your reservation number is " + R.number + ". We hope you enjoy your stay with L4 Hotels.");
							}
						}
					}
					else {
						DisplaySystem.displayDateError();
					}
				}
				else {
					DisplaySystem.displayDateError();
				}
			}
		}
	}
	
	/***
	 * Calculates the total cost of staying for a reservation.
	 * @return Cost.
	 */
	public int reservationCost(Reservation r) {
		int day = r.CheckInDate.getDayOfWeek().getValue() - 1; //value in range [1, 7] adjusted to value in range[0, 6].
		long daysBetween = ChronoUnit.DAYS.between(r.CheckInDate, r.CheckOutDate);
		int cost = 0;
		for (int i = 0; i < daysBetween; i++) {
			cost += r.room.weekRates[(day + i) % 7];
		}
		if (r.type.equals("AP")) cost *= 0.95;
		return cost;
	}
	
	/***
	 * Handles the processing of date inputs.
	 * @return the date asked for, given the input was successful. Returns Date(0, 0, 0) on failure.
	 */
	public LocalDate inputDate() throws DateTimeParseException {
		System.out.println("Please enter the date in the format DD/MM/YYYY.");
		String dateInfo = in.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = errorDate;
		try {
			date = LocalDate.parse(dateInfo, formatter);
		}
		catch (DateTimeParseException e) {
			return errorDate;
		}
		if (date.isBefore(LocalDate.now())) {
			return errorDate;
		}
		return date;
	}
	
	
	/***
	 * Determines if a new reservation can fit in the existing schedule.
	 * @param r is the new reservation.
	 * @return True if the space is already reserved, false if it is free.
	 */
	public boolean isReserved(Reservation r) {
		if (reservations.size() == 0) return false;
		int count = 1; //count of concurrent room usages.
		for (int i = 0; i < reservations.size(); i++) {
			if (r.room.roomType == reservations.get(i).room.roomType) {
				if ((r.CheckInDate.isAfter(reservations.get(i).CheckInDate) || r.CheckInDate.isEqual(reservations.get(i).CheckInDate))
						&& r.CheckInDate.isBefore(reservations.get(i).CheckOutDate)) {
					count++;
				}
				else if ((r.CheckOutDate.isAfter(reservations.get(i).CheckInDate)
						&& (r.CheckOutDate.isBefore(reservations.get(i).CheckOutDate) || r.CheckOutDate.isEqual(reservations.get(i).CheckOutDate)))) {
					count++;
				}
			}
		}
		return (count > r.room.numberOfRooms) ? true : false;
	}
	
	
	/***
	 * The process for logging in to the system or creating a new customer account.
	 * @throws IOException when writing new customer account.
	 */
	public void Login() throws IOException {
		System.out.println("To access certain system features, you must be logged in. To either create an account or login, enter the account's user name.");
		String userName = in.nextLine().trim();
		System.out.println("Now enter your password. Please remember this, because there is no account recovery policy.");
		String pass = in.nextLine().trim();
		boolean accountFound = false;
		if (customers.size() > 0) {
			for (int i = 0; i < customers.size(); i++) {
				if (customers.get(i).userName.equals(userName)) {
					accountFound = true;
					if (customers.get(i).attemptLogin(userName, pass)) {
						account = customers.get(i);
						System.out.println("You are now logged in as " + account.name + ".");
						return;
					}
					break;
				}
			}
		}
		if (!accountFound) {
			System.out.println("Creating new account. Please enter your name.");
			String name = in.nextLine();
			account = new Customer(name, userName, pass);
			customers.add(account);
			FileSystem.writeCustomer(account);
		}
		else {
			System.out.println("That account already exists but the given password is wrong. Please try again.");
		}
	}
}
