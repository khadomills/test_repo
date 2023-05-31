/**
 * Class for creating a new part - employee view
 * @author Nathan
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class PartCreator extends JFrame {

	/**
	 * Frame Object fields
	 */
	private JPanel contentPane;
	private JTextField partNameField;
	private JTextField partDescField;
	private JTextField priceField;

	/**
	 * DB connection info
	 */
	private static String url = "jdbc:mysql://localhost:3306/c_cats";
	private static String userName = "root"; //root should work too
	private static String pass = "cs380";
	private static Connection con;

	/**
	 * Closes the feature window and returns to employee main window. Called after creating Part or pressing back button
	 */
	public void goBack() {
		//return to employee main view
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartCreator frame = new PartCreator();
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
	public PartCreator() {
		setTitle("Part Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel partDisclaimer = new JLabel("Create a new part.");
		partDisclaimer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		partDisclaimer.setBounds(162, 236, 225, 14);
		contentPane.add(partDisclaimer);
		
		JLabel featureLabel = new JLabel("Part Creator");
		featureLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		featureLabel.setBounds(182, 11, 225, 14);
		contentPane.add(featureLabel);
		
		partNameField = new JTextField();
		partNameField.setBounds(133, 45, 274, 20);
		contentPane.add(partNameField);
		partNameField.setColumns(10);
		
		JLabel partNameLabel = new JLabel("Part name:");
		partNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		partNameLabel.setBounds(35, 48, 225, 14);
		contentPane.add(partNameLabel);
		
		JLabel partDescLabel = new JLabel("Part desc:");
		partDescLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		partDescLabel.setBounds(35, 79, 225, 14);
		contentPane.add(partDescLabel);
		
		partDescField = new JTextField();
		partDescField.setColumns(10);
		partDescField.setBounds(133, 76, 274, 20);
		contentPane.add(partDescField);
		
		JLabel partCatLabel = new JLabel("Part category:");
		partCatLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		partCatLabel.setBounds(35, 107, 225, 14);
		contentPane.add(partCatLabel);
		
		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(133, 135, 274, 20);
		contentPane.add(priceField);
		
		JLabel partPriceLabel = new JLabel("Part price:");
		partPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		partPriceLabel.setBounds(35, 138, 225, 14);
		contentPane.add(partPriceLabel);

		//add part categories to combobox
		String[] categories = {"power_supply", "motherboard", "gpu", "cpu", "ram", "storage", "case"};
		JComboBox catList = new JComboBox(categories);
		catList.setBounds(133, 104, 274, 22);
		contentPane.add(catList);
		
		JButton createButton = new JButton("Create Part");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//ensure all fields are filled
				if (partNameField.getText().isEmpty() || partDescField.getText().isEmpty() || priceField.getText().isEmpty()) {
					System.out.println("All fields not entered.");
					JOptionPane.showMessageDialog(null, "All fields not entered.");
					return;
				}

				//ensure price entered is double and only has two decimal places
				String priceInput = priceField.getText();

				// Regular expression pattern to match a double with exactly 2 decimal places
				String pattern = "\\d+\\.\\d{2}";

				if (priceInput.matches(pattern)) {
					try {
						double price = Double.parseDouble(priceInput);
					} catch (NumberFormatException x) {
						System.out.println("Price entered isn't valid");
						JOptionPane.showMessageDialog(null, "Price entered isn't valid.");
						return;
					}
				} else {
					System.out.println("Price entered isn't valid.");
					JOptionPane.showMessageDialog(null, "Price entered isn't valid.");
					return;
				}

				//add part to part table and part_inv tables

				try {
					String query = "SELECT MAX(part_id) FROM part";
					Statement statement = con.createStatement();
					ResultSet result = statement.executeQuery(query);
					int newPartID = 0;

					//check for part_id to use (max + 1)
					while(result.next()) {
						newPartID = result.getInt(1) + 1;
					}

					//add new part to part table
					String insert = "INSERT INTO `c_cats`.`part` (`part_id`, `part_name`, `part_desc`, `part_cat`, `part_price`) VALUES ('" +
							newPartID + "', '"+ partNameField.getText() +"', '" + partDescField.getText() +"', '" +
							(String) catList.getSelectedItem() + "', '" + priceField.getText() + "');";
					Statement insertStatement = con.createStatement();
					insertStatement.execute(insert);

					//add new part to part_inventory table with qty 0
					insert = "INSERT INTO `c_cats`.`part_inventory` (`part_id`, `qty`, `pending_qty`) VALUES ('" + newPartID + "', '0', '0');";
					insertStatement = con.createStatement();
					insertStatement.execute(insert);

					JOptionPane.showMessageDialog(null, "New Part created!");
					goBack();

				} catch (Exception ex) {
					System.out.println("exception " + ex.getMessage());
				}
			}
		});
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		createButton.setBounds(223, 181, 184, 44);
		contentPane.add(createButton);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack();
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		backButton.setBounds(29, 181, 184, 44);
		contentPane.add(backButton);

		//create connection
		try {
			con = DriverManager.getConnection(url,userName,pass);
			System.out.println("connected");
		} catch (Exception e ) {
			System.out.println("exception " + e.getMessage());
			return; //exit program if connection fails
		}
	}
}
