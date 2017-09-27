package eaglezr.rip_shredder;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Mark Zeagler
 *
 */
public class RIP_Shredder {

	/**
	 * Retrieves the RIPs from the default directory.
	 * 
	 * @return An ArrayList of RIP objects containing the new RIPs
	 */
	public static ArrayList<RIP> getRIPs() {
		System.out.println("Retrieving all files in the default directory.");
		ArrayList<RIP> rips = new ArrayList<RIP>();
		ArrayList<File> addresses = getAddresses();
		// System.out.println(addresses.size());

		System.out.println("Beginning to process each RIP document.");
		while (!addresses.isEmpty()) {
			File currFile = addresses.remove(0);
			try {
				rips.addAll(ripProcessor(currFile));
			} catch (FileNotFoundException e) {
				System.err
						.println("A RIP had an error. It may need to be manually added.");
				// e.printStackTrace();
				// RIP errRIP = new RIP();
				// errRIP.name = "ERROR";
				// rips.add(errRIP);
			}
		}
		System.out.println("Finished processing all RIPs.");
		return rips;
	}

	// Collects the addresses of each RIP in the normal directory
	private static ArrayList<File> getAddresses() {
		ArrayList<File> addresses = new ArrayList<File>();

		System.out
				.println("Retrieving the address of each item in the default directory");
		System.out.println("The following documents will be processed:");
		File folder = new File(
				"C:\\Users\\Mark\\workspace\\Fun Stuff\\src\\New RIPs");
		for (File contents : folder.listFiles()) {
			System.out.println(contents);
			addresses.add(contents);
		}
		System.out.println("Done collecting addresses.");
		return addresses;
	}

	// Separates files with multiple RIPs into separate text files to be made
	// into separate RIP objects
	private static ArrayList<RIP> ripProcessor(File file)
			throws FileNotFoundException {
		System.out.println("Processing a RIP Document.");
		ArrayList<RIP> rips = new ArrayList<RIP>();

		System.out
				.println("Using a scanner to add each line of text to a String.");
		Scanner scanner = new Scanner(file);
		String fileText = "";
		while (scanner.hasNextLine()) {
			fileText += scanner.nextLine() + "\n";
		}
		scanner.close();
		System.out.println("Done with the scanner.");

		ArrayList<String> ripTexts = ripSeparator(fileText);
		// System.out.println(ripTexts.size());

		System.out
				.println("The separated texts are being turned into RIP obects and added to an array.");
		while (!ripTexts.isEmpty()) {
			rips.add(dataToRIP(ripTexts.remove(0)));
		}

		System.out.println("Done processing a RIP document.");
		return rips;
	}

	// helper method for the below method
	private static ArrayList<String> ripSeparator(String bulkText) {
		System.out.println("Separating the String into separate RIPs.");
		ArrayList<String> texts = new ArrayList<String>();
		System.out.println("The RIP document has been fully separated.");
		return ripSeparator(bulkText, texts);
	}

	// This separates the bulk text data into the individual texts for each RIP
	private static ArrayList<String> ripSeparator(String bulkText,
			ArrayList<String> texts) {
		System.out.println("Beginning recursive separation process.");
		if (bulkText.length() < 100) {
			System.out
					.println("A branch of the recursive separation process is complete.");
			return texts;
		}
		int breakpoint = bulkText.indexOf("Page: ");
		String frontText = bulkText.substring(0, breakpoint -1);
		String backText = bulkText.substring(breakpoint);
		while (true) {// loop through to find all ends of pages and split into 2
						// strings when x=y
			Scanner wastingMemory = new Scanner(backText);
			wastingMemory.next();

			int x = wastingMemory.nextInt();
			wastingMemory.next();
			int y = wastingMemory.nextInt();
			wastingMemory.close();

//			System.out.println("x = " + x + ", y = " + y);

			breakpoint = backText.indexOf("\n") + 1;
			frontText += backText.substring(0, breakpoint -1);
			backText = backText.substring(breakpoint);

			if (x == y) {
				texts.add(frontText);

				texts.addAll(ripSeparator(backText, texts));
				System.out.println("A RIP is separated.");

				System.out
						.println("A branch of the recursive separation process is complete.");
				return texts;
			}
			breakpoint = backText.indexOf("Page: ");
			frontText = backText.substring(0, breakpoint -1);
			backText = backText.substring(breakpoint);
			// System.out.println(tempText);
		}
	}

