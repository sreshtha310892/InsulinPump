package bin;

import java.math.BigDecimal;
import java.util.Observable;

public class InsulinReservoir extends Observable {
	private static final Object InsulinReservoir = null;
	final String STATUS_LOW = "LOW";
	final String STATUS_FULL = "FULL";
	final String STATUS_EMPTY = "EMPTY";
	final String STATUS_OK = "OK";
	
	private BigDecimal tank_capacity = new BigDecimal(100); //units
	private BigDecimal available_amount = tank_capacity  ;
	AudioPlayer audioplayer02 = new AudioPlayer();
	
	public InsulinReservoir(){
		
	}
	public void refill() {
		available_amount = tank_capacity; 
		
	}

	public BigDecimal getInsulinAmount(BigDecimal amount) {
		available_amount = available_amount.subtract(amount);
		setChanged();
		notifyObservers(InsulinReservoir);
		return amount;
	}

	public BigDecimal getAvailable(){
		if(available_amount.compareTo(new BigDecimal(20))<0 && available_amount.compareTo(new BigDecimal(5))>0){
			String song="E:\\programm\\InsulinGlucagon\\sound\beep.wav";
		    audioplayer02.playAudio(song);
		}else if(available_amount.compareTo(new BigDecimal(5))<0 && !SimulatorUtility.mailSentIns){
			//send emails
			String[] recipients = new String[]{"sreshtha310892@gmail.com"};
			String[] bccRecipients = new String[]{"poojapatelce@gmail.com"};
			String subject = "Patient1 pump shutdown(Insulin Reservoir is Empty)!!";
			String messageBody = "Patient1 is not changing his insulin reservoir and the system may shut down. "
					+ "Can lead to dangerous effects.";
			//new MailUtil().sendMail(recipients, bccRecipients, subject, messageBody);
			SimulatorUtility.mailSentIns=true;

		}
		return this.available_amount;
	}

	public void setInsulin(BigDecimal amount) {
		this.available_amount = amount;
		setChanged();
		notifyObservers(InsulinReservoir);	
	}
	



}
