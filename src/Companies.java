import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Companies extends JFrame {

	static Companies frame;
	
	JButton butOrange,butEon,butDigi,butEnel;
	
	static String selectedCompany = null;
	
	public Companies()
	{
		super("Alege companie");
		drawGUI();
	}
	
	// *************************************************************** draw GUI *********************************************************
	private void drawGUI()
	{
		// properties for the frame
		this.setLayout(new GridLayout(0,2));
		this.setLocation(500,300);
		
		// load the Orange Image
		ImageIcon imageIcon = new ImageIcon("./Logos/orange.png"); 
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create Orange button
		butOrange = new JButton();
		butOrange.setIcon(imageIcon);
		butOrange.setPreferredSize(new Dimension(120,120));
		
		// load the Eon Image
		imageIcon = new ImageIcon("./Logos/eon.gif");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create Eon button
		butEon = new JButton();
		butEon.setIcon(imageIcon);
		butEon.setPreferredSize(new Dimension(120,120));
		
		// load the DIGI Image
		imageIcon = new ImageIcon("./Logos/digi.png");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create DIGI button
		butDigi = new JButton();
		butDigi.setIcon(imageIcon);
		butDigi.setPreferredSize(new Dimension(120,120));

		// load the ENEL Image
		imageIcon = new ImageIcon("./Logos/enel.gif");
		image = imageIcon.getImage();
		newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imageIcon = new ImageIcon(newimg); // transform it back
		// create ENEL button
		butEnel = new JButton();
		butEnel.setIcon(imageIcon);
		butEnel.setPreferredSize(new Dimension(120, 120));
		
		// ************* button ORANGE is clicked *************************************
		butOrange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				selectedCompany = "Orange";
				
				// option "Add Bill" is selected
				if(MainFrame.boGetAdaugaFactura() == true)
				{
					frame.dispose();
					JFileChooser fc = new JFileChooser();
					fc.setCurrentDirectory(new File("./Facturi"));
					int result = fc.showOpenDialog(fc);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fc.getSelectedFile();
						String path = selectedFile.getAbsolutePath();
						TextParser.extractTextOrange(path);
					}
					
					MainFrame.vSetAdaugaFactura(false);
				}
				
				// option "Download bill" is selected
				if(MainFrame.boGetDownloadFactura() == true)
				{
					frame.dispose();
					if(MainFrame.findLoginDetails(selectedCompany) == true)
					{
						try {
							DownloadBill.downloadOrange();
						} catch (AWTException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						LoginDetailsFrame.run();
					}
				}
			}
		});
		//******************************************************************************		
		
		//add components to frame
		this.add(butOrange);
		this.add(butEon);
		this.add(butDigi);
		this.add(butEnel);
	}
	// **********************************************************************************************************************************
	
	
	// ************ get selected company ****************************
	private String getSelectedCompany()
	{
		return this.selectedCompany;
	}
	// ****************************************************************
	
	// ***************************************************** Run ************************************************************************
			public static void run() {
				try {
					frame = new Companies();
					frame.setVisible(true);
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	// ********************************************************************************************************************************
}