	// Transfers the textual data of a single file into a RIP object
	private static RIP dataToRIP(String text) {
		System.out
				.println("Beginning to collect data to create the RIP object.");
//		System.out.println(text);
		// Name
		int startIndex = text.indexOf("1. ") + 4;
		int endIndex = text.indexOf("SSAN") - 2;
		String name = text.substring(startIndex, endIndex).trim();
		System.err.println(name);

		// Rank
		String rank = getRank(name);
		name = name.substring(3).trim();

		// PDD
		startIndex = text.indexOf("PROJ DPT DT: ") + 13;
		endIndex = startIndex + 11;
		MyDate PDD = toDate(text.substring(startIndex, endIndex));

		// RNLTD
		startIndex = text.indexOf("RNLTD: ") + 7;
		endIndex = startIndex + 11;
		MyDate RNLTD = toDate(text.substring(startIndex, endIndex));

		// DAS
		startIndex = text.indexOf("DT ARRIVED STATION: ") + 20;
		endIndex = startIndex + 11;
		MyDate DAS = toDate(text.substring(startIndex, endIndex));

		// SSN
		startIndex = text.indexOf("SSAN: ") + 6;
		endIndex = startIndex + 11;
		String SSN = text.substring(startIndex, endIndex).trim();

		// CurrPAS
		startIndex = text.indexOf("CURR-PAS:  ") + 11;
		endIndex = startIndex + 8;
		String currPAS = text.substring(startIndex, endIndex).trim();

		// ProjPAS
		startIndex = text.indexOf("PROJ-PAS:  ") + 11;
		endIndex = startIndex + 8;
		String projPAS = text.substring(startIndex, endIndex).trim();

		// CurrUnit
		startIndex = text.indexOf("CURR-PAS:  ");
		String currUnit = getLine(text.substring(startIndex), 1);
		endIndex = currUnit.indexOf("RNLTD: ");
		currUnit = currUnit.substring(0, endIndex).trim();

		// ProjUnit
		startIndex = text.indexOf("PROJ-PAS:  ");
		String projUnit = getLine(text.substring(startIndex), 1).trim();

		// CurrBase
		startIndex = text.indexOf("CURR-PAS:  ");
		String currBase = getLine(text.substring(startIndex), 2);
		endIndex = currBase.indexOf("AAN:");
		currBase = currBase.substring(0, endIndex).trim();

		// ProjBase
		startIndex = text.indexOf("PROJ-PAS:  ");
		String projBase = getLine(text.substring(startIndex), 2).trim();

		// Servicing MPS
		startIndex = text.indexOf("SERVICING MPF: ") + 15;
		endIndex = startIndex + 7;
		String servicingMPS = text.substring(startIndex, endIndex).trim();

		// Selected AFSC
		startIndex = text.indexOf("AFSC IN WHICH SELECTED:  ") + 25;
		endIndex = startIndex + 7;
		String selectedAFSC = text.substring(startIndex, endIndex).trim();

		// Marital Status
		startIndex = text.indexOf("MARITAL STAT: ") + 14;
		endIndex = startIndex + 8;
		String maritalStatus = text.substring(startIndex, endIndex).trim();

		// num Dependents
		startIndex = text.indexOf("# OF DEPN: ") + 11;
		endIndex = startIndex + 2;
		int numDepn = Integer.parseInt(text.substring(startIndex, endIndex)
				.replaceAll("\\s", ""));

		// ETS
		MyDate ETS = new MyDate(3888, 8, 8);
		if (rank.charAt(0) == 'E') {
			startIndex = text.indexOf("ETS: ") + 5;
			endIndex = startIndex + 11;
			ETS = toDate(text.substring(startIndex, endIndex));
		}

		// DOS
		startIndex = text.indexOf("DOS: ") + 5;
		endIndex = startIndex + 11;
		MyDate DOS = toDate(text.substring(startIndex, endIndex));

		// TAFMSD
		startIndex = text.indexOf("TAFMSD: ") + 8;
		endIndex = startIndex + 11;
		MyDate TAFMSD = toDate(text.substring(startIndex, endIndex));

		// ASD
		startIndex = text.indexOf("ASSIGNMENT SELECTION DATE (ASD): ") + 33;
		endIndex = startIndex + 11;
		MyDate ASD = toDate(text.substring(startIndex, endIndex));

		// AACs
		startIndex = text
				.indexOf("ASSIGNMENT AVAILABILITY DATA (CODES AND DATES OF AVAILABILITY):");
		ArrayList<String> AACs = getListing(text.substring(startIndex));

		// ALCs
		startIndex = text
				.indexOf("ASSIGNMENT LIMITATION DATA (CODES AND DATES OF AVAILABILITY):");
		ArrayList<String> ALCs = getListing(text.substring(startIndex));

		// AAR
		startIndex = text.indexOf("ASSIGNMENT ACTION REASON: ") + 26;
		endIndex = startIndex + 2;
		String AAR = text.substring(startIndex, endIndex);

		// PPCs
		startIndex = text.indexOf("PPC") + 11;
		endIndex = text.indexOf("REMARKS:");
//		System.out.println(startIndex + ", " + endIndex);
		ArrayList<String> PPCs = getPPCs(text.substring(startIndex, endIndex));

		RIP rip = new RIP(name, rank, PDD, RNLTD, DAS, SSN, currPAS, projPAS,
				currUnit, projUnit, currBase, projBase, servicingMPS,
				selectedAFSC, maritalStatus, numDepn, ETS, DOS, TAFMSD, ASD,
				AACs, ALCs, AAR, PPCs, text);

		System.out.println("Finished collecting data from RIP.");
		return rip;
	}

