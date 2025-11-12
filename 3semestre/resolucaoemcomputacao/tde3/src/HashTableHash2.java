public class HashTableHash2 extends AbstractHashTable {
    public HashTableHash2(int tableCapacity) { super(tableCapacity); }

    @Override
    protected int hash(String key) {
        long hashValue = 5381L;
        for (int i = 0; i < key.length(); i++) {
            hashValue = ((hashValue << 5) + hashValue) + key.charAt(i);
        }
        return (int)hashValue;
    }
}
