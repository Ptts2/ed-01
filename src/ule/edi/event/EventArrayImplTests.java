package ule.edi.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.*;

import ule.edi.model.*;

public class EventArrayImplTests {

	private DateFormat dformat = null;
	private EventArrayImpl e;
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public EventArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before
	public void testBefore() throws Exception{
	    e = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 110);

	}
	
	@Test
	public void testEventoVacio() throws Exception {
		
	    Assert.assertTrue(e.getNumberOfAvailableSeats()==110);
	    Assert.assertEquals(e.getNumberOfAvailableSeats(), 110);
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
	}
	
	@Test
	public void testSellSeat1Adult() throws Exception{
		
			
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
		Assert.assertTrue(e.sellSeat(1, new Person("10203040A","Alice", 34),false));	//venta normal
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);  
	    Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 1);
	  
	}
	

	
	@Test
	public void testgetCollection() throws Exception{
		Event  ep = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertEquals(ep.sellSeat(1, new Person("1010", "AA", 10), true),true);
		Assert.assertTrue(ep.getCollectionEvent()==75);					
	}
	
	@Test
	public void testOtherConstructors() throws Exception{
		Byte price = 26;
		Event  otherC = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4, 150.0, price);
		Assert.assertTrue(otherC.getPrice() == 150.0);
	}
	
	@Test
	public void testgetName() {
		Assert.assertTrue(e.getName().equals("The Fabulous Five"));
	}
	
	@Test
	public void testgetDateEvent() throws Exception{
		Assert.assertTrue(e.getDateEvent().equals(parseLocalDate("24/02/2018 17:00:00")));
	}
	
	@Test
	public void testgetPrice() {
		Assert.assertTrue(e.getPrice() == 100.0);
	}
	
	@Test
	public void getDiscountAdvanceSale() {
		Assert.assertTrue(e.getDiscountAdvanceSale() == 25);
	}
	
	@Test
	public void testgetNumberOfSoldSeats() throws Exception{
		Event  event = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		event.sellSeat(1, new Person("1010", "AA", 10), true);
		event.sellSeat(3, new Person("1011", "AB", 11), true);
		Assert.assertTrue(event.getNumberOfSoldSeats() == 2);
	}
	
	@Test
	public void testgetNumberOfNormalSaleSeats() throws Exception{
		Event  event = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		event.sellSeat(1, new Person("1010", "AA", 10), true);
		event.sellSeat(3, new Person("1011", "AB", 11), false);
		Assert.assertTrue(event.getNumberOfNormalSaleSeats() == 1);
	}
	
	@Test
	public void testgetNumberOfAdvanceSaleSeats() throws Exception{
		Event  event = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		event.sellSeat(1, new Person("1010", "AA", 10), false);
		event.sellSeat(3, new Person("1011", "AB", 11), true);
		Assert.assertTrue(event.getNumberOfAdvanceSaleSeats() == 1);
	}
	
	@Test
	public void testgetNumberOfSeats() throws Exception{
		Event  event = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		event.sellSeat(1, new Person("1010", "AA", 10), false);
		event.sellSeat(3, new Person("1011", "AB", 11), true);
		Assert.assertTrue(event.getNumberOfSeats() == 4);
	}
	
	@Test
	public void testgetNumberOfAvailableSeats() throws Exception{
		Assert.assertTrue(e.getNumberOfAvailableSeats()==110);
		e.sellSeat(5, new Person("1012","ABC", 13), true);
		Assert.assertTrue(e.getNumberOfAvailableSeats()==109);

	}
	
	@Test
	public void testgetSeat() throws Exception{
		Event  event = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertNull(event.getSeat(1));
		event.sellSeat(1, new Person("1010", "AA", 10), false);
		Assert.assertNotNull(event.getSeat(1));
		Assert.assertNull(event.getSeat(5));
		Assert.assertNull(event.getSeat(-5));
	}
	
	@Test
	public void testrefundSeat() {
		Assert.assertNull(e.refundSeat(1));
		e.sellSeat(1, new Person("1010", "AA", 10), false);
		Assert.assertNotNull(e.refundSeat(1));
		Assert.assertNull(e.refundSeat(300));
		Assert.assertNull(e.refundSeat(-300));
	}
	
	@Test
	public void testsellSeat() {
		Assert.assertTrue(e.sellSeat(1, new Person("1010", "AA", 10), false));
		Assert.assertFalse(e.sellSeat(1, new Person("1010", "AA", 10), false));
		Assert.assertFalse(e.sellSeat(300, new Person("1010", "AA", 10), false));
		Assert.assertFalse(e.sellSeat(-300, new Person("1010", "AA", 10), false));
	}
	
	@Test
	public void testgetNumberOfAttendingChildren() {
		Assert.assertTrue(e.getNumberOfAttendingChildren() == 0);
		e.sellSeat(1, new Person("1010", "AA", Configuration.CHILDREN_EXMAX_AGE-1), false);
		Assert.assertTrue(e.getNumberOfAttendingChildren() == 1);
		e.sellSeat(3, new Person("1010", "AA", Configuration.CHILDREN_EXMAX_AGE), false);
		Assert.assertTrue(e.getNumberOfAttendingChildren() == 1);
	}
	
	@Test
	public void testgetNumberOfAttendingAdults() {
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 0);
		e.sellSeat(1, new Person("1010", "AA", Configuration.CHILDREN_EXMAX_AGE), false);
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 1);
		e.sellSeat(3, new Person("1010", "AA", Configuration.CHILDREN_EXMAX_AGE-1), false);
		Assert.assertTrue(e.getNumberOfAttendingAdults() == 1);
	}
	
	@Test
	public void testgetNumberOfAttendingElderlyPeople() {
		Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 0);
		e.sellSeat(1, new Person("1010", "AA", Configuration.ELDERLY_PERSON_INMIN_AGE), false);
		Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 1);
		e.sellSeat(2, new Person("1010", "AA", Configuration.ELDERLY_PERSON_INMIN_AGE-1), false);
		Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 1);
		e.sellSeat(3, new Person("1010", "AA", Integer.MAX_VALUE), false);
		Assert.assertTrue(e.getNumberOfAttendingElderlyPeople() == 1);
	}
	
	@Test
	public void testgetAvailableSeatsList() {
		Assert.assertTrue(e.getAvailableSeatsList().size() == 110);
		e.sellSeat(2, new Person("1010", "AA", 10), false);
		Assert.assertTrue(e.getAvailableSeatsList().size() == 109);
	}
	
	@Test
	public void testgetAdvanceSaleSeatsList() {
		Assert.assertTrue(e.getAdvanceSaleSeatsList().isEmpty());
		e.sellSeat(2, new Person("1010", "AA", 10), true);
		Assert.assertTrue(e.getAdvanceSaleSeatsList().size() == 1);
		e.sellSeat(3, new Person("1010", "AA", 10), false);
		Assert.assertTrue(e.getAdvanceSaleSeatsList().size() == 1);
	}
	
	@Test
	public void testgetMaxNumberConsecutiveSeats() {
		Assert.assertTrue(e.getMaxNumberConsecutiveSeats()==110);
		e.sellSeat(56, new Person("1010", "AA", 10), true);
		Assert.assertTrue(e.getMaxNumberConsecutiveSeats()==55);
	}
	
	@Test
	public void testgetPriceCollectionEvent() {
		e.sellSeat(1, new Person("1010", "AA", 10), true);
		e.sellSeat(2, new Person("1010", "AA", 10), false);
		Assert.assertTrue(e.getCollectionEvent() == 175.0);
	}
	
	@Test
	public void testgetPosPerson() {
		Person person = new Person("1010", "AA", 10);
		
		Assert.assertTrue(e.getPosPerson(person) == -1);
		e.sellSeat(1, new Person("1011", "AB", 11), true);
		e.sellSeat(2, person, true);
		Assert.assertTrue(e.getPosPerson(person) == 2);
	}
	
	@Test
	public void testisAdvanceSale() {
		Person person1 = new Person("1011", "AB", 11);
		Person person2 = new Person("1010", "AA", 10);
		
		e.sellSeat(1, person1, true);
		e.sellSeat(2, person2, false);
		Assert.assertTrue(e.isAdvanceSale(person1));
		Assert.assertFalse(e.isAdvanceSale(person2));
		Assert.assertFalse(e.isAdvanceSale(new Person("1013", "AC", 13)));
	}
	
	@Test
	public void testPersonEquals() {
		Person person1 = new Person("Person", "1000", 15);
		Person person2 = new Person("SamePerson", "1000", 16);
		Person person3 = new Person("NotSamePerson", "1001", 15);
		
		Assert.assertTrue(person1.equals(person1));
		Assert.assertTrue(person1.equals(person2));
		Assert.assertFalse(person1.equals(person3));
		Assert.assertFalse(person1.equals(new Object()));	
		
	}
}
