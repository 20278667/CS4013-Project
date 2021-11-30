
public class Room {

	String hotelType;
	String roomType;
	int numberOfRooms;
	int minOccupancy;
	int maxOccupancy;
	int[] weekRates;

	public Room(String HotelType, String RoomType, int NumberOfRooms, int MinOccupancy, int MaxOccupancy, int[] rates) {
		hotelType = HotelType;
		roomType = RoomType;
		numberOfRooms = NumberOfRooms;
		minOccupancy = MinOccupancy;
		maxOccupancy = MaxOccupancy;
		weekRates = rates;
	}
	
	public String toString() {
		return roomType;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		else if (o.getClass() != this.getClass()) return false;
		else if (((Room)o).roomType == this.roomType && ((Room)o).hotelType == this.hotelType) return true;
		else return false;
	}
}