import java.util.Random;
import java.util.Vector;

public class Data {

    private Vector<Address> addresses;
    private Vector<Address> finalAddresses;
    Random r;

    ////////////////////////////////////////////////////////////////////////


    public Vector<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Vector<Address> addresses) {
        this.addresses = addresses;
    }

    public Vector<Address> getFinalAddresses() {
        return finalAddresses;
    }

    public void setFinalAddresses(Vector<Address> finalAddresses) {
        this.finalAddresses = finalAddresses;
    }

    ///////////////////////////////////////////////////////////////////////

    public Data() {
        addresses = new Vector<Address>(5,5);
        finalAddresses = new Vector<Address>(5, 5);
        r = new Random();
    }

    ///////////////////////////////////////////////////////////////////////

    public void set500Addresses() {
        //locality of reference
        int elements = 0;
        while(elements < 250) {
            Address a = new Address();
            addresses.addElement(a);
            Address temp = a;
            int rand = r.nextInt(6);
            for(int i = 0; i < rand; i++) {
                Address b = new Address(temp);
                addresses.addElement(b);
                temp = b;
                elements++;
            }
        }

        //temporal locality
        for(int i = 0; i < 50; i++) {
            int rand = r.nextInt(25);
            for(int j = 0; j < 10; j++) {
                finalAddresses.addElement(addresses.elementAt(j + rand * 10));
            }
        }
    }
}
