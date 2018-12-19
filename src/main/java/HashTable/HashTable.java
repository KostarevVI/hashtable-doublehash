package HashTable;

import java.util.*;

public class HashTable implements Map {

    private Cell[] cellsArray;
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
        for (Cell cell : oldCells) {
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
    public Object put(Object key, Object value) {
        for (int i = 0; i < this.size; i++) {
            int cellHash = hashOfKey(key, i);
            if (cellsArray[cellHash] != null) {
                if (cellsArray[cellHash].getValue().equals(value) &&
                        cellsArray[cellHash].getKey().equals(key) && !cellsArray[cellHash].isDeleted()) {
                    cellsArray[cellHash].incAmount();
                    System.out.println("Увеличил на 1");
                    return cellsArray[cellHash].getValue();
                } else if (cellsArray[cellHash].isDeleted() || cellsArray[cellHash].getKey().equals(key)) {
                    Object prevValue = cellsArray[cellHash].getValue();
                    cellsArray[cellHash] = new Cell(cellHash, value);
                    System.out.println("Заменил значение");
                    return prevValue;
                }
            } else if (cellsArray[cellHash] == null) {
                cellsArray[cellHash] = new Cell(key, value);
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
    public Object remove(Object key) {
        if (this.containsKey(key)) {
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
            Cell cell = (Cell) obj;
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
            if (cellsArray[cellHash] != null) {
                if (cellsArray[cellHash].getKey().equals(key) && !cellsArray[cellHash].isDeleted()) {
                    return true;
                }
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
    public Object get(Object key) {
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

    public Cell getCell(Object key) {
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
    public Set keySet() {
        Set<Object> keysCol = new HashSet<>();
        for (int i = 0; i < this.size; i++) {
            keysCol.add(cellsArray[i].getKey());
        }
        return keysCol;
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public Collection values() {
        List<Object> valuesCol = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            valuesCol.add(cellsArray[i].getValue());
        }
        return valuesCol;
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public Set<Entry> entrySet() {
        return new HashSet<>(Arrays.asList(cellsArray).subList(0, this.size));
    }

    /**
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    @Override
    public int size() {
        return size;
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
            if (cellsArray[i] != null) {
                hashOfMap += cellsArray[i].hashCode();
            }
        }
        return hashOfMap;
    }

    private int hashOfKey(Object key, int i) {
        int h1 = (key.hashCode() * 31) % (this.cellsArray.length);
        int h2 = key.toString().hashCode() % (this.cellsArray.length - 1) + 1;
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
        if (this==obj) {
            return true;
        }
        if (Objects.equals(obj,null) || this.getClass() != obj.getClass()) {
            return false;
        }
        Map mapObj = (HashTable) obj;
        List<Entry> thisEntries = new ArrayList<>(this.entrySet());
        List<Entry> objEntries = new ArrayList<>(mapObj.entrySet());
        if (thisEntries.size() != objEntries.size()) {
            System.out.println("Таблицы не равны");
            return false;
        }
        for (int i = 0; i < this.size; i++)
            if (!Objects.equals(thisEntries.get(i), objEntries.get(i))) {
                System.out.println("Таблицы не равны");
                return false;
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
            while (newHashTable.size() < this.size()) {
                newHashTable.size *= 2;
            }
            newHashTable.cellsArray = new Cell[newHashTable.size];
            System.arraycopy(this.cellsArray, 0, newHashTable.cellsArray, 0, this.size);
        }
        return newHashTable;
    }
}


