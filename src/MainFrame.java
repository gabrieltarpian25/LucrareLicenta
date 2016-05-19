import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Wait;

import net.lucrare.licenta.Factura;

public class MainFrame extends JFrame {

	// Singleton MainFrame
	private static MainFrame frame = null;
	
	// boolean values that informs us what option is selected when showing the companies
	static boolean boAdaugaFactura = false;
	static boolean boDownloadFactura = false;
	static boolean boPlatesteFactura = false;

	private static JTable tabel;
	JTextField textSearchFactura;
	JButton butAdaugaFactura, butStergeFactura, butPlatesteFactura, butDownloadFactura;
	JPanel panelSus;
	Font f;

	// get instance
	public static MainFrame getInstance() {
		if (frame == null)
			frame = new MainFrame();
		return frame;
	}

	// class constructor
	public MainFrame() {
		super("Facturi");
		drawGUI();

		this.setTitle("Facturi");
	}

	// function that informs us if the card details are stored
	public static boolean findCardDetails() {
		boolean boResult = false;
		String fileNameToFind = "detaliiCard.txt";

		// current directory where app is installed
		File dir = new File("./Details");

		// all children of the directory ( subfolders and files)
		String[] children = dir.list();

		for (int i = 0; i < children.length; i++) {
			String fileName = children[i];
			if (fileNameToFind.equals(fileName))
				boResult = true;
		}
		return boResult;
	}
	
	// function that informs us if the card details are stored
		public static boolean findLoginDetails(String companie) {
			boolean boResult = false;
			String fileNameToFind = "login"+companie+".txt";

			// current directory where app is installed
			File dir = new File("./Details");

			// all children of the directory ( subfolders and files)
			String[] children = dir.list();

			for (int i = 0; i < children.length; i++) {
				String fileName = children[i];
				if (fileNameToFind.equals(fileName))
					boResult = true;
			}
			return boResult;
		}

	// draw GUI function
	private void drawGUI() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// buttons
		panelSus = new JPanel();
		butAdaugaFactura = new JButton("Adauga Factura");
		butStergeFactura = new JButton("Sterge Factura");
		butPlatesteFactura = new JButton("Plateste Factura");
		butDownloadFactura = new JButton("Descarca Factura");
		textSearchFactura = new JTextField(10);
		panelSus.add(textSearchFactura);
		panelSus.add(butAdaugaFactura);
		panelSus.add(butStergeFactura);
		panelSus.add(butPlatesteFactura);
		panelSus.add(butDownloadFactura);

		// font
		f = new Font("Arial", Font.BOLD, 15);

		// set font to buttons and textField
		textSearchFactura.setFont(f);
		butAdaugaFactura.setFont(f);
		butStergeFactura.setFont(f);
		butPlatesteFactura.setFont(f);
		butDownloadFactura.setFont(f);

		// add panel to layout
		this.add(panelSus, BorderLayout.NORTH);

		// full screen frame
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// create table
		final ArrayList columnNames = new ArrayList();
		final ArrayList data = new ArrayList();

		String PERSISTENCE_UNIT_NAME = "persistenceIG";
		EntityManagerFactory factory;
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = factory.createEntityManager();

		TypedQuery<Factura> query = em.createNamedQuery("Factura.findAll", Factura.class);
		List<Factura> results = query.getResultList();

		int columns = 6;
		columnNames.add("Nr Factura");
		columnNames.add("Companie");
		columnNames.add("Data Emiterii");
		columnNames.add("Data Scadenta");
		columnNames.add("Total de Plata");
		columnNames.add("Status");

		for (int i = 0; i < results.size(); i++) {
			Vector row = new Vector(columns);
			row.add(results.get(i).getNrFactura());
			row.add(results.get(i).getCompanie());
			row.add(results.get(i).getDataEmiterii());
			row.add(results.get(i).getDataScadenta());
			row.add(results.get(i).getTotalPlata());
			row.add(results.get(i).getStatus());
			data.add(row);
		}

		em.close();
		factory.close();

		Vector columnNamesVector = new Vector();
		Vector dataVector = new Vector();

		for (int i = 0; i < data.size(); i++) {
			Vector subArray = (Vector) data.get(i);
			Vector subVector = new Vector();
			for (int j = 0; j < subArray.size(); j++) {
				subVector.add(subArray.get(j));
			}
			dataVector.add(subVector);
		}

		for (int i = 0; i < columnNames.size(); i++) {
			columnNamesVector.add(columnNames.get(i));
		}

