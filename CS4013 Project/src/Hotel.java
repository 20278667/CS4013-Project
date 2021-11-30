import java.util.*;

public class Hotel {
	
	public String name;
	public ArrayList<Room> rooms;
	
	public Hotel (String Name, ArrayList<Room> Rooms) {
		name = Name;
		rooms = Rooms;
	}
	
	public String toString() {
		return name + " Hotel";
	}
	
	@Override
	public boolean equals (Object o) {
		if (o == null) return false;
		else if (o.getClass() != this.getClass()) return false;
		else if (this.name.equals(((Hotel)o).name)) return true;
		else return false;
	}
}
