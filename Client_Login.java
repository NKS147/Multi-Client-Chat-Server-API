package scpbb;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Loginn extends JFrame {

	private JPanel contentPane;
	private JTextField txtname;
	private JTextField txtipadd;
	private JTextField txtport;

	/**
	 * Launch the application.
	 */
	
	public Loginn() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(290,420);
		setLocationRelativeTo(null);
		//setBounds(100, 100, 290, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtname = new JTextField();
		txtname.setBounds(57, 85, 169, 26);
		contentPane.add(txtname);
		txtname.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(114, 60, 46, 14);
		contentPane.add(lblName);
		
		JLabel lblIpadd = new JLabel("Ipadd: ");
		lblIpadd.setBounds(119, 154, 46, 14);
		contentPane.add(lblIpadd);
		
		txtipadd = new JTextField();
		txtipadd.setColumns(10);
		txtipadd.setBounds(57, 179, 169, 26);
		contentPane.add(txtipadd);
		
		JLabel lblPort = new JLabel("Port: ");
		lblPort.setBounds(122, 233, 40, 20);
		contentPane.add(lblPort);
		
		txtport = new JTextField();
		txtport.setColumns(10);
		txtport.setBounds(57, 264, 169, 26);
		contentPane.add(txtport);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String a,b;
				int c;
				a=txtname.getText();
				b=txtipadd.getText();
				c=Integer.parseInt(txtport.getText());
				
				dispose();
				try {
					new Client(a,b,c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("Login: "+a+b+c);
			}
		});
		btnLogin.setBounds(97, 342, 89, 23);
		contentPane.add(btnLogin);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loginn frame = new Loginn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
