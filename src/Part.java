/**
 * Class for individual parts
 * @author Nathan
 */
public class Part {
    /**
     * Part fields
     */
    private int part_id;
    private String part_name;
    private String part_desc;
    private double part_price;

    /**
     * Function that returns part_id
     * @return part_id integer (identifier for DB)
     */
    public int getId() {
        return part_id;
    }

    /**
     * Function that returns part_name
     * @return part_name String name of the part
     */
    public String getName() {
        return part_name;
    }

    /**
     * Function that returns part_desc
     * @return part_desc String description of the part
     */
    public String getDesc() {
        return part_desc;
    }

    /**
     * Function that returns part_price
     * @return part_price double listed price of part
     */
    public double getPrice() {
        return part_price;
    }

    /**
     * Function to update part_id
     * @param id integer value to update part id with
     */
    public void setId(int id) {
        part_id = id;
    }

    /**
     * Function to update part_name
     * @param name String value to update part name with
     */
    public void setName(String name) {
        part_name = name;
    }

    /**
     * Function to update part_desc
     * @param desc String value to update part description with
     */
    public void setDesc(String desc) {
        part_desc = desc;
    }

    /**
     * Function to update part_price
     * @param price double value to update part price with
     */
    public void setPrice(double price) {
        part_price = price;
    }

    @Override
    public String toString() {
        return part_name;
    }

    /**
     * Part Constructor function
     * @param id integer value for part_id
     * @param name String value for part_name
     * @param desc String value for part_desc
     */
    public Part(int id, String name, String desc, double price) {
        //initialize fields
        part_id = id;
        part_name = name;
        part_desc = desc;
        part_price = price;
    }
}
