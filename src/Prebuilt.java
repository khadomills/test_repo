/**
 * Class for prebuilt computers
 * @author Nathan
 */
public class Prebuilt {

    /**
     * Prebuilt fields
     */
    private String comp_name;
    private Part motherboard;
    private Part gpu;
    private Part power_supply;
    private Part cpu;
    private Part ram;
    private Part aCase;
    private Part storage;
    private double price;

    /**
     * Function to return computer case
     * @return Part aCase
     */
    public Part getaCase() {
        return aCase;
    }

    /**
     * Function to return computer cpu
     * @return Part cpu
     */
    public Part getCpu() {
        return cpu;
    }

    /**
     * Function to return computer gpu
     * @return Part gpu
     */
    public Part getGpu() {
        return gpu;
    }

    /**
     * Function to return computer motherboard
     * @return Part motherboard
     */
    public Part getMotherboard() {
        return motherboard;
    }

    /**
     * Function to return computer power supply
     * @return Part power_supply
     */
    public Part getPower_supply() {
        return power_supply;
    }

    /**
     * Function to return computer ram
     * @return Part ram
     */
    public Part getRam() {
        return ram;
    }

    /**
     * Function to return computer storage
     * @return Part storage
     */
    public Part getStorage() {
        return storage;
    }

    /**
     * Function to return computer name
     * @return String comp_name
     */
    public String getComp_name() {
        return comp_name;
    }

    /**
     * Function to return computer price
     * @return double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Function to set computer case Part
     * @param aCase Part case to be used
     */
    public void setaCase(Part aCase) {
        this.aCase = aCase;
    }

    /**
     * Function to set computer name
     * @param comp_name Part case to be used
     */
    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    /**
     * Function to set computer cpu Part
     * @param cpu Part cpu to be used
     */
    public void setCpu(Part cpu) {
        this.cpu = cpu;
    }

    /**
     * Function to set computer gpu Part
     * @param gpu Part gpu to be used
     */
    public void setGpu(Part gpu) {
        this.gpu = gpu;
    }

    /**
     * Function to set computer motherboard Part
     * @param motherboard Part motherboard to be used
     */
    public void setMotherboard(Part motherboard) {
        this.motherboard = motherboard;
    }


    /**
     * Function to set computer power supply Part
     * @param power_supply Part power supply to be used
     */
    public void setPower_supply(Part power_supply) {
        this.power_supply = power_supply;
    }

    /**
     * Function to set computer ram Part
     * @param ram Part ram to be used
     */
    public void setRam(Part ram) {
        this.ram = ram;
    }

    /**
     * Function to set computer storage Part
     * @param storage Part storage to be used
     */
    public void setStorage(Part storage) {
        this.storage = storage;
    }

    /**
     * Function to set computer price
     * @param price double price to be used
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Prebuilt Constructor function
     * @param name Part
     * @param motherboard Part
     * @param gpu Part
     * @param ps Part
     * @param cpu Part
     * @param ram Part
     * @param aCase Part
     * @param storage Part
     */
    public Prebuilt(String name, Part motherboard, Part gpu, Part ps, Part cpu,
    Part ram, Part aCase, Part storage, double price){
        setComp_name(name);
        setMotherboard(motherboard);
        setGpu(gpu);
        setPower_supply(ps);
        setCpu(cpu);
        setRam(ram);
        setaCase(aCase);
        setStorage(storage);
        setPrice(price);
    }
}
