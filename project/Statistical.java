package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class Statistical extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private static Client client;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JButton btnStatistical;
	private DefaultTableModel model;
	private JButton btnEnd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Statistical frame = new Statistical(client);
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
	public Statistical(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 621, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"2018", "2019", "2020"}));
		
		btnStatistical = new JButton("Th\u1ED1ng k\u00EA");
		btnStatistical.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JLabel lblDoanhThu = new JLabel("Doanh thu");
		
		btnEnd = new JButton("K\u1EBFt th\u00FAc");
		btnEnd.addActionListener(this);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(34)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblDoanhThu)
								.addComponent(btnStatistical, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnEnd, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(33)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(40)
							.addComponent(btnStatistical)
							.addGap(31)
							.addComponent(btnEnd))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDoanhThu))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ten nguoi mua", "Ten SP", "HSD", "SL", "Gia"
			}
		));
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
		model = (DefaultTableModel) table.getModel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(btnStatistical)){
			int doanhthu = 0;
			String month = (String)comboBox.getSelectedItem();
			String year = (String)comboBox_1.getSelectedItem();
			String nameofuser =null;
			String namesp = null;
			String HSD = null;
			String SL = null;
			String price = null;
			try {
				client.sendMSG("statistical");
				client.sendMSG(month);
				client.sendMSG(year);
				while(true){
					String title = client.getMSG();
					if(title.equals("continue")){
						nameofuser = client.getMSG();
						namesp = client.getMSG();
						HSD = client.getMSG();
						SL = client.getMSG();
						price = client.getMSG();
						int soluong = Integer.valueOf(SL);
						int gia = Integer.valueOf(price);
						doanhthu += soluong * gia;
						Vector vector = new Vector<>();
						vector.add(nameofuser);
						vector.add(namesp);
						vector.add(HSD);
						vector.add(SL);
						vector.add(price);
						model.addRow(vector);
					}
					if(title.equals("done")){
						textField.setText("");
						textField.setText(Integer.toString(doanhthu));
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(arg0.getSource().equals(btnEnd)){
			this.setVisible(false);
		}
	}
}
