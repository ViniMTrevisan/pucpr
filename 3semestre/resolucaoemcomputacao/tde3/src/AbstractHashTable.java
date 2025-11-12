import java.util.LinkedList;
import java.util.Arrays;

public abstract class AbstractHashTable {
    protected final int tableCapacity;
    protected final LinkedList<String>[] buckets;

    protected long collisionEventCount = 0;
    protected long collisionComparisonsTotal = 0;

    public AbstractHashTable(int tableCapacity) {
        this.tableCapacity = tableCapacity;
        @SuppressWarnings("unchecked")
        LinkedList<String>[] bucketsArray = new LinkedList[tableCapacity];
        this.buckets = bucketsArray;
        for (int i = 0; i < tableCapacity; i++) this.buckets[i] = new LinkedList<>();
    }

    protected abstract int hash(String key);

    protected int indexForKey(String key) {
        int rawHash = hash(key);
        return Math.floorMod(rawHash, tableCapacity);
    }

    public void insert(String key) {
        int bucketIndex = indexForKey(key);
        LinkedList<String> bucketList = buckets[bucketIndex];
        if (!bucketList.isEmpty()) {
            collisionEventCount++;
            collisionComparisonsTotal += bucketList.size();
        }
        bucketList.add(key);
    }

    public boolean contains(String key) {
        int bucketIndex = indexForKey(key);
        LinkedList<String> bucketList = buckets[bucketIndex];
        return bucketList.contains(key);
    }

    public int[] getDistribution() {
        int[] distribution = new int[tableCapacity];
        for (int i = 0; i < tableCapacity; i++) distribution[i] = buckets[i].size();
        return distribution;
    }

    public long getCollisionEvents() { return collisionEventCount; }
    public long getCollisionComparisons() { return collisionComparisonsTotal; }
    public int getCapacity() { return tableCapacity; }

    public void clear() {
        for (int i = 0; i < tableCapacity; i++) buckets[i].clear();
        collisionEventCount = 0;
        collisionComparisonsTotal = 0;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[capacity=" + tableCapacity + ", collisionsEvents=" + collisionEventCount + ", collisionComparisons=" + collisionComparisonsTotal + "]";
    }
}
