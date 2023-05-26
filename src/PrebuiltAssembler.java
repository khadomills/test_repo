/**
 * Class for creating prebuilts from in stock parts - employee view
 * @author Nathan
 */

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.ArrayList;

public class PrebuiltAssembler extends JFrame {

	/**
	 * Frame Object fields
	 */
	private JPanel contentPane;
	private JTextField compNameField;
	private JTextField pricefield;

	/**
	 * DB connection info
	 */
	private static String url = "jdbc:mysql://localhost:3306/c_cats";
	private static String userName = "root"; //root should work too
	private static String pass = "cs380";
	private static Connection con;



	/**
	 * deducts 1 qty from part_id in database
	 * @param partID the part to deduct inventory
	 */
	public void deductQty(int partID) {
		try {
			String update = "UPDATE part_inventory SET qty = qty - 1 WHERE part_id = " + partID;
			Statement statement = con.createStatement();
			statement.execute(update);
		} catch (Exception e) {
			System.out.println("exception " + e.getMessage());
		}
	}

	/**
	 *
	 * @param part_cat category of parts to check stock and add to list
	 * @return partList the list of parts in stock for a given category
	 */
	public ArrayList<Part> getParts(String part_cat) {
		//create part list
		ArrayList<Part> partList = new ArrayList<Part>();

		try {
			//create query to get all parts in stock of given category and execute
			String query = "SELECT * FROM part p JOIN part_inventory pi ON p.part_id = pi.part_id WHERE p.part_cat = '" + part_cat + "' AND pi.qty > 0;";
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
	 * Closes the feature window and returns to employee main window. Called after creating PC or pressing back button
	 */
	public void goBack() {
		//return to employee main view
	}



	/**
	 * Create the frame.
	 */
	public PrebuiltAssembler() {
		
		//initialize Jframe objects
		setTitle("Prebuilt Computer Assembler");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 473, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel featureLabel = new JLabel("Prebuilt Computer Assembler");
		featureLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		featureLabel.setBounds(117, 11, 262, 24);
		contentPane.add(featureLabel);
		
		compNameField = new JTextField();
		compNameField.setBounds(213, 50, 152, 20);
		contentPane.add(compNameField);
		compNameField.setColumns(10);
		
		JLabel nameLabel = new JLabel("Enter prebuilt name:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameLabel.setBounds(46, 50, 157, 14);
		contentPane.add(nameLabel);
		
		JComboBox mbList = new JComboBox();
		mbList.setBounds(213, 104, 152, 22);
		contentPane.add(mbList);
		
		JLabel mbLabel = new JLabel("Select a motherboard:");
		mbLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		mbLabel.setBounds(46, 103, 157, 18);
		contentPane.add(mbLabel);
		
		JComboBox psList = new JComboBox();
		psList.setBounds(213, 137, 152, 22);
		contentPane.add(psList);
		
		JLabel psLabel = new JLabel("Select a power suppy:");
		psLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		psLabel.setBounds(46, 136, 157, 18);
		contentPane.add(psLabel);
		
		JLabel gpuLabel = new JLabel("Select a gpu:");
		gpuLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		gpuLabel.setBounds(46, 169, 130, 18);
		contentPane.add(gpuLabel);
		
		JComboBox gpuList = new JComboBox();
		gpuList.setBounds(213, 170, 152, 22);
		contentPane.add(gpuList);
		
		JLabel cpuLabel = new JLabel("Select a cpu:");
		cpuLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cpuLabel.setBounds(46, 198, 130, 18);
		contentPane.add(cpuLabel);
		
		JComboBox cpuList = new JComboBox();
		cpuList.setBounds(213, 200, 152, 22);
		contentPane.add(cpuList);
		
		JLabel ramLabel = new JLabel("Select a ram kit:\r\n");
		ramLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		ramLabel.setBounds(46, 228, 130, 18);
		contentPane.add(ramLabel);
		
		JComboBox ramList = new JComboBox();
		ramList.setBounds(213, 230, 152, 22);
		contentPane.add(ramList);
		
		JLabel storageLabel = new JLabel("Select a storage option:");
		storageLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		storageLabel.setBounds(46, 258, 157, 18);
		contentPane.add(storageLabel);
		
		JComboBox storageList = new JComboBox();
		storageList.setBounds(213, 260, 152, 22);
		contentPane.add(storageList);
		
		JLabel caseLabel = new JLabel("Select a case:\r\n");
		caseLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		caseLabel.setBounds(46, 287, 130, 18);
		contentPane.add(caseLabel);
		
		JComboBox caseList = new JComboBox();
		caseList.setBounds(213, 289, 152, 22);
		contentPane.add(caseList);
		
		JButton createButton = new JButton("Create Computer");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//check all fields are filled
				if (compNameField.getText().isEmpty() || pricefield.getText().isEmpty() || mbList.getSelectedIndex() < 1
				|| psList.getSelectedIndex() < 1 || gpuList.getSelectedIndex() < 1 || cpuList.getSelectedIndex() < 1
				|| ramList.getSelectedIndex() < 1 || storageList.getSelectedIndex() < 1 || caseList.getSelectedIndex() < 1) {
					System.out.println("All fields not entered.");
					JOptionPane.showMessageDialog(null, "All fields not entered.");
					return;
				}

				//ensure price entered is double and only has two decimal places
				String priceInput = pricefield.getText();

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

				//create a prebuilt from existing parts
				Prebuilt newComp = new Prebuilt(compNameField.getText(),(Part) mbList.getSelectedItem(),(Part) gpuList.getSelectedItem(),(Part) psList.getSelectedItem()
						,(Part) cpuList.getSelectedItem(),(Part) ramList.getSelectedItem(),(Part) caseList.getSelectedItem(),(Part) storageList.getSelectedItem(),Double.parseDouble(pricefield.getText()));

				//add prebuilt to database
				try {
					String insert = "INSERT INTO `c_cats`.`prebuilt` (`comp_name`, `motherboard_id`, `ps_id`, `gpu_id`, `cpu_id`, `ram_id`, `storage_id`, `case_id`, `price`) VALUES " +
							"('" + newComp.getComp_name() + "', '" + newComp.getMotherboard().getId() + "'," +
							" '" + newComp.getPower_supply().getId() + "', '" + newComp.getGpu().getId() + "'," +
							" '" + newComp.getCpu().getId() + "', '" + newComp.getRam().getId()+ "'," +
							" '" + newComp.getStorage().getId() + "', '" + newComp.getaCase().getId() + "', '" + newComp.getPrice()+ "')";
					Statement statement = con.createStatement();
					statement.execute(insert);

					//remove 1 qty of each part if successful
					deductQty(newComp.getMotherboard().getId());
					deductQty(newComp.getGpu().getId());
					deductQty(newComp.getPower_supply().getId());
					deductQty(newComp.getCpu().getId());
					deductQty(newComp.getRam().getId());
					deductQty(newComp.getaCase().getId());
					deductQty(newComp.getStorage().getId());
					JOptionPane.showMessageDialog(null, "Prebuilt Created!");
					goBack();



				} catch (Exception ex) {
					System.out.println("exception " + ex.getMessage());
					// Display the dialog box
					JOptionPane.showMessageDialog(null, "Prebuilt with same name already exists");
					return;
				}
			}
		});
		createButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		createButton.setBounds(195, 322, 184, 44);
		contentPane.add(createButton);
		
		JLabel disclaimerLabel = new JLabel("Parts will be removed from inventory and a prebuilt computer added.");
		disclaimerLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		disclaimerLabel.setBounds(34, 373, 423, 14);
		contentPane.add(disclaimerLabel);
		
		pricefield = new JTextField();
		pricefield.setColumns(10);
		pricefield.setBounds(213, 77, 152, 20);
		contentPane.add(pricefield);
		
		JLabel priceLabel = new JLabel("Enter prebuilt price:");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		priceLabel.setBounds(46, 77, 157, 14);
		contentPane.add(priceLabel);
		
		JButton returnButton = new JButton("Back");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//return to employee LP
				goBack();
			}
		});
		returnButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		returnButton.setBounds(45, 330, 85, 24);
		contentPane.add(returnButton);

		//create connection
		try {
			con = DriverManager.getConnection(url,userName,pass);
			System.out.println("connected");
		} catch (Exception e ) {
			System.out.println("exception " + e.getMessage());
			return; //exit program if connection fails
		}
		
		//pull parts from database if in stock and add to part lists
		ArrayList<Part> mbAList = getParts("motherboard");
		ArrayList<Part> psAList = getParts("power_supply");
		ArrayList<Part> gpuAList = getParts("gpu");
		ArrayList<Part> cpuAList = getParts("cpu");
		ArrayList<Part> ramAList = getParts("ram");
		ArrayList<Part> storageAList = getParts("storage");
		ArrayList<Part> caseAList = getParts("case");

		//add parts to drop down lists, with an empty starting entry

		mbList.insertItemAt("", 0);
		for (Part aPart : mbAList) {
			mbList.addItem(aPart);
		}

		psList.insertItemAt("", 0);
		for (Part aPart : psAList) {
			psList.addItem(aPart);
		}

		gpuList.insertItemAt("", 0);
		for (Part aPart : gpuAList) {
			gpuList.addItem(aPart);
		}

		cpuList.insertItemAt("", 0);
		for (Part aPart : cpuAList) {
			cpuList.addItem(aPart);
		}

		ramList.insertItemAt("", 0);
		for (Part aPart : ramAList) {
			ramList.addItem(aPart);
		}

		storageList.insertItemAt("", 0);
		for (Part aPart : storageAList) {
			storageList.addItem(aPart);
		}

		caseList.insertItemAt("", 0);
		for (Part aPart : caseAList) {
			caseList.addItem(aPart);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrebuiltAssembler frame = new PrebuiltAssembler();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
}
