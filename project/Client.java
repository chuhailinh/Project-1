package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionEvent;

public class Client extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnCreateAccount;
	private Socket socketOfClient;
	static final String ip = "localhost";	
	private DataInputStream is;
	private DataOutputStream os;
	public String nameOfUser;
	
	/**
	 * Launch the application.
	 */
	public void startClient() throws UnknownHostException, IOException{
		socketOfClient = new Socket(ip, 1112);
		is = new DataInputStream(socketOfClient.getInputStream());
		os = new DataOutputStream(socketOfClient.getOutputStream());
	}
	
	public void close() throws IOException{
		is.close();
		os.close();
		socketOfClient.close();
	}
	
	public void sendMSG(String data) throws IOException{
		os.writeUTF(data);
		os.flush();
	}
	
	public void sendAccount(String username, String password, String phone, String address) throws IOException{
		os.writeUTF(username);
		os.flush();
		os.writeUTF(password);
		os.flush();
		os.writeUTF(phone);
		os.flush();
		os.writeUTF(address);
		os.flush();
	}
	
	public String getMSG() throws IOException{
		String data = is.readUTF();
		return data;
	}
	
	public void sendRequestLogin(String name, String password) throws IOException{
		os.writeUTF(name);
		os.flush();
		os.writeUTF(password);
		os.flush();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.startClient();
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
	public Client() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblPassword = new JLabel("Password");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		
		btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(this);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(59)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUsername)
								.addComponent(lblPassword))
							.addGap(45)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(passwordField)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
								.addComponent(btnCreateAccount)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(178)
							.addComponent(btnLogin)))
					.addContainerGap(161, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(54)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblUsername)
							.addGap(39)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPassword)
								.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(btnLogin)
					.addGap(33)
					.addComponent(btnCreateAccount)
					.addGap(19))
		);
		contentPane.setLayout(gl_contentPane);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(btnLogin)){
			String name = null;
			String title = null;
			String password = null;
			try {
				title = "login";
				name = textField.getText();
				password = passwordField.getText();
				sendMSG(title);
				sendRequestLogin(name, password);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				String check = getMSG();
				if(check.equals("client")){
					nameOfUser = name;
					JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
					this.setVisible(false);
					new Customer(this).setVisible(true);
				}
				if(check.equals("failed")){
					JOptionPane.showMessageDialog(null, "please check your username and password");
				}
				if(check.equals("admin")){
					JOptionPane.showMessageDialog(null, "Admin đăng nhập");
					this.setVisible(false);
					new Admin(this).setVisible(true);
				}
			} catch (HeadlessException | IOException e) {
				e.printStackTrace();
			}
		}
		else if(arg0.getSource().equals(btnCreateAccount)){
			this.setVisible(false);
			new CreateAccount(this).setVisible(true);
		}
	}
}

