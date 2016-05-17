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
import javax.swing.JTable;
import javax.swing.JTextField;

public class LoginDetailsFrame extends JFrame {

	static LoginDetailsFrame frame;
	
	JLabel labelEmail,labelParola,labelParola2;
	JTextField textEmail, textParola,textParola2;
	JButton butOK,butSterge;
	
	// class constructor
	public LoginDetailsFrame()
	{
		super("Alege Compania");
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
		textParola = new JTextField(10);
		textParola2 = new JTextField(5);
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
				
				// check if parola == parola2
				
				PrintWriter writer = null;
				try {
					writer = new PrintWriter("loginOrange.txt", "UTF-8");
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
				
				if(MainFrame.findCardDetails() == true)
				{
					JOptionPane.showMessageDialog(null,"Date adaugate cu succes");
					frame.dispose();
				}
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
