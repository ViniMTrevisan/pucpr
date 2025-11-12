public class HashTableHash1 extends AbstractHashTable {
    public HashTableHash1(int tableCapacity) { super(tableCapacity); }

    @Override
    protected int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) hashValue += key.charAt(i);
        return hashValue;
    }
}
