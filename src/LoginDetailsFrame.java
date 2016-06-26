import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.sikuli.script.FindFailed;

public class LoginDetailsFrame extends JFrame {

	static LoginDetailsFrame frame;
	
	JLabel labelEmail,labelParola,labelParola2;
	JTextField textEmail;
	JPasswordField textParola,textParola2;
	JButton butOK,butSterge;
	
	// class constructor
	public LoginDetailsFrame()
	{
		super("Introduceți datele de logare");
		drawGUI();
	}
	
	// ***************************************************** Draw GUI
	private void drawGUI()
	{
		// Properties for the frame
		this.setLayout(new GridLayout(0,2));
		this.setLocation(300, 300);
		this.setSize(300,100);
		
		// initialize the frame components
		labelEmail = new JLabel("Username / Email: ");
		labelParola =  new JLabel("Parola: ");
		labelParola2 = new JLabel("Reintroduceti parola:");
		textEmail = new JTextField(20);
		textParola = new JPasswordField(20);
		textParola2 = new JPasswordField(20);
		butOK = new JButton("Introduceti datele");
		butSterge = new JButton("Sterge");
		
		labelEmail.setAlignmentX(CENTER_ALIGNMENT);
		
		// add elements to frame
		this.add(labelEmail);
		this.add(textEmail);
		this.add(labelParola);
		this.add(textParola);
		this.add(labelParola2);
		this.add(textParola2);
		this.add(butOK);
		this.add(butSterge);
		
		//************************************************** button OK is pressed ***********************************************
		butOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// get texts from textfields
				String email = textEmail.getText().trim();
				String parola = textParola.getText().trim();
				String parola2 = textParola2.getText().trim();
				
				if(parola.equals(parola2) == false)
				{
					JOptionPane.showMessageDialog(null, "Primul camp pentru parola nu se potriveste cu al doilea", "Eroare", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
				// check if parola == parola2
				
				String company = Companies.selectedCompany;
				String filename ="./Details/login"+company+".txt";
				
				PrintWriter writer = null;
				try {
					writer = new PrintWriter(filename, "UTF-8");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				writer.println(email);
				writer.println(parola);
				writer.close();
				
				if(MainFrame.findLoginDetails(company) == true)
				{
					JOptionPane.showMessageDialog(null,"Date adaugate cu succes");
					frame.dispose();
					
					if(MainFrame.boGetDownloadFactura() == true)
					{
						// *** Orange ***
						if (company.equals("Orange")) 
						{
							try {
								DownloadBill.downloadOrange();
							} catch (AWTException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							MainFrame.resetFlags();
						}
						
						// *** E-ON ***
						if (company.equals("Eon")) 
						{
							try {
								DownloadBill.downloadEon();
							} catch (AWTException | FindFailed e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							MainFrame.resetFlags();
						}
					}
				}
				else JOptionPane.showMessageDialog(null,"Eroare adaugare date");
			}
		});
		//*************************************************************************************************************************
	}
	// ***************************************************** End of Draw GUI
	
	// ***************************************************** Run
		public static void run() {
			try {
				frame = new LoginDetailsFrame();
				frame.setVisible(true);
				frame.pack();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	// ***************************************************** End of Run
}
