package eaglezr.rip_shredder;
import java.util.Date;


public class MyDate extends Date {

	private int myDay = 0;
	private int myMonth = 0; 
	private int myYear = 0;
	
	public MyDate() {
		// TODO Auto-generated constructor stub
	}

	public MyDate(long arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	//Creates a date from a year, month, day format.
	public MyDate(int year, int month, int day){
		this.myDay = day;
		this.myMonth = month;
		this.myYear = year;
	}
	
	public String toString(){
		String printDay = Integer.toString(this.myDay);
		if(this.myDay<10){
			printDay = "0" + this.myDay;
		}
		return printDay + " " + monthText(this.myMonth) + " " + this.myYear; 
	}
	
	private String monthText(int monthNum){
		String month = "err";
		
		if(monthNum == 0){
			month = "Jan";
		}
		else if(monthNum == 1){
			month = "Feb";
		}
		else if(monthNum == 2){
			month = "Mar";
		}
		else if(monthNum == 3){
			month = "Apr";
		}
		else if(monthNum == 4){
			month = "May";
		}
		else if(monthNum == 5){
			month = "Jun";
		}
		else if(monthNum == 6){
			month = "Jul";
		}
		else if(monthNum == 7){
			month = "Aug";
		}
		else if(monthNum == 8){
			month = "Sep";
		}
		else if(monthNum == 9){
			month = "Oct";
		}
		else if(monthNum == 10){
			month = "Nov";
		}
		else if(monthNum == 11){
			month = "Dec";
		}
		
		return month;
	}

}
