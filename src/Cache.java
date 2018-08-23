import java.util.Vector;

public class Cache {

    private Vector<Block> cacheBlocks;
    private VictimCache victimCache;
    private int cacheSize = 2 ^ 8;
    private int hit = 0;
    private Vector<Address> instructs;

    //////////////////////////////////////////////////////////////////////

    public Vector<Block> getCacheBlocks() {
        return cacheBlocks;
    }

    public void setCacheBlocks(Vector<Block> cacheBlocks) {
        this.cacheBlocks = cacheBlocks;
    }

    public VictimCache getVictimCache() {
        return victimCache;
    }

    public void setVictimCache(VictimCache victimCache) {
        this.victimCache = victimCache;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }


    //////////////////////////////////////////////////////////////////

    public Cache(Vector<Address> instructs) {
        cacheBlocks = new Vector<Block>(2 ^ 8);
        victimCache = new VictimCache();
        for(int i = 0; i < cacheSize; i++) {
            Block b = new Block(i);
            cacheBlocks.add(b);
        }
        this.instructs = instructs;
    }

    /////////////////////////////////////////////////////////////////

    //Check
    public void checkDataInCache() {

        for(int i = 0; i < instructs.size(); i++) {
            Address current = instructs.elementAt(i);
            int currentBlockNumber = (current.getAddressNumber() / 16) % cacheSize;
            int currentTag = (current.getAddressNumber() / 16) / cacheSize;
            //Check to see if data with the current address is in L1 cache

            //In case the block in L1 cache that's supposed to contain the current data is empty
            if(cacheBlocks.elementAt(currentBlockNumber).getTag() == -1000) {
                cacheBlocks.elementAt(currentBlockNumber).setTag(currentTag);
                for(int j = 0; j < 16; j++) {
                    Address a = new Address();
                    a.setAddressNumber(currentTag * 16 + j);
                    a.binaryString();
                    cacheBlocks.elementAt(currentBlockNumber).getAddresses().addElement(a);
                }
            }

            //In case data is already in L1 cache
            else if(cacheBlocks.elementAt(currentBlockNumber).getTag() != -1000 &&
                    cacheBlocks.elementAt(currentBlockNumber).getTag() == currentTag) {
                this.setHit(this.getHit() + 1);
            }

            //In case data is not in L1 cache
            //Comment this scope to check the hit rate without L2 cache
            //Checking Victim Cache
            else if(cacheBlocks.elementAt(currentBlockNumber).getTag() != -1000 &&
                    cacheBlocks.elementAt(currentBlockNumber).getTag() != currentTag) {
                boolean found = false; //To see if data is found in Victim Cache
                int j = 0;
                for(j = 0; j < 16 && !found; j++) {
                    //Check to see if block containing
                    if(victimCache.getVictimCacheData().elementAt(j).getTag() == currentTag) {
                        found = true;
                    }
                }
                //If data is found in Victim Cache
                if(found) {
                    this.setHit(this.getHit() + 1);
                    for(int t = 0; t < 16; t++) {
                        victimCache.getVictimCacheData().elementAt(t).setUsage(victimCache.getVictimCacheData().elementAt(t).getUsage() - 1);
                    }
                    Block temp = new Block(currentBlockNumber);
                    if(j == 16) {
                        j = 15;
                    }
                    temp.setAddresses(victimCache.getVictimCacheData().elementAt(j).getAddresses());
                    temp.setTag(currentTag);
                    temp.setBlockNumber(currentBlockNumber);
                    temp.setUsage(0);
                    victimCache.getVictimCacheData().elementAt(j).setAddresses(cacheBlocks.elementAt(currentBlockNumber).getAddresses());
                    victimCache.getVictimCacheData().elementAt(j).setUsage(Integer.MAX_VALUE);
                    victimCache.getVictimCacheData().elementAt(j).setBlockNumber(currentBlockNumber);
                    victimCache.getVictimCacheData().elementAt(j).setTag(currentTag);
                    cacheBlocks.elementAt(currentBlockNumber).setAddresses(temp.getAddresses());
                    cacheBlocks.elementAt(currentBlockNumber).setTag(temp.getTag());
                    cacheBlocks.elementAt(currentBlockNumber).setUsage(temp.getUsage());
                    cacheBlocks.elementAt(currentBlockNumber).setBlockNumber(temp.getBlockNumber());
                }
                //If data is not found in Victim Cache
                else {
                    int minUse = victimCache.getVictimCacheData().elementAt(15).getUsage();
                    int index = 15;
                    for(int k = 15; k >= 0; k--) {
                        if(victimCache.getVictimCacheData().elementAt(k).getUsage() <= minUse) {
                            index = k;
                        }
                    }

                    for(int s = 0; s < 16; s++) {
                       victimCache.getVictimCacheData().elementAt(s).setUsage(victimCache.getVictimCacheData().elementAt(s).getUsage() - 1);
                    }

                    victimCache.getVictimCacheData().elementAt(index).setTag(cacheBlocks.elementAt(currentBlockNumber).getTag());
                    victimCache.getVictimCacheData().elementAt(index).setUsage(Integer.MAX_VALUE);
                    victimCache.getVictimCacheData().elementAt(index).setBlockNumber(currentBlockNumber);
                    victimCache.getVictimCacheData().elementAt(index).setAddresses(cacheBlocks.elementAt(currentBlockNumber).getAddresses());

                    cacheBlocks.elementAt(currentBlockNumber).setBlockNumber(currentBlockNumber);
                    cacheBlocks.elementAt(currentBlockNumber).setUsage(0);
                    cacheBlocks.elementAt(currentBlockNumber).setTag(currentTag);
                    for(int k = 0; k < 16; k++) {
                        cacheBlocks.elementAt(currentBlockNumber).getAddresses().elementAt(k).setAddressNumber(currentTag * 16 + k);
                        cacheBlocks.elementAt(currentBlockNumber).getAddresses().elementAt(k).binaryString();
                    }
                }
            }


        }



    }

}
