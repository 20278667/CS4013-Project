import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Analytics {

	public static int income(LocalDate checkfromDate, LocalDate checktoDate, Hotel hotel, ArrayList<Reservation> reservations) {
		int totalIncome = 0;
        ArrayList<Reservation> hotelReservations = dateChecker(checkfromDate, checktoDate, hotel, reservations);
        for (int i = 0; i < hotelReservations.size(); i++) {
        	totalIncome += reservationCost(hotelReservations.get(i));
        }
        return totalIncome;
    }
	
	public static int occupancy(LocalDate checkfromDate, LocalDate checktoDate, Hotel hotel, ArrayList<Reservation> reservations) {
		int totaloccupancy = 0;
        ArrayList<Reservation> hotelReservations = dateChecker(checkfromDate, checktoDate, hotel, reservations);
        for (int i = 0; i < hotelReservations.size(); i++) {
        	totaloccupancy += (hotelReservations.get(i).room.maxOccupancy + hotelReservations.get(i).room.minOccupancy)/2;
        }
        return totaloccupancy;
    }

	private static ArrayList<Reservation> dateChecker(LocalDate checkfromDate, LocalDate checktoDate, Hotel hotel, ArrayList<Reservation> reservations) {
		ArrayList<Reservation> output = new ArrayList<Reservation>();
		for (int i = 0; i < reservations.size(); i++) {
			if (reservations.get(i).hotel.equals(hotel)) {
				if ((checkfromDate.isBefore(reservations.get(i).CheckInDate) || checkfromDate.isEqual(reservations.get(i).CheckInDate))
						&& (checktoDate.isAfter(reservations.get(i).CheckOutDate) || checktoDate.isEqual(reservations.get(i).CheckOutDate))) {
					output.add(reservations.get(i));
				}
			}
		}
		return output;
	}
	
	/***
	 * Calculates the total cost of staying for a reservation. Identical to ReservationSystem.reservationCost().
	 * @return Cost.
	 */
	public static int reservationCost(Reservation r) {
		int day = r.CheckInDate.getDayOfWeek().getValue() - 1; //value in range [1, 7] adjusted to value in range[0, 6].
		long daysBetween = ChronoUnit.DAYS.between(r.CheckInDate, r.CheckOutDate);
		int cost = 0;
		for (int i = 0; i < daysBetween; i++) {
			cost += r.room.weekRates[(day + i) % 7];
		}
		if (r.type.equals("AP")) cost *= 0.95;
		return cost;
	}

}