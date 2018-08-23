import java.util.Random;

public class Address {

    private String address = "";
    private int addressNumber = 0;
    Random r;

    ///////////////////////////////////////////////////////////////


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }

    ///////////////////////////////////////////////////////////////

    public Address() {
        r = new Random();
        this.setAddressNumber(r.nextInt(300));
        binaryString();
    }

    public Address(Address a) {
        this.setAddressNumber(a.getAddressNumber() + 1);
        binaryString();
    }

    ///////////////////////////////////////////////////////////////

    public void binaryString() {
        String binary = Integer.toBinaryString(this.addressNumber);
        int length = binary.length();
        for(int i = 0; i < 32 - length; i++) {
            binary = "0" + binary;
        }
        this.setAddress(binary);
    }

    ///////////////////////////////////////////////////////////////

    public void stringToBinary() {
        this.setAddressNumber(Integer.parseInt(this.getAddress(), 2));
    }








}
