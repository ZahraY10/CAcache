import java.util.Vector;

public class VictimCache {

    private Vector<Block> victimCacheData;
    private int maxUsedBlock;
    private int minUsedBlock;

    public Vector<Block> getVictimCacheData() {
        return victimCacheData;
    }

    public void setVictimCacheData(Vector<Block> victimCacheData) {
        this.victimCacheData = victimCacheData;
    }

    public int getMaxUsedBlock() {
        return maxUsedBlock;
    }

    public void setMaxUsedBlock(int maxUsedBlock) {
        this.maxUsedBlock = maxUsedBlock;
    }

    public int getMinUsedBlock() {
        return minUsedBlock;
    }

    public void setMinUsedBlock(int minUsedBlock) {
        this.minUsedBlock = minUsedBlock;
    }

    /////////////////////////////////////////////////////////

    public VictimCache() {
        victimCacheData = new Vector<Block>(16, 5);
        for(int i = 0; i < 16; i++) {
            Block b = new Block(i);
            victimCacheData.addElement(b);
        }
    }

    ////////////////////////////////////////////////////////
}
