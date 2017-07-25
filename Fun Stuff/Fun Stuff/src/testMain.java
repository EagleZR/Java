import java.util.ArrayList;
import eaglezr.rip_shredder.RIP;
import eaglezr.rip_shredder.RIP_Shredder;


public class testMain {

	public static void main(String[] args) {
		ArrayList<RIP> rips = RIP_Shredder.getRIPs();
		System.out.println(rips.get(0).outputAll());
		System.out.println(rips.get(1).outputAll());
		System.out.println(rips.get(2).outputAll());
		
		System.out.println(rips.get(0).name);

	}

}
