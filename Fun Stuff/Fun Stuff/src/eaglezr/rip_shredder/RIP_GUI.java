package eaglezr.rip_shredder;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class RIP_GUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JMenuItem mntmLoadRips = new JMenuItem("Load Rips");
	private RIP testRIP = RIP_Shredder.getRIPs().get(0);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RIP_GUI frame = new RIP_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RIP_GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		Object[] columnNames = { "Rank", "First Name", "Last Name", "SSN", "PDD", "RNLTD" };
		Object[][] data = {this.testRIP.getArray()};
		table = new JTable(data, columnNames);
		contentPane.add(table, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane);

		contentPane.add(menuBar, BorderLayout.NORTH);

		menuBar.add(mnFile);

		mnFile.add(mntmLoadRips);
	}

}
