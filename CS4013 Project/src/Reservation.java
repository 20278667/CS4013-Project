import java.time.LocalDate;

public class Reservation {

	public int number;
	public String name;
	public LocalDate CheckInDate;
	public LocalDate CheckOutDate;
	public String type;
	public Hotel hotel;
	public Room room;
	public boolean cancelled;
	
	public Reservation(String Name, LocalDate checkIn, LocalDate checkOut, String Type, Hotel h, Room r, int Number, boolean Cancelled) {
		name = Name;
		CheckInDate = checkIn;
		CheckOutDate = checkOut;
		hotel = h;
		room = r;
		if (Type.equals("AP")) type = Type;
		else type = "S";
		number = Number;
		cancelled = Cancelled;
	}
	
	public boolean isValid() {
		if (CheckInDate.isAfter(CheckOutDate)) return false;
		else return true;
	}
	
	public String toString() {
		return ((type == "S") ? "Standard" : "Advance purchase") + " reservation, in " + hotel.toString() + ", room " + room.roomType;
	}
	
	public String fullDetails() {
		return ((type == "S") ? "Standard" : "Advance Purchase") + " Reservation"
		+ "\nID: " + number
		+ "\nHotel: " + hotel.toString()
		+ "\nRoom: " + room.roomType
		+ "\nCheck In Date: " + CheckInDate
		+ "\nCheck Out Date: " + CheckOutDate
		+ (cancelled ? "\nCancelled" : "");
	}
}