	// Retrieves rank if it is in the ranks listing. Otherwise, it returns an
	// "err" rank.
	private static String getRank(String name) {
		System.out.println("Collecting the rank from the name.");
		String rank = "err";
		try {
			Scanner listingTextOfficer = new Scanner(
					new File(
							"C:\\Users\\Mark\\workspace\\Fun Stuff\\src\\Officer Ranks.txt"));
			Scanner listingTextEnlisted = new Scanner(
					new File(
							"C:\\Users\\Mark\\workspace\\Fun Stuff\\src\\Enlisted Ranks.txt"));

			String ranksListOfficer = "";
			while (listingTextOfficer.hasNextLine()) {
				ranksListOfficer += listingTextOfficer.nextLine() + " \n";
			}

			String ranksListEnlisted = "";
			while (listingTextEnlisted.hasNextLine()) {
				ranksListEnlisted += listingTextEnlisted.nextLine() + " \n";
			}

			String memRank = name.substring(0, 3).trim();
			// System.out.println(memRank);
			// System.out.println(ranksListOfficer.contains("BG"));
			// System.out.println(ranksListOfficer);

			if (ranksListOfficer.contains(memRank)) {
				rank = "O\\" + memRank;
			}

			if (ranksListEnlisted.contains(memRank)) {
				rank = "E\\" + memRank;
			}
			listingTextEnlisted.close();
			listingTextOfficer.close();
		} catch (FileNotFoundException e) {
			System.out.println("There was an error reading the rank.");
		}

		return rank;
	}

	// Converts "DD MMM YYYY" into a Date object.
	private static MyDate toDate(String text) {
		System.out.println("Turning text into a MyDate object.");
		int day = Integer.parseInt(text.substring(0, 2));

		int month = 0;
		String textMonth = text.substring(3, 6);

		if (textMonth.contains("JAN")) {
			month = 1;
		}
		if (textMonth.contains("FEB")) {
			month = 2;
		}
		if (textMonth.contains("MAR")) {
			month = 3;
		}
		if (textMonth.contains("APR")) {
			month = 4;
		}
		if (textMonth.contains("MAY")) {
			month = 5;
		}
		if (textMonth.contains("JUN")) {
			month = 6;
		}
		if (textMonth.contains("JUL")) {
			month = 7;
		}
		if (textMonth.contains("AUG")) {
			month = 8;
		}
		if (textMonth.contains("SEP")) {
			month = 9;
		}
		if (textMonth.contains("OCT")) {
			month = 10;
		}
		if (textMonth.contains("NOV")) {
			month = 11;
		}
		if (textMonth.contains("DEC")) {
			month = 12;
		}

		int year = Integer.parseInt(text.substring(7));
		return new MyDate(year, month - 1, day);
	}

	// Gets the AAC/ALC listing starting at the given index.
	private static ArrayList<String> getListing(String text) {
		System.out
				.println("Reading the 2-letter availability/limitation codes.");
		ArrayList<String> listing = new ArrayList<String>();

		Scanner temp = new Scanner(text);
		temp.nextLine();
		for (int i = 0; i < 4; i++) {
			String line = temp.nextLine();
			listing.add(line.substring(12, 14).trim());
		}
		temp.close();
		return listing;
	}

	// Retrieves PPCs from the text.
	private static ArrayList<String> getPPCs(String text) {
		System.out.println("Collecting PPC codes.");
		ArrayList<String> PPCs = new ArrayList<String>();
		Scanner scanner = new Scanner(text);
		while (scanner.hasNext()) {
			String next = scanner.next();
			if (!next.contains("000")) {
				PPCs.add(next);
			}
		}
		scanner.close();
		return PPCs;
	}

	// Extracts a given line and converts it to a String.
	private static String getLine(String text, int line) {
		Scanner scanner = new Scanner(text);
		for (int i = 0; i < line; i++) {
			scanner.nextLine();
		}

		// I know, but i wanted to get rid of the warning
		String returnText = scanner.nextLine();
		scanner.close();
		return returnText;
	}
}