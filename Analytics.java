import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.*;  
/**
 * Write a description of class analytics here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Analytics {
  public static void main(String args[]){
    LocalDate dateFrom = LocalDate.of(2001, Month.FEBRUARY, 6);
    LocalDate dateTo = LocalDate.of(2021, Month.NOVEMBER, 30);
 
    Period intervalPeriod = Period.between(dateFrom, dateTo);
    Period resverationPeriod = Period.between(reserveFrom, reserveTo);
    System.out.println("Difference of days: " + intervalPeriod.getDays());
    System.out.println("Difference of months: " + intervalPeriod.getMonths());
    System.out.println("Difference of years: " + intervalPeriod.getYears());
    
    System.out.println("Difference of total days: " + (intervalPeriod.getDays() +
    (intervalPeriod.getMonths()*30.42) + (intervalPeriod.getYears()*365.25)));
	public LocalDate CheckInDate;
	public LocalDate CheckOutDate;
	public Hotel hotel;
	public Room room;
	
	HashMap<String_Name, LocalDate_checkIn, LocalDate_checkOut, String_Type, 
	Hotel_h, Room_r, int_Number,boolean_Cancelled> map=new HashMap
	<String_Name, LocalDate_checkIn, LocalDate_checkOut, String_Type, 
	Hotel_h, Room_r, int_Number,boolean_Cancelled>();//Creating HashMap    
   map.put("Paul White",2021-12-22,2030-10-15,5-star,Deluxe Single,false);  //Put elements in Map  
   map.put("Edgar Wright",2001-02-06,2011-05-16,3-star,Classic Double,true);    
   map.put("Kevin Finnan",2010-01-01,2012-08-29,4-star,Executive Twin,false);   
   map.put("Todd Ryan",2004-12-10,2015-03-02,5-star,Deluxe Family,true);  
         
   for(Map.Entry m: map.entrySet()){    
    System.out.println(m.getKey()+" "+m.getValue());    
   }
	
      for (int i = 0; i < 5; i++) {
       if (rerserveFrom.isAfter(reserveTo)) return false;
    else{ if (reserveFrom.isAfter(CheckInDate)) return false;
    else{ if (reserveTo.isAfter(CheckOutDate)) return false;
    else 
    return true;
      }
     }
    }
	public Reservation(LocalDate checkIn, LocalDate checkOut, Hotel h, Room r) {
		CheckInDate = checkIn;
		CheckOutDate = checkOut;
		hotel = h;
		room = r;
   } 
} 