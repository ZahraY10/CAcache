import java.util.Vector;

public class Block {

    Vector<Address> addresses;
    private int usage = 0;
    private  int blockNumber = 0;
    private int tag = -1000;

    ///////////////////////////////////////

    public Vector<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Vector<Address> addresses) {
        this.addresses = addresses;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    //////////////////////////////////////

    public Block(int blockNumber) {
        addresses = new Vector<Address>(16, 5);
        this.setBlockNumber(blockNumber);
        for(int i = 0; i < 16; i++) {
            Address a = new Address();
            addresses.addElement(a);
        }
    }
}
