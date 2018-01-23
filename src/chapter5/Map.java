package chapter5;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/23
 * Time:11:15
 */
public class Map<Keytype, Valtype> {
    private QuadraticProbingHashTable<Entry<Keytype, Valtype>> items;

    private static class Entry<Keytype, Valtype> {
        Keytype key;
        Valtype val;

        public Entry(Keytype key, Valtype val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public boolean equals(Object rhs) {
            return rhs instanceof Entry && key.equals(((Entry<Keytype, Valtype>) rhs).key);
        }

    }

    public Map() {
        items = new QuadraticProbingHashTable<>();
    }

    public void put(Keytype key, Valtype val) {
        Entry<Keytype, Valtype> e = new Entry<Keytype, Valtype>(key, val);
        items.insert(e);
    }

    public Valtype get(Keytype key) {
        Valtype v = (Valtype) new Object();
        Entry<Keytype, Valtype> e = new Entry<>(key, v);
        e = items.find(e);
        return e.val;
    }

    public Boolean isEmpty() {
        return items.isEmpty();
    }

    public void makeEmpty() {
        items.makeEmpty();
    }
}
