package ule.edi.model;

import java.util.Date;
import java.util.List;

public interface Event {
	
	
	public String getName();
	
	public Date getDateEvent();
	
	public Double getPrice();
	
	public Byte getDiscountAdvanceSale();

	
	/**
	 * Calcula el número de asientos totales vendidos del evento.
	 * 
	 * @return
	 */
	public int getNumberOfSoldSeats();
	
	/**
	 * Calcula el número de butacas vendidos en venta normal del evento.
	 * 
	 * @return
	 */
	public int getNumberOfNormalSaleSeats();
	
	/**
	 * Calcula el número de butacas vendidas en venta anticipada.
	 * 
	 * @return
	 */
	public int getNumberOfAdvanceSaleSeats();
	
	/**
	 * Número de butacas totales del evento (ocupadas y disponibles).
	 * 
	 * @return
	 */
	public int getNumberOfSeats();
	
	
	/**
	 * Calcula el número de butacas disponibles (no vendidas).
	 * 
	 * @return
	 */
	public int getNumberOfAvailableSeats();

	/**
	 * Devuelve la butaca en la posición dada o null si no está ocupada
	 * 
	 * Las posiciones empiezan en '1'.
	 * 
	 * @param pos
	 * @return
	 */
	public Seat getSeat(int pos);
	

	/**
	 * Libera la butaca de la posición dada. 
	 * 
     * Si la butaca de esa posición ya está libre, devuelve null.
	 * 
	 * Las posiciones empiezan en '1'.
	 * 
	 * 
	 * @param pos
	 * @return p :la persona que ocupaba la butaca o null si la butaca no estaba ocupada.
	 * 
	 */
	public Person refundSeat(int pos);

	/**
	 * 
	 * Si la butaca de esa posición ya está ocupada, no hace nada.
	 * 
	 * Las posiciones empiezan en '1'.
	 * 
	 * @param pos
	 * @param p : la persona que ocupará la butaca
	 * @param isAdvanceSale: true si es venta anticipada; false si es venta normal
	 * @return true indica si se pudo realizar la venta de la butaca, false en caso contrario
	 */
	public boolean sellSeat(int pos, Person p, boolean isAdvanceSale);
	

	/**
	 * Calcula el número de niños asistentes al evento.
	 * 
	 * 	[0, Configuration.CHILDREN_EXMAX_AGE)
	 * 
	 * CHILDREN_EXMAX_AGE no contabiliza como niño (menor que)
	 * 
	 * @return
	 */
	public int getNumberOfAttendingChildren();
	
	/**
	 * Calcula el número de adultos asistentes al evento.
	 * 
	 * 	[Configuration.CHILDREN_EXMAX_AGE, Configuration.ELDERLY_PERSON_INMIN_AGE)
	 * 
	 * ELDERLY_PERSON_INMIN_AGE no incluido como adulto (menor que)
	 * 
	 * @return
	 */
	public int getNumberOfAttendingAdults();

	/**
	 * Calcula el número de ancianos asistentes al evento.
	 * 
	 * 	[Configuration.ELDERLY_PERSON_INMIN_AGE, Integer.MAX_VALUE)
	 * 
	 * ELDERLY_PERSON_INMIN_AGE incluido como anciano
	 *  
	 * @return
	 */
	public int getNumberOfAttendingElderlyPeople();

	
	/**
	 * Calcula la lista de números de butacas disponibles
	 * Tener en cuenta que las posiciones empiezan en 1
	 * 
	 * @return lista de posiciones disponibles 
	 */
    public List<Integer> getAvailableSeatsList();
    
    /**
	 * Calcula la lista de números de butacas vendidas en venta anticipada
	 * Tener en cuenta que las posiciones empiezan en 1
	 * 
	 * @return lista de posiciones vendidas en venta anticipada 
	 */
    public List<Integer> getAdvanceSaleSeatsList();

    /**
	 * Calcula el número máximo de posiciones disponibles consecutivas
	 * 
	 * @return nº de posiciones consecutivas disponibles
	 */
    public int getMaxNumberConsecutiveSeats();

    
    /**
	 * Calcula el precio de la butaca en función del tipo de venta y del descuento si es venta anticipada de esa butaca
	 * Tener en cuenta que las posiciones empiezan en 1
	 * 
	 * 	 @return precio de la butaca 
	 */
	public Double getPrice(Seat seat);

   	
	/**
	 * Calcula el importe total recaudado por las butacas ocupadas
	 * 
	 * 	 @return importe total recaudado
	 */
	public Double getCollectionEvent();
	
	/**
	 * Calcula el número de butaca que ocupa la persona (empiezan en 1)
	 * 
	 * @param p persona a buscar 
	 * @return la butaca que ocupa la persona o -1 si no está
	 */
	public int getPosPerson(Person p);
	
		
	/**
	 * 
	 * @param p persona a buscar 
	 * @return true si la persona ocupa una butaca vendida en venta anticipada, false en caso contrario
	 */
	public boolean isAdvanceSale(Person p);
	
	
}
