import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileSystem {
	public static final String sep = ",";
	public static final String reservations = "src/reservations.csv";
	
	public static ArrayList<Reservation> readReservations() {
		return null;
	}
	
	public static void writeReservation(Reservation R) throws IOException {
		File f = new File(reservations);
		FileWriter fw = new FileWriter(f, true);
		fw.append("\n" + R.name + sep + R.CheckInDate + sep + R.type + sep + R.hotel.name + sep + R.room.roomType + sep + R.cancelled);
		fw.close();
	}
}
