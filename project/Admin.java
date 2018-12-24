package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Admin extends JFrame implements ActionListener{
	private ServerSocket listener;
	private static Client client;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JTable table_1;
	private JButton btnShowsp;
	private JButton btnShowuser;
	private JButton btnAdd;
	private JButton btnLogOut;
	private JButton btnDeleteuser;
	private JButton delSP;
	private JButton statistical;
	private DefaultTableModel model;
	private DefaultTableModel model1;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin(client);
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
	public Admin(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnShowsp = new JButton("ShowSP");
		btnShowsp.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		
		btnShowuser = new JButton("ShowUser");
		btnShowuser.addActionListener(this);
		
		JLabel lblAddsp = new JLabel("AddSP");
		
		JLabel lblNamesp = new JLabel("NameSP");
		
		JLabel lblPrice = new JLabel("Price");
		
		JLabel lblSl = new JLabel("SL");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		
		btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(this);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		btnDeleteuser = new JButton("DeleteUser");
		btnDeleteuser.addActionListener(this);
		
		JLabel lblHsd = new JLabel("HSD");
		
		comboBox = new JComboBox();
		comboBox.addActionListener(this);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(this);
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		
		comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(this);
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020"}));
		
		delSP = new JButton("Xóa SP");
		delSP.addActionListener(this);
		
		statistical = new JButton("Statistical\r\n");
		statistical.addActionListener(this);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPrice)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(44)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(btnShowuser)
										.addComponent(btnDeleteuser)))
								.addComponent(lblSl)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(62)
									.addComponent(lblAddsp))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnAdd)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblHsd)
											.addGap(18)
											.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(2)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnShowsp)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(delSP))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblNamesp)
											.addGap(18)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(textField)
												.addComponent(textField_1, 125, 125, Short.MAX_VALUE)
												.addComponent(textField_2))))))
							.addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(60)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(statistical)
								.addComponent(btnLogOut, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
							.addGap(67)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(delSP)
								.addComponent(btnShowsp))
							.addGap(18)
							.addComponent(btnShowuser)
							.addGap(18)
							.addComponent(btnDeleteuser)
							.addGap(20)
							.addComponent(lblAddsp)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNamesp))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPrice))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSl)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblHsd)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAdd)
							.addGap(18)
							.addComponent(statistical))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
					.addComponent(btnLogOut)
					.addContainerGap())
		);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ten", "S\u0110T", "Dia chi"
			}
		));
		scrollPane_1.setViewportView(table_1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ten", "Gia", "SL", "HSD"
			}
		));

		model = (DefaultTableModel) table.getModel();
		model1 = (DefaultTableModel) table_1.getModel();
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(btnShowsp)){
			model.setRowCount(0);
			try {
				client.sendMSG("showspAdmin");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String title = null;
			String name = null;
			String price;
			String SL;
			String HSD;
			while(true){
				try {
					title = client.getMSG();
					if(title.equals("done")){
						break;
					}
					if(title.equals("sp")){
						name = client.getMSG();
						price = client.getMSG();
						SL = client.getMSG();
						HSD = client.getMSG();
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date dateHSD = sdf.parse(HSD);
						Vector vector = new Vector<>();
						vector.add(name);
						vector.add(price);
						vector.add(SL);
						if(new Date().compareTo(dateHSD) > 0){
							vector.add("Đã hết hạn");
						}
						else {
							vector.add(HSD);
						}
						model.addRow(vector);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource().equals(btnShowuser)){
			model1.setRowCount(0);
			try {
				client.sendMSG("showuser");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String title = null;
			String name = null;
			String phone = null;
			String address = null;
			while(true){
				try {
					title = client.getMSG();
					if(title.equals("done")){
						break;
					}
					if(title.equals("user")){
						name = client.getMSG();
						phone = client.getMSG();
						address = client.getMSG();
						Vector vector = new Vector<>();
						vector.add(name);
						vector.add(phone);
						vector.add(address);
						model1.addRow(vector);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource().equals(btnDeleteuser)){
			int row = table_1.getSelectedRow();
			String username = (String)model1.getValueAt(row, 0);
			model1.removeRow(row);
			try {
				client.sendMSG("deluser");
				client.sendMSG(username);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(arg0.getSource().equals(btnAdd)){
			String nameSP = textField.getText();
			String priceSP = textField_1.getText();
			String SL = textField_2.getText();
			String date = comboBox.getSelectedItem().toString();
			String month = comboBox_1.getSelectedItem().toString();
			String year = comboBox_2.getSelectedItem().toString();
			String HSD = year + "-" + month + "-" + date;
			if(nameSP != null && priceSP != null && SL != null){
				if(checkInput(priceSP) && checkInput(SL)){
					try {
						client.sendMSG("addsp");
						client.sendMSG(nameSP);
						client.sendMSG(priceSP);
						client.sendMSG(SL);
						client.sendMSG(HSD);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "please check price and amount");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "please fill out SP");
			}
			try {
				String reply = client.getMSG();
				if(reply.equals("done")){
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
					JOptionPane.showMessageDialog(null, "Thêm thành công");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		if(arg0.getSource().equals(delSP)){
			int row = table.getSelectedRow();
			String nameSP = (String)model.getValueAt(row, 0);
			model.removeRow(row);
			try {
				client.sendMSG("delsp");
				client.sendMSG(nameSP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(arg0.getSource().equals(btnLogOut)){
			try {
				client.sendMSG("close");
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				client.close();
				this.setVisible(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(arg0.getSource().equals(statistical)){
			new Statistical(client).setVisible(true);
		}
	}
	public boolean checkInput(String string){
		for(int i = 0; i < string.length(); i++){
			int num = string.charAt(i);
			if(num < 48 && num != 10){
				return false;
			}
			if(num > 57 && num != 10){
				return false;
			}
		}
		return true;
	}
}
