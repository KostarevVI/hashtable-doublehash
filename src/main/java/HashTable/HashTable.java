package HashTable;

import java.util.*;

public class HashTable<K, V> implements Map<K, V> {

    private Cell<K, V>[] cellsArray;
    private int size;

    /**
     * Constructor of HashTable
     * Init size is 16
     */

    public HashTable() {
        this.size = 16;
        this.cellsArray = new Cell[size];
    }

    /**
     * Increases the capacity of and internally reorganizes this HashTable
     */

    private void rehash() {
        Cell[] oldCells = this.cellsArray;
        this.size *= 2;
        this.cellsArray = new Cell[this.size];
        for (Cell<K, V> cell : oldCells) {
            if (cell != null && !cell.isDeleted()) {
                for (int i = 0; i < cell.getAmount(); i++) {
                    this.put(cell.getKey(), cell.getValue());
                }
            }
        }
        System.out.println("Расширили таблицу до " + this.size + " ячеек");
    }

    /**
     * Adding value in HashTable
     *
     * @param value Received value from user or else
     * @return If adding succeeds returns True, else False
     */

    @Override
    public V put(K key, V value) {
        for (int i = 0; i < this.size; i++) {
            int cellHash = hashOfKey(key, i);
            if (cellsArray[cellHash] != null) {
                if (cellsArray[cellHash].isDeleted() || (cellsArray[cellHash].getKey().equals(key) &&
                        !cellsArray[cellHash].getValue().equals(value))) {
                    Cell<K, V> prevCell = cellsArray[cellHash];
                    Cell<K, V> newCell = new Cell<>(key, value);
                    cellsArray[cellHash] = newCell;
                    System.out.println("Заменил значение");
                    return prevCell.getValue();
                } else if (cellsArray[cellHash].getValue().equals(value) &&
                        cellsArray[cellHash].getKey().equals(key) && !cellsArray[cellHash].isDeleted()) {
                    cellsArray[cellHash].incAmount();
                    System.out.println("Увеличил на 1");
                    return cellsArray[cellHash].getValue();
                }
            } else if (cellsArray[cellHash] == null) {
                Cell<K, V> newCell = new Cell<>(key, value);
                cellsArray[cellHash] = newCell;
                System.out.println("Добавил новую ячейку");
                return null;
            }
        }
        this.rehash();  //требуется увеличить размер таблицы
        return this.put(key, value);
    }

    /**
     * Deleting value in HashTable
     *
     * @param key Received value from user or else
     * @return If deleting succeeds returns True, else False
     */

    @Override
    public V remove(Object key) {
        for (int i = 0; i < this.size; i++) {
            int cellHash = hashOfKey(key, i);
            if (cellsArray[cellHash].getKey().equals(key)) {
                if (cellsArray[cellHash].getAmount() > 1) {
                    cellsArray[cellHash].decAmount();
                    System.out.println("Уменьшил на 1");
                    return cellsArray[cellHash].getValue();
                } else {
                    cellsArray[cellHash].delete();
                    cellsArray[cellHash].setValue(null);
                    System.out.println("Удалил");
                    return cellsArray[cellHash].getValue();
                }
            }
        }
        System.out.println("Удалять нечего");
        return null;
    }

    /**
     * Searches for value in HashTable
     *
     * @param m Received value from user or else
     */

    @Override
    public void putAll(Map m) {
        for (Object obj : m.entrySet()) {
            Cell<K, V> cell = (Cell) obj;
            this.put(cell.getKey(), cell.getValue());
        }
    }

