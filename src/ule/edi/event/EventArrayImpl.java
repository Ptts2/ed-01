package ule.edi.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ule.edi.model.*;
import ule.edi.model.Configuration.Type;


public class EventArrayImpl implements Event {
	
	private String name;
	private Date eventDate;
	private int nSeats;
	
	private Double price;    // precio de entradas 
	private Byte discountAdvanceSale;   // descuento en venta anticipada (0..100)
   	
	private Seat[] seats;
		
	
	
   public EventArrayImpl(String name, Date date, int nSeats){
	   //TODO 
	   // utiliza los precios por defecto: DEFAULT_PRICE y DEFAULT_DISCOUNT definidos en Configuration.java
	   this.name=name;
	   this.eventDate=date;
	   
	   price=Configuration.DEFAULT_PRICE;
	   discountAdvanceSale=Configuration.DEFAULT_DISCOUNT;
	   
	   
	   // Debe crear los arrays de butacas gold y silver

	   seats=new Seat[nSeats];
	   
   }
   
   
   public EventArrayImpl(String name, Date date, int nSeats, Double price, Byte discount){
	   //TODO 
	   // Debe crear los arrays de butacas gold y silver
	   this.name=name;
	   this.eventDate=date;
	   
	   this.price= price;
	   this.discountAdvanceSale=discount;   
	 
	   this.seats=new Seat[nSeats];   
   }


@Override
public String getName() {
	
	return this.name;
}


@Override
public Date getDateEvent() {
	
	return this.eventDate;
}


@Override
public Double getPrice() {
	
	return this.price;
}


@Override
public Byte getDiscountAdvanceSale() {
	
	return this.discountAdvanceSale;
}


@Override
public int getNumberOfSoldSeats() {
	
	int soldSeats = 0;
	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!=null)
			soldSeats++;
	}
	return soldSeats;
}


@Override
public int getNumberOfNormalSaleSeats() {
	int normalSold = 0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!= null && this.seats[i].getType().name().equals("NORMAL"))
			normalSold++;
	}
	return normalSold;
}


@Override
public int getNumberOfAdvanceSaleSeats() {
	int advancedSold = 0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!= null && this.seats[i].getType().name().equals("ADVANCE_SALE"))
			advancedSold++;
	}
	return advancedSold;
}


@Override
public int getNumberOfSeats() {

	return this.nSeats;
}


@Override
public int getNumberOfAvailableSeats() {
	int availableSeats = 0;

	for(int i = 0; i<this.nSeats; i++){
		if(this.seats[i] == null)
			availableSeats++;
	}
	return availableSeats;
}


@Override
public Seat getSeat(int pos) {
	
	return this.seats[pos-1];
}


@Override
public Person refundSeat(int pos) {
	
	Person person = null;
	if(this.seats[pos-1] != null)
	{
		person = this.seats[pos-1].getHolder();
		this.seats[pos-1] = null;
	}
	return person;
}	


@Override
public boolean sellSeat(int pos, Person p, boolean advanceSale) {

	if(this.seats[pos-1] != null)
	{
		return false;
	}else
	{
		this.seats[pos-1] = new Seat(this, pos, (advanceSale == true? Type.ADVANCE_SALE : Type.NORMAL), p);
		return true;
	}
}


@Override
public int getNumberOfAttendingChildren() {
	
	int childrenCount = 0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!= null && this.seats[i].getHolder().getAge() < Configuration.CHILDREN_EXMAX_AGE)
			childrenCount++;
	}
	return childrenCount;
}


@Override
public int getNumberOfAttendingAdults() {
	int adultCount = 0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!= null && this.seats[i].getHolder().getAge() >= Configuration.CHILDREN_EXMAX_AGE) {
			adultCount++;
		}
	}
	return adultCount;
}


@Override
public int getNumberOfAttendingElderlyPeople() {
	int elderCount = 0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!= null && this.seats[i].getHolder().getAge() >= Configuration.ELDERLY_PERSON_INMIN_AGE)
			elderCount++;
	}
	return elderCount;
}


@Override
public List<Integer> getAvailableSeatsList() {

	ArrayList<Integer> asientos = new ArrayList<Integer>();

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]==null)
			asientos.add(i+1);
	}
	
	return asientos;
}


@Override
public List<Integer> getAdvanceSaleSeatsList() {

	ArrayList<Integer> asientosAdvanceSale = new ArrayList<Integer>();

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!=null && this.seats[i].getType().name().equals("ADVANCE_SALE"))
			asientosAdvanceSale.add(i+1);
	}
	
	return asientosAdvanceSale;
}


@Override
public int getMaxNumberConsecutiveSeats() {
	int consecutivosMax = 0, consecutivos=0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(consecutivos > consecutivosMax)
		{
			consecutivosMax = consecutivos;
		}
		if(this.seats[i] != null)
		{
			consecutivos = 0;
		}else
		{
			consecutivos++;
		}

	}

	return consecutivosMax;
}


@Override
public Double getPrice(Seat seat) {

	Double priceSeat = this.price;

	if(seat.getType().name().equals("ADVANCE_SALE"))
		priceSeat -= (priceSeat * (double)(this.discountAdvanceSale/100));

	return priceSeat;
}


@Override
public Double getCollectionEvent() {
	Double totalPrice = 0.0;

	for(int i = 0; i<this.nSeats; i++)
	{
		if(this.seats[i]!= null)
			totalPrice += getPrice(this.seats[i]);
	}
	return totalPrice;
}


@Override
public int getPosPerson(Person p) {
	int pos = -1, i = 0;

	while(pos == -1 && i < this.nSeats)
	{
		if(this.seats[i] != null){
			if(this.seats[i].getHolder().equals(p))
				pos = i+1;
		}

		i++;
	}
	return pos;
}


@Override
public boolean isAdvanceSale(Person p) {

	return this.seats[getPosPerson(p)-1].getType().name().equals("ADVANCE_SALE");
}
   


}	