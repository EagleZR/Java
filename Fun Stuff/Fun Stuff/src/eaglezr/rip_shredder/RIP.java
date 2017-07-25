package eaglezr.rip_shredder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Mark
 *
 */
public class RIP {
	public String name = "";
	public String firstName = "";
	public String lastName = "";
	public String rank = "";
	public MyDate PDD = new MyDate(); // new date(int yyyy, int mm, int
												// dd)
	public MyDate RNLTD = new MyDate();
	public MyDate DAS = new MyDate();
	public String SSN = "";
	public String currPAS = "";
	public String projPAS = "";
	public String currUnit = "";
	public String projUnit = "";
	public String currBase = "";
	public String projBase = "";
	public String servicingMPF = "";
	public String selectedAFSC = "";
	public String maritalStatus = "";
	public int numDepn = 0;
	public MyDate ETS = new MyDate();
	public MyDate DOS = new MyDate();
	public MyDate TAFMSD = new MyDate();
	public MyDate ASD = new MyDate();
	public ArrayList<String> AACs = new ArrayList<String>();
	public ArrayList<String> ALCs = new ArrayList<String>();
	public String AAR = ""; // Assignment Action Reason
	public ArrayList<String> PPCs = new ArrayList<String>();
	public String text = "";

	public RIP() {

	}

	public RIP(String memName, String memRank, MyDate memPDD, MyDate memRNLTD,
			MyDate memDAS, String memSSN, String memCurrPAS, String memProjPAS,
			String memCurrUnit, String memProjUnit, String memCurrBase,
			String memProjBase, String memServicingMPF, String memSelectedAFSC,
			String memMaritalStatus, int memNumDepn, MyDate memETS,
			MyDate memDOS, MyDate memTAFMSD, MyDate memASD, ArrayList<String> memAACs,
			ArrayList<String> memALCs, String memAAR, ArrayList<String> memPPCs, String text) {
		this.name = memName;
		separateName();
		this.rank = memRank;
		this.PDD = memPDD;
		this.RNLTD = memRNLTD;
		this.DAS = memDAS;
		this.SSN = memSSN;
		this.currPAS = memCurrPAS;
		this.projPAS = memProjPAS;
		this.currUnit = memCurrUnit;
		this.projUnit = memProjUnit;
		this.currBase = memCurrBase;
		this.projBase = memProjBase;
		this.servicingMPF = memServicingMPF;
		this.selectedAFSC = memSelectedAFSC;
		this.maritalStatus = memMaritalStatus;
		this.numDepn = memNumDepn;
		this.ETS = memETS;
		this.DOS = memDOS;
		this.TAFMSD = memTAFMSD;
		this.ASD = memASD;
		this.AACs = memAACs;
		this.ALCs = memALCs;
		this.AAR = memAAR;
		this.PPCs = memPPCs;
		this.text = text;
	}

	public String outputAll() {
		String output = "";

		output += "Name: " + this.name + "\n";
		output += "Rank: " + this.rank + "\n";
		output += "SSN: " + this.SSN + "\n\n";

		output += "PDD: " + this.PDD + "\n";
		output += "RNLTD: " + this.RNLTD + "\n";
		output += "DAS: " + this.DAS + "\n\n";

		output += "Current PAS: " + this.currPAS + "\n";
		output += "Current Unit: " + this.currUnit + "\n";
		output += "Current Base: " + this.currBase + "\n\n";

		output += "Projected PAS: " + this.projPAS + "\n";
		output += "Projected Unit: " + this.projUnit + "\n";
		output += "Projected Base: " + this.projBase + "\n";
		output += "Servicing MPS: " + this.servicingMPF + "\n\n";

		output += "Selected AFSC: " + this.selectedAFSC + "\n";
		output += "Marital Status: " + this.maritalStatus + "\n";
		output += "Number of Dependents: " + this.numDepn + "\n\n";

		output += "ETS: " + this.ETS + "\n";
		output += "DOS: " + this.DOS + "\n";
		output += "TAFMSD: " + this.TAFMSD + "\n";
		output += "ASD: " + this.ASD + "\n\n";

		output += "AACs: " + this.AACs + "\n";
		output += "ALCs: " + this.ALCs + "\n\n";

		output += "AAR: " + this.AAR + "\n\n";

		output += "PPCs: " + this.PPCs + "\n";

		return output;
	}

	public String toString() {
		return this.text;
	}

	public Object[] getArray() {
		Object[] arrayRIP = { this.rank, this.firstName, this.lastName,
				this.SSN, this.PDD, this.RNLTD };
		return arrayRIP;
	}

	private void separateName(){
		Scanner temp = new Scanner(this.name);
		this.lastName = temp.next().trim();
		this.lastName = this.lastName.substring(0, this.lastName.length()-1);
		this.firstName = temp.next().trim();
		temp.close();
	}
}