    /**
     * Searches for value in HashTable
     *
     * @param key Received value from user or else
     * @return If the search succeeds returns True, else False
     */

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < this.size; i++) {
            int cellHash = hashOfKey(key, i);
            if (cellsArray[cellHash] == null) {
                break;
            }
            if (cellsArray[cellHash].getKey().equals(key) && !cellsArray[cellHash].isDeleted()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for value in HashTable
     *
     * @param value Received value from user or else
     * @return If the search succeeds returns True, else False
     */

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < this.size; i++) {
            if (cellsArray[i] != null) {
                if (!cellsArray[i].isDeleted() && cellsArray[i].getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the value which mapped (or not) to key
     *
     * @param key Received key from user or else
     * @return If value exists - returns value, else null
     */

    @Override
    public V get(Object key) {
        for (int i = 0; i < this.size; i++) {
            int cellHash = hashOfKey(key, i);
            if (cellsArray[cellHash] != null) {
                if (cellsArray[cellHash].getKey().equals(key) && !cellsArray[cellHash].isDeleted()) {
                    return cellsArray[cellHash].getValue();
                }
            }
        }
        return null;
    }

    /**
     * Returns Cell in Hash table with key
     *
     * @param key Received key from user or else
     * @return If Cell exists - returns Cell, else null
     */

    public Cell getCell(K key) {
        for (int i = 0; i < this.size; i++) {
            int cellHash = hashOfKey(key, i);
            if (cellsArray[cellHash] != null) {
                if (cellsArray[cellHash].getKey().equals(key) && !cellsArray[cellHash].isDeleted()) {
                    return cellsArray[cellHash];
                }
            }
        }
        return null;
    }

    /**
     * Printing in console all Cells in HashTable (debugging info)
     */

    public void print() {
        for (int i = 0; i < this.size; i++) {
            if (cellsArray[i] != null) {
                System.out.println(cellsArray[i].toString());
            } else {
                System.out.println("Null");
            }
        }
        System.out.println();
    }

    /**
     * Clears HashTable
     */

    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++) {
            cellsArray[i] = null;
        }
        System.out.println("Таблица очищена");
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public Set<K> keySet() {
//        Set<K> keysCol = new HashSet<>();
//        for (int i = 0; i < this.size; i++) {
//            keysCol.add(cellsArray[i].getKey());
//        }
//        return keysCol;
        return new KeySet();
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public Collection<V> values() {
//        List<V> valuesCol = new ArrayList<>();
//        for (int i = 0; i < this.size; i++) {
//            valuesCol.add(cellsArray[i].getValue());
//        }
//        return valuesCol;
        return new Values();
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
//        Set<Map.Entry<K, V>> entrySet = new HashSet<>();
//        for (Cell cell : this.cellsArray) {
//            if (cell != null && !cell.isDeleted()) {
//                entrySet.add(cell);
//            }
//        }
//        return entrySet;
        return new EntrySet();
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public int size() {
        int counter = 0;
        for (Cell cell : this.cellsArray) {
            if (cell != null && !cell.isDeleted()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Checking is HashTable empty
     *
     * @return If empty returns True, else False
     */

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.size; i++) {
            if (cellsArray[i] != null) {
                if (!cellsArray[i].isDeleted()) {
                    return false;
                }
            }
        }
        System.out.println("Таблица пустая");
        return true;
    }

    /**
     * Overriding of hashCode for HashTable
     *
     * @return Generated hashCode
     */

    @Override
    public int hashCode() {
        int hashOfMap = 0;
        for (int i = 0; i < this.size; i++) {
            if (cellsArray[i] != null && !cellsArray[i].isDeleted()) {
                hashOfMap += cellsArray[i].hashCode();
            }
        }
        return hashOfMap;
    }

    private int hashOfKey(Object key, int i) {
        int h1 = (key.hashCode() * 31) % (this.size);
        int h2 = key.toString().hashCode() % (this.size - 1) + 1;
        if (h2 % 2 == 0) {
            h2++;
        }
        return (h1 + i * h2) % this.size;
    }

    /**
     * Override of equals for HashTable
     *
     * @param obj Other HashTable
     * @return If HashTables equals returns True, else False
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Map)) {
            return false;
        }
        Map mapObj = (Map) obj;
        List<Entry> thisEntries = new ArrayList<>(this.entrySet());
        if (this.size() != mapObj.size()) {
            System.out.println("Таблицы не равны");
            return false;
        }
        for (Entry entry : thisEntries) {
            if (!entry.getValue().equals(mapObj.get(entry.getKey()))) {
                System.out.println("Таблицы не равны");
                return false;
            }
        }
        System.out.println("Таблицы равны");
        return true;
    }

    /**
     * Override of toString for HashTable
     *
     * @return String of values in braces
     */

    @Override
    public String toString() {
        if (!this.isEmpty()) {
            StringBuilder cellsStr = new StringBuilder("[");
            for (int i = 0; i < this.size; i++) {
                if (cellsArray[i] != null && !cellsArray[i].isDeleted()) {
                    cellsStr.append(cellsArray[i].getValue().toString()).append(", ");
                }
            }
            cellsStr.setLength(cellsStr.length() - 2);
            cellsStr.append("]");
            return cellsStr.toString();
        } else {
            return "[]";
        }
    }

    /**
     * Clones the current Hash table
     *
     * @return Clone of current Hash table
     */

    @Override
    public HashTable clone() {
        HashTable newHashTable = new HashTable();
        if (this.size >= 0) {
            while (newHashTable.size < this.size) {
                newHashTable.size *= 2;
            }
            newHashTable.cellsArray = new Cell[newHashTable.size];
            System.arraycopy(this.cellsArray, 0, newHashTable.cellsArray, 0, this.size);
        }
        return newHashTable;
    }

    abstract class AbstractIterator<T> implements Iterator<T> {
        Integer curIndex = -1;
        Integer lastDeleted = null;

        protected void skipNull() {
            while (curIndex < size) {
                if (cellsArray[curIndex] != null && !cellsArray[curIndex].isDeleted())
                    break;
                curIndex++;
            }
        }

        @Override
        public boolean hasNext() {
            int i = curIndex + 1;
            while (i < size) {
                if (cellsArray[i] != null && !cellsArray[i].isDeleted())
                    break;
                i++;
            }
            return i < size; // && cellsArray[curIndex] != null;
        }

        @Override
        public void remove() {
            if (curIndex.equals(lastDeleted)) {
                throw new RuntimeException("Double remove isn't allowed");
            }
            HashTable.this.remove(cellsArray[curIndex].getKey());
            lastDeleted = curIndex;
            //curIndex++;
        }
    }

    class KeySetIterator extends AbstractIterator<K> {

        @Override
        public K next() {
            curIndex++;
            if (cellsArray[curIndex] == null || cellsArray[curIndex].isDeleted()) {
                skipNull();
            }
            K ret = cellsArray[curIndex].getKey();
            return ret;
        }
    }

    class KeySet extends AbstractSet<K> {

        @Override
        public Iterator<K> iterator() {
            return new KeySetIterator();
        }

        @Override
        public int size() {
            return HashTable.this.size();
        }

        @Override
        public boolean contains(Object o) {
            return containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            if (containsKey(o)) {
                HashTable.this.remove(o);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }
    }

    class EntrySetIterator extends AbstractIterator<Entry<K, V>> {

        @Override
        public Entry<K, V> next() {
            curIndex++;
            if (cellsArray[curIndex] == null || cellsArray[curIndex].isDeleted()) {
                skipNull();
            }
            Entry<K, V> ret = cellsArray[curIndex];
            return ret;
        }
    }

    class EntrySet extends AbstractSet<Entry<K, V>> {

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new EntrySetIterator();
        }

        @Override
        public int size() {
            return HashTable.this.size();
        }

        @Override
        public boolean contains(Object o) {
            Entry<K, V> newCell = (Entry) o;
            return newCell.getValue().equals(get(newCell.getKey()));
        }

        @Override
        public boolean remove(Object o) {
            if (containsKey(((Entry) o).getKey())) {
                HashTable.this.remove(o);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }
    }

    class ValuesIterator extends AbstractIterator<V> {

        @Override
        public V next() {
            curIndex++;
            if (cellsArray[curIndex] == null || cellsArray[curIndex].isDeleted()) {
                skipNull();
            }
            Entry<K, V> ret = cellsArray[curIndex];
            return ret.getValue();
        }
    }

    class Values extends AbstractCollection<V> {

        @Override
        public int size() {
            return HashTable.this.size();
        }

        @Override
        public boolean contains(Object o) {
            return HashTable.this.containsValue(o);
        }

        @Override
        public Iterator<V> iterator() {
            return new ValuesIterator();
        }

        @Override
        public boolean remove(Object o) {
            for(int i = 0; i < size(); i++){
                if(cellsArray[i] == null || cellsArray[i].isDeleted()){
                    continue;
                }
                if(cellsArray[i].getValue().equals(o)){
                    cellsArray[i].delete();
                    cellsArray[i].setValue(null);
                    return true;
                }
            }
            return false;
        }

        @Override
        public void clear() {
            HashTable.this.clear();
        }
    }
}