		tabel = new JTable(dataVector, columnNamesVector) {
			public Class getColumnClass(int column) {
				for (int row = 0; row < getRowCount(); row++) {
					Object obj = getValueAt(row, column);
					if (obj != null) {
						return obj.getClass();
					}
				}
				return Object.class;
			}

			// Set cell editable to false
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// titlu tabel
		TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Facturi");
		title.setTitleJustification(TitledBorder.CENTER);

		final JScrollPane jsp = new JScrollPane(tabel);
		jsp.setBorder(title);

		// aliniere centrata a continutului tabelului
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < 6; i++)
			tabel.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		// setare font tabel
		tabel.setFont(new Font("Arial", Font.BOLD, 16));

		// Setare rearanjare coloane pe FALSE
		tabel.getTableHeader().setReorderingAllowed(false);

		// Setare culoare Background tabel
		tabel.setBackground(new Color(255, 232, 208));

		// set Row Heigth
		for (int i = 0; i < tabel.getRowCount(); i++)
			tabel.setRowHeight(32);

		// singura selectie
		tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// afisare format data dd-MM-yyyy
		tabel.getColumnModel().getColumn(2).setCellRenderer(new TimestampCellRenderer());
		tabel.getColumnModel().getColumn(3).setCellRenderer(new TimestampCellRenderer());

		// add table to frame
		this.add(jsp, BorderLayout.CENTER);

		// buton adaugaFactura actionListener
		butAdaugaFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boAdaugaFactura = true;
				boDownloadFactura = false;
				boPlatesteFactura = false;
				
				Companies.run();
			}
		});

		butStergeFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] options = new String[2];
				options[0] = new String("NU");
				options[1] = new String("DA");
				int answer=JOptionPane.showOptionDialog(frame.getContentPane(),"Sunteti sigur?","Atentionare", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
				
				if (answer == 1) 
				{
					String nrFactura = getSelectedNrFactura();

					String PERSISTENCE_UNIT_NAME = "persistenceIG";
					EntityManagerFactory factory;
					factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
					EntityManager em = factory.createEntityManager();

					Query query = em.createQuery("DELETE FROM Factura f WHERE f.nrFactura = :nrFact");
					query.setParameter("nrFact", nrFactura);
					EntityTransaction et = em.getTransaction();
					et.begin();
					int r = query.executeUpdate();
					et.commit();
					// factory.close();
					// em.close();
					if (r > 0) {
						JOptionPane.showMessageDialog(null, "Factura stearsa cu succes");
						frame.dispose();
						MainFrame.run();
					} else {
						JOptionPane.showMessageDialog(null, "Eroare", "Eroare", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		// ************************************************************ Buton Plateste Factura
		butPlatesteFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (findCardDetails() == false)
					CardDetailsFrame.run();
				else
					JOptionPane.showMessageDialog(null, "Fisier text creat");

			}
		});
		
		// ************************************************************ Buton Download Factura
				butDownloadFactura.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						MainFrame.boDownloadFactura = true;
						boPlatesteFactura = false;
						boAdaugaFactura = false;
						
						Companies.run();

					}
				});

		// ***************************************************************************************** CAUTARE
		final TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(tabel.getModel());
		tabel.setRowSorter(rowSorter);
		textSearchFactura.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = textSearchFactura.getText();
				if (text.trim().length() == 0)
					rowSorter.setRowFilter(null);
				else
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				String text = textSearchFactura.getText();
				if (text.trim().length() == 0)
					rowSorter.setRowFilter(null);
				else
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

				String text = textSearchFactura.getText();
				if (text.trim().length() == 0)
					rowSorter.setRowFilter(null);
				else
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			}

		});
		// *********************************************END CAUTARE*********************************************************************
	}// end of Draw Gui

	// run function
	public static void run() {
		try {
			frame = new MainFrame();
			frame.setVisible(true);
			frame.pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Getter and Setter for AdaugaFactura option
	public static boolean boGetAdaugaFactura()
	{
		return boAdaugaFactura;
	}
	
	public static void vSetAdaugaFactura(boolean boValue)
	{
		boAdaugaFactura = boValue;
	}
	
	// Getter and Setter for DownloadFactura option
	public static boolean boGetDownloadFactura()
	{
		return boDownloadFactura;
	}
	
	public static void vSetDownloadFactura(boolean boValue)
	{
		boDownloadFactura = boValue;
	}
	
	// Getter and Setter for DownloadFactura option
	public static boolean boGetPlatesteFactura() 
	{
		return boPlatesteFactura;
	}

	public static void vSetPlatesteFactura(boolean boValue) 
	{
		boPlatesteFactura = boValue;
	}

	// get number of selected bill
	public static String getSelectedNrFactura() {
		int selIndex = tabel.getSelectedRow();
		return (String) tabel.getValueAt(selIndex, 0);
	}
}
