/**
 * Class for creating a new repair order - customer view
 * @author Nathan
 */

import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RepairOrderCreator extends JFrame {

	/**
	 * User fields
	 */

	User currentUser;

	/**
	 * Frame Object fields
	 */
	private JPanel contentPane;
	private JTextArea problemField;

	private JLabel charLabel;

	/**
	 * DB connection
	 */
	private static Connection con;

	/**
	 * Closes the feature window and returns to customer main window. Called after creating repair order or pressing back button
	 */
	public void goBack() {
		//return to employee main view
	}

	/**
	 * Create the frame.
	 */

	public RepairOrderCreator(Connection connection, User newUser) {

		//set user and connection
		currentUser = newUser;
		con = connection;

		//create application frame
		setTitle("Repair Order");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 370);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		JLabel featureLabel = new JLabel("Create a repair order");
		sl_contentPane.putConstraint(SpringLayout.NORTH, featureLabel, 16, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, featureLabel, 188, SpringLayout.WEST, contentPane);
		featureLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(featureLabel);
		
		JLabel timeLabel = new JLabel("Select a future date and time:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, timeLabel, 24, SpringLayout.SOUTH, featureLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, timeLabel, 46, SpringLayout.WEST, contentPane);
		timeLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(timeLabel);

		problemField = new JTextArea();
		problemField.setLineWrap(true);
		problemField.setWrapStyleWord(true);
		sl_contentPane.putConstraint(SpringLayout.WEST, problemField, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, problemField, 254, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, problemField, -10, SpringLayout.EAST, contentPane);
		problemField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// update char counter, set red if over limit
				int charNum = problemField.getText().length();
				charLabel.setText(charNum + " / 255");
				if (charNum > 255) {
					charLabel.setForeground(Color.RED);
				} else {
					charLabel.setForeground((Color.BLACK));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// update char counter
				int charNum = problemField.getText().length();
				charLabel.setText(charNum + " / 255");
				if (charNum > 255) {
					charLabel.setForeground(Color.RED);
				} else {
					charLabel.setForeground((Color.BLACK));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Attribute change (not applicable to JTextArea)
			}
		});
		contentPane.add(problemField);
		problemField.setColumns(10);

		JLabel problemLabel = new JLabel("Enter the problem below:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, problemField, 6, SpringLayout.SOUTH, problemLabel);
		sl_contentPane.putConstraint(SpringLayout.NORTH, problemLabel, 105, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, problemLabel, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, problemLabel, 119, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, problemLabel, 170, SpringLayout.WEST, contentPane);
		problemLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(problemLabel);

		charLabel = new JLabel("0 / 255");
		sl_contentPane.putConstraint(SpringLayout.NORTH, charLabel, 0, SpringLayout.NORTH, problemLabel);
		sl_contentPane.putConstraint(SpringLayout.EAST, charLabel, 0, SpringLayout.EAST, problemField);
		contentPane.add(charLabel);
		
		JButton backButton = new JButton("Back");
		sl_contentPane.putConstraint(SpringLayout.NORTH, backButton, 6, SpringLayout.SOUTH, problemField);
		sl_contentPane.putConstraint(SpringLayout.WEST, backButton, 0, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, backButton, -4, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, backButton, 243, SpringLayout.WEST, contentPane);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				goBack(); //return to customer LP
			}
		});
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(backButton);
		
		JButton createButton = new JButton("Create Repair Order");
		sl_contentPane.putConstraint(SpringLayout.NORTH, createButton, 6, SpringLayout.SOUTH, problemField);
		sl_contentPane.putConstraint(SpringLayout.WEST, createButton, 16, SpringLayout.EAST, backButton);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, createButton, -4, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, createButton, 0, SpringLayout.EAST, contentPane);
		
		DateTimePicker dateTimePicker = new DateTimePicker();
		sl_contentPane.putConstraint(SpringLayout.NORTH, dateTimePicker, -2, SpringLayout.NORTH, timeLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, dateTimePicker, 31, SpringLayout.EAST, timeLabel);
		contentPane.add(dateTimePicker);

		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//parse selected time/date and problem fields
				String selectedTime = dateTimePicker.toString();
				String problem = problemField.getText();

				selectedTime = selectedTime.replace("T"," ") + ":00";
				System.out.println(selectedTime);

				//ensure all fields were entered
				if (selectedTime.length() != 19) {
					System.out.println("All fields must be entered");
					JOptionPane.showMessageDialog(null, "All fields must be entered!");
					return;
				}
				if (selectedTime.endsWith("00:00:00")) {
					System.out.println("All fields must be entered");
					JOptionPane.showMessageDialog(null, "All fields must be entered!");
					return;
				}
				if (problem.isEmpty()) {
					System.out.println("All fields must be entered");
					JOptionPane.showMessageDialog(null, "All fields must be entered!");
					return;
				}

				//ensure problem text is within 255 char limit
				if (problem.length() > 255) {
					System.out.println("Problem message too long!");
					JOptionPane.showMessageDialog(null, "Problem message too long!");
					return;
				}

				//add repair order to database
				try {
					//query for max order_id and increment
					String query = "SELECT MAX(rep_order_id) FROM repair_orders";
					Statement statement = con.createStatement();
					ResultSet result = statement.executeQuery(query);


					//check for which rep_order_id to use
					int newOrderId = 0;
					while(result.next()) {
						newOrderId= result.getInt(1) + 1;
					}

					//insert repair order
					String insert = "INSERT INTO `c_cats`.`repair_orders` (`cust_id`, `rep_order_id`, `repair_time`, `problem`) VALUES ('"+currentUser.getUserID()+"', '" +
							newOrderId + "', '" + selectedTime + "', '" + problem + "');";
					statement = con.createStatement();
					statement.execute(insert);
					JOptionPane.showMessageDialog(null, "New repair order created!");

				} catch (Exception ex) {
				System.out.println("exception " + ex.getMessage());
			}
				goBack(); //return to customer LP
			}
		});
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		contentPane.add(createButton);
	}
}
