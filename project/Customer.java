package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class Customer extends JFrame implements ActionListener{
	
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JButton btnShowsp;
	private JButton btnCharged;
	private JButton btnSignOut;
	private static Client client;
	private DefaultTableModel model;
	private DefaultTableModel model1;
	private JButton btnRemovesp;
	private JButton btnSelectsp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Customer frame = new Customer(client);
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
	public Customer(Client client) {
		this.client = client;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		btnShowsp = new JButton("ShowSP");
		btnShowsp.addActionListener(this);
		
		btnCharged = new JButton("Charged");
		btnCharged.addActionListener(this);
		
		btnSignOut = new JButton("Sign out");
		btnSignOut.addActionListener(this);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblNhpVoSp = new JLabel("Nh\u1EA5p v\u00E0o SP \u0111\u1EC3 ch\u1ECDn mua");
		
		btnRemovesp = new JButton("RemoveSP");
		btnRemovesp.addActionListener(this);
		
		btnSelectsp = new JButton("SelectSP");
		btnSelectsp.addActionListener(this);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnShowsp)
								.addComponent(btnSignOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnCharged, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSelectsp)
								.addComponent(btnRemovesp)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNhpVoSp)))
					.addPreferredGap(ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane_1, 0, 0, Short.MAX_VALUE)
							.addContainerGap())
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(23)
							.addComponent(btnShowsp)
							.addGap(18)
							.addComponent(lblNhpVoSp)
							.addGap(32)
							.addComponent(btnSelectsp)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnRemovesp)))
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnCharged)
							.addGap(18)
							.addComponent(btnSignOut)
							.addGap(19))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
		);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ten", "SL", "Price", "HSD"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane_1.setViewportView(table_1);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ten", "Gia", "SL", "HSD"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Integer.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
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
				client.sendMSG("showsp");
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
						Vector vector = new Vector<>();
						vector.add(name);
						vector.add(price);
						vector.add(SL);
						vector.add(HSD);
						model.addRow(vector);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(arg0.getSource().equals(btnSelectsp)){
			int xuathien = 0;
			int row = table.getSelectedRow();
			String nameSP = (String)table.getValueAt(row, 0);
			String price = (String)table.getValueAt(row, 1);
			String soluong = (String)table.getValueAt(row, 2);
			String HSD = (String)table.getValueAt(row, 3);
//			System.out.println(nameSP);
			int sl = Integer.parseInt(soluong);
			int r = table_1.getRowCount();
			int i = 0;
			while(i < r){
				String name = (String)table_1.getValueAt(i, 0);
				String hsd = (String)table_1.getValueAt(i, 3);
				System.out.println(name);
				if(name.equals(nameSP) && HSD.equals(hsd)){
					xuathien = 1;
					int SL = (int)table_1.getValueAt(i, 1);
					if(SL == sl){
						JOptionPane.showMessageDialog(null, "Mua it thoi het mat roi");
					}
					else {
						SL++;
						model1.removeRow(i);
						Vector vector = new Vector<>();
						vector.add(name);
						vector.add(SL);
						vector.add(price);
						vector.add(HSD);
						model1.addRow(vector);
					}
				}
				i++;
			}
			if(xuathien == 0){
				Vector vector = new Vector<>();
				vector.add(nameSP);
				vector.add(1);
				vector.add(price);
				vector.add(HSD);
				model1.addRow(vector);
			}
		}
		if(arg0.getSource().equals(btnRemovesp)){
			int row = table_1.getSelectedRow();
			String name = (String)table_1.getValueAt(row, 0);
			String price = (String)table_1.getValueAt(row, 2);
			int soluong = (int)table_1.getValueAt(row, 1);
			soluong--;
			model1.removeRow(row);
			if(soluong > 0){
				Vector vector = new Vector<>();
				vector.add(name);
				vector.add(soluong);
				vector.add(price);
				model1.addRow(vector);
			}
		}
		if(arg0.getSource().equals(btnCharged)){
			int money = 0;
			int row = table_1.getRowCount();
			int i = 0;
			try {
				client.sendMSG("charged");
				
				//gửi tên người mua tới server
				client.sendMSG(client.nameOfUser);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while(i < row){
				String nameSP = (String)table_1.getValueAt(i, 0);
				int sl = (int)table_1.getValueAt(i, 1);
				String priceSP = (String)table_1.getValueAt(i, 2);
				int price = Integer.valueOf(priceSP);
				String SL = Integer.toString(sl);
				String HSD = (String)table_1.getValueAt(i, 3);
				money += price * sl;
				i++;
				try {
					client.sendMSG("continue");
					client.sendMSG(nameSP);
					client.sendMSG(SL);
					client.sendMSG(HSD);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				client.sendMSG("done");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String data = null;
			try {
				data = client.getMSG();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(data.equals("done")){
				JOptionPane.showMessageDialog(null, "Tong thanh toan: " + money);
			}
			model1.setRowCount(0);
		}
		if(arg0.getSource().equals(btnSignOut)){
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
	}
}

