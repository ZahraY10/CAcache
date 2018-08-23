import java.io.*;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {

        //Create data
        Data data = new Data();
        data.set500Addresses();
        try {
            FileWriter fw = new FileWriter("File1.text");
            for(int i = 0; i < 500; i ++) {
                fw.write(data.getFinalAddresses().elementAt(i).getAddress());
                fw.write("\n");
            }
            fw.flush();
        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }


        /////////////////////////////////////////////
        //Read data from files

        try (BufferedReader br = new BufferedReader(new FileReader("File1.text"))) {
            Vector<Address> CPUInstruct = new Vector<Address>(500, 5);
            String line;
            while ((line = br.readLine()) != null) {
                Address newAddress = new Address();
                newAddress.setAddress(line);
                newAddress.stringToBinary();
                CPUInstruct.addElement(newAddress);
            }
            Cache cache = new Cache(CPUInstruct);
            cache.checkDataInCache();
            System.out.println("Hits = "+ cache.getHit());
        }

        catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }
}
