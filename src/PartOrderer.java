import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PartOrderer extends JFrame {

	/**
	 * User field
	 */
	//private User currentUser;
	private User currentUser = new User("M00004","ksam","password","Kate", "Samuleson", 1);
	//private User currentUser = new User("E00001","nmill","password","Nathan ", "Miller", 1);

	/**
	 * Frame Object fields
	 */
	private JPanel contentPane;
	private JTextField qtyField;

	JTextArea textArea;

	/**
	 * DB connection info
	 */
	private static String url = "jdbc:mysql://localhost:3306/c_cats";
	private static String userName = "root"; //root should work too
	private static String pass = "cs380";
	private static Connection con;

	/**
	 * updates ordering qty from part_id in database
	 * @param partID the part to modify inventory
	 * @param qty the qty to add or subtract from pending_qty
	 * @param operation adds if true, subtracts if false;
	 */
	public void updateQty(int partID, int qty, boolean operation) {
		try {
			String update;
			if (operation) {
				update = "UPDATE part_inventory SET pending_qty = pending_qty + " + qty + " WHERE part_id = " + partID;
			} else {
				update = "UPDATE part_inventory SET pending_qty = CASE WHEN (pending_qty - " + qty + " < 0) THEN 0 ELSE pending_qty - " + qty + " END WHERE part_id = " + partID;
			}
			Statement statement = con.createStatement();
			statement.execute(update);

		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}
	}

	/**
	 * Closes the feature window and returns to employee main window. Called after pressing back button
	 */
	public void goBack() {
		//return to employee main view
	}

	/**
	 * Function to update inventory textArea
	 */
	public void updateInventory() {
		textArea.setText("");

		try {
			//create query to get all parts in stock of given category and execute
			String query = "SELECT * FROM part p JOIN part_inventory pi ON p.part_id = pi.part_id;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);

			//iterate through ResultSet outputting parts and their inventory status
			while(result.next()) {
				String output = "";
				output += " " + result.getString(2) +  " / " + result.getInt(7) +  " / " + result.getInt(8) + "   \n";
				textArea.append(output);
			}
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}

	}

	/**
	 *
	 * @return partList the list of all parts in system
	 */
	public ArrayList<Part> getParts() {
		//create part list
		ArrayList<Part> partList = new ArrayList<Part>();

		try {
			//create query to get all parts in stock of given category and execute
			String query = "SELECT * FROM part p JOIN part_inventory pi ON p.part_id = pi.part_id;";
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(query);

			//iterate through ResultSet making parts
			while(result.next()) {
				//create a part
				Part newPart;
				newPart =  new Part(result.getInt(1),result.getString(2),result.getString(3),result.getDouble(5));
				partList.add(newPart);
			}
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}
		return partList;
	}

	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartOrderer frame = new PartOrderer();
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
	public PartOrderer() {
		setTitle("Order Parts");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 286);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(247, 31, 220, 196);
		textArea.setEditable(false);

		// Create a JScrollPane and set the scroll mode
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(247, 31, 220, 196);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane);
		
		JLabel featureLabel = new JLabel("Order Parts");
		featureLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		featureLabel.setBounds(81, 6, 95, 27);
		contentPane.add(featureLabel);
		
		JComboBox partsList = new JComboBox();
		partsList.setBounds(93, 44, 120, 22);
		contentPane.add(partsList);
		
		qtyField = new JTextField();
		qtyField.setBounds(170, 73, 43, 20);
		contentPane.add(qtyField);
		qtyField.setColumns(10);
		
		JLabel invLabel = new JLabel("Inventory: Part Name / Current qty / Pending qty");
		invLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		invLabel.setBounds(230, 10, 250, 14);
		contentPane.add(invLabel);
		
		JLabel partLabel = new JLabel("Select Part:");
		partLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		partLabel.setBounds(10, 47, 73, 14);
		contentPane.add(partLabel);
		
		JLabel qtyLabel = new JLabel("Enter Quantity:");
		qtyLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		qtyLabel.setBounds(10, 76, 100, 14);
		contentPane.add(qtyLabel);
		
		JButton addButton = new JButton("Add to order");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//check all fields are filled
				if (partsList.getSelectedIndex() < 1 || qtyField.getText().isEmpty()) {
					System.out.println("Fields not all entered!");
					JOptionPane.showMessageDialog(null, "All fields not entered.");
					return;
				}

				//check that qty is an int
				try {
					Integer.parseInt(qtyField.getText());

				}catch (NumberFormatException ex) {
					System.out.println("Qty entered must be a whole number!");
					JOptionPane.showMessageDialog(null, "All fields not entered.");
				}
				//call updateQty with true and add to pending, update inventory list

				updateQty(((Part)partsList.getSelectedItem()).getId(),Integer.parseInt(qtyField.getText()),true);
				JOptionPane.showMessageDialog(null, "Order updated!");
				updateInventory();

			}
		});
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		addButton.setBounds(10, 100, 227, 23);
		contentPane.add(addButton);
		
		JButton removeButton = new JButton("Remove from order");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check all fields are filled
				if (partsList.getSelectedIndex() < 1 || qtyField.getText().isEmpty()) {
					System.out.println("Fields not all entered!");
					JOptionPane.showMessageDialog(null, "All fields not entered.");
					return;
				}

				//check that qty is an int
				try {
					Integer.parseInt(qtyField.getText());

				}catch (NumberFormatException ex) {
					System.out.println("Qty entered must be a whole number!");
					JOptionPane.showMessageDialog(null, "All fields not entered.");
				}
				//call updateQty with false and subtract from pending, update inventory list
				updateQty(((Part)partsList.getSelectedItem()).getId(),Integer.parseInt(qtyField.getText()),false);
				JOptionPane.showMessageDialog(null, "Order updated!");
				updateInventory();

			}
		});
		removeButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		removeButton.setBounds(10, 133, 227, 23);
		contentPane.add(removeButton);
		
		JButton approveButton = new JButton("Approve order");
		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//query all parts inventory
				try {
					String query = "SELECT * FROM part_inventory";
					Statement statement = con.createStatement();
					ResultSet result = statement.executeQuery(query);

					while (result.next()) {
						//update qty on hand if pending is > 0
						int pendingQty = result.getInt(3);
						if (pendingQty > 0) {
							int currentPart = result.getInt(1);
							String update = "UPDATE `c_cats`.`part_inventory` SET `qty` = qty + pending_qty, `pending_qty` = 0 WHERE (`part_id` = '" + currentPart + "');";
							statement = con.createStatement();
							statement.execute(update);
						} else {
							continue;
						}
					}
					//success message, update inventory
					updateInventory();
					JOptionPane.showMessageDialog(null, "Approved Pending Order!");


				} catch (Exception exc) {
					System.out.println("exception " + exc.getMessage());
				}
			}
		});
		approveButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		approveButton.setBounds(10, 167, 227, 23);
		contentPane.add(approveButton);
		
		JButton backbutton = new JButton("Back");
		backbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		backbutton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backbutton.setBounds(71, 194, 115, 42);
		contentPane.add(backbutton);


		//add set current user
		//User currentUser = newUser;

		//if not a mgr, set approve button to not visible
		if (!(currentUser.getUserID().charAt(0) == 'M')) {
			approveButton.setVisible(false);
		}

		//create connection
		try {
			con = DriverManager.getConnection(url,userName,pass);
			System.out.println("connected");
		} catch (Exception e ) {
			System.out.println("exception " + e.getMessage());
			return; //exit program if connection fails
		}

		//get all parts in system and add to combo box
		ArrayList<Part> parts = getParts();

		partsList.insertItemAt("", 0);
		for (Part aPart : parts) {
			partsList.addItem(aPart);
		}

		//initially update inventory console
		updateInventory();





	}
}
