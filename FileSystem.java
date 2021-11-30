import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {
	public static final String sep = ",";
	public static final String reservations = "src/reservations.csv";
	public static final String hotels = "src/l4Hotels.csv";
	public static final String customers = "src/customers.csv";
	
	/***
	 * Reads list of reservations from file.
	 * @param hotels is a list of hotels read from file. This is used to reference the reservation's hotel and room.
	 * @return List of existing reservations.
	 * @throws IOException if reservations not found.
	 */
	public static ArrayList<Reservation> readReservations(ArrayList<Hotel> hotels) throws IOException {
		File f = new File(reservations);
		Scanner reader = new Scanner(f);
		ArrayList<String> lines = new ArrayList<String>();
		while (reader.hasNextLine()) lines.add(reader.nextLine());
		ArrayList<Reservation> Reservations = new ArrayList<Reservation>();
		//staring at line 1, all lines should represent reservations
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		for (int i = 1; i < lines.size(); i++) {
			String[] clauses = lines.get(i).split(sep);
			int hotelIndex = 0;
			int roomIndex = 0;
			for (int j = 0; j < hotels.size(); j++) {
				if (hotels.get(j).name.equals(clauses[4])) {
					hotelIndex = j;
					for (int k = 0; k < hotels.get(j).rooms.size(); k++) {
						if (hotels.get(j).rooms.get(k).roomType.equals(clauses[5])) {
							roomIndex = k;
							break;
						}
					}
					break;
				}
			}
			Reservations.add(new Reservation(clauses[0], LocalDate.parse(clauses[1]),
					LocalDate.parse(clauses[2]), clauses[3], hotels.get(hotelIndex),
					hotels.get(hotelIndex).rooms.get(roomIndex), i, (clauses[6].equals("false") ? false : true)));
		}
		reader.close();
		return Reservations;
	}
	
	/***
	 * Finds an existing reservation and updates its status to cancelled.
	 * @param R is the cancelled reservation.
	 * @throws IOException if reservations not found.
	 */
	public static void cancelReservation(Reservation R) throws IOException {
		File f = new File(reservations);
		Scanner s = new Scanner(f);
		String newText = s.nextLine();
		String[] next;
		int line = 0;
		while (s.hasNextLine()) {
			line++;
			if (line == R.number) {
				next = s.nextLine().split(sep);
				next[next.length - 1] = "true";
				newText += "\n";
				for (int i = 0; i < next.length; i++) {
					newText += next[i] + (i + 1 == next.length ? "" : ",");
				}
				break;
			}
			newText += "\n" + s.nextLine();
		}
		while (s.hasNextLine()) {
			newText += "\n" + s.nextLine();
		}
		FileWriter fw = new FileWriter(f);
		s.close();
		fw.write(newText);
		fw.close();
	}
	
	/***
	 * Appends a new reservation to file.
	 * @param R is the new reservation.
	 * @throws IOException if reservations not found.
	 */
	public static void writeReservation(Reservation R) throws IOException {
		File f = new File(reservations);
		FileWriter fw = new FileWriter(f, true);
		fw.append("\n" + R.name + sep + R.CheckInDate + sep + R.CheckOutDate + sep + R.type + sep + R.hotel.name + sep + R.room.roomType + sep + R.cancelled);
		fw.close();
	}
	
	
	/***
	 * Reads l4Hotels.csv and converts each line to a new hotel.
	 * @return arraylist of hotels.
	 * @throws IOException if file not found.
	 * @supressWarnings is used to supress the warning of the casting of Rooms.clone() to type ArrayList<Room>.
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Hotel> readHotels () throws IOException {
		File f = new File(hotels);
		Scanner reader = new Scanner(f);
		ArrayList<String> lines = new ArrayList<String>();
		while (reader.hasNextLine()) lines.add(reader.nextLine());
		//hotelType declared separately since it holds over from prior lines
		String HotelType = "";
		ArrayList<Room> Rooms = new ArrayList<Room>();
		ArrayList<Hotel> Hotels = new ArrayList<Hotel>();
		for (int i = 0; i < lines.size(); i++) {
			//if a line doesn't contain any digit it doesn't represent a hotel room => only match lines that include a digit
			if (lines.get(i).matches(".*\\d")) {
				String[] clauses = lines.get(i).split(sep);
				//save hotel data and get information for the next hotel
				if (clauses[0].matches(".*\\S.*")) {
					if (HotelType != "") Hotels.add(new Hotel(HotelType, (ArrayList<Room>) Rooms.clone()));
					Rooms.clear();
					HotelType = clauses[0];
				}
				int[] rates = new int[7];
				for (int j = 0; j < rates.length; j++) {
					rates[j] = Integer.parseInt(clauses[5 + j]);
				}
				Rooms.add(new Room(HotelType, clauses[1], Integer.parseInt(clauses[2]), Integer.parseInt(clauses[3]), Integer.parseInt(clauses[4]), rates));
			}
			if (i + 1 == lines.size()) Hotels.add(new Hotel(HotelType, Rooms));
		}
		reader.close();
		return Hotels;
	}
	
	/***
	 * Reads customers from file and returns a list of them.
	 * @return List of customers.
	 * @throws IOException if customers not found.
	 */
	public static ArrayList<Customer> readCustomers() throws IOException {
		File f = new File(customers);
		Scanner reader = new Scanner(f);
		ArrayList<String> lines = new ArrayList<String>();
		while (reader.hasNextLine()) lines.add(reader.nextLine());
		ArrayList<Customer> Customers = new ArrayList<Customer>();
		//staring at line 1, all lines should represent customers
		for (int i = 1; i < lines.size(); i++) {
			String[] clauses = lines.get(i).split(sep);
			Customers.add(new Customer(clauses[0], clauses[1], Integer.parseInt(clauses[2]), (clauses[3].equals("true") ? true : false)));
		}
		reader.close();
		return Customers;
	}
	

	public static void writeCustomer(Customer c) throws IOException {
		File f = new File(customers);
		FileWriter fw = new FileWriter(f, true);
		fw.append("\n" + c.name + sep + c.userName + sep + c.password + sep + c.getIsAdmin());
		fw.close();
	}
}
