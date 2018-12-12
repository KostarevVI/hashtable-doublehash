package HashTable;

import java.util.Objects;

public class HashTable {

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
     * Getter of size of hash table
     *
     * @return Size of hash table
     */

    public int getSize() {
        return size;
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
                    this.push(cell.getValue());
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

    public boolean push(int value) {
        for (int i = 0; i < this.size; i++) {
            int valHash = hashOfValue(value, i);
            if (cellsArray[valHash] != null && cellsArray[valHash].getValue().equals(value) &&
                    !cellsArray[valHash].isDeleted()) {
                cellsArray[valHash].incAmount();
                System.out.println("Увеличил на 1");
                return true;
            } else if (cellsArray[valHash] == null || cellsArray[valHash].isDeleted()) {
                cellsArray[valHash] = new Cell(valHash, value);
                System.out.println("Добавил новую ячейку");
                return true;
            }
        }
        this.rehash();  //требуется увеличить размер таблицы
        return this.push(value);
    }

    /**
     * Deleting value in HashTable
     *
     * @param value Received value from user or else
     * @return If deleting succeeds returns True, else False
     */

    public boolean delete(int value) {
        if (this.contains(value)) {
            for (int i = 0; i < this.size; i++) {
                int valHash = hashOfValue(value, i);
                if (cellsArray[valHash].getValue().equals(value)) {
                    if (cellsArray[valHash].getAmount() > 1) {
                        cellsArray[valHash].decAmount();
                        System.out.println("Уменьшил на 1");
                        return true;
                    } else {
                        cellsArray[valHash].delete();
                        System.out.println("Удалил");
                        return true;
                    }
                }
            }
        }
        System.out.println("Удалять нечего");
        return false;
    }

    /**
     * Searches for value in HashTable
     *
     * @param value Received value from user or else
     * @return If the search succeeds returns True, else False
     */

    public Boolean contains(int value) {
        for (int i = 0; i < this.size; i++) {
            int valHash = hashOfValue(value, i);
            if (cellsArray[valHash] != null) {
                if (cellsArray[valHash].getValue().equals(value) && !cellsArray[valHash].isDeleted()) {
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

    public Integer get(int key) {
        if (key < this.size && this.cellsArray[key] != null && !this.cellsArray[key].isDeleted()) {
            return this.cellsArray[key].getValue();
        } else {
            return null;
        }
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

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            cellsArray[i] = null;
        }
        System.out.println("Таблица очищена");
    }

    /**
     * Checking is HashTable empty
     *
     * @return If empty returns True, else False
     */

    public Boolean isEmpty() {
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
        return Objects.hash(this, this.cellsArray.length);
    }

    private int hashOfValue(Integer value, int i) {
        int h1 = (value.hashCode() * 31) % (this.cellsArray.length);
        int h2 = value % (this.cellsArray.length - 1) + 1;
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
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        HashTable otherHashTable = (HashTable) obj;
        if (this.size != otherHashTable.getSize()) {
            System.out.println("Таблицы не равны");
            return false;
        }
        for (int i = 0; i < this.size; i++)
            if (!Objects.equals(this.cellsArray[i], otherHashTable.cellsArray[i])) {
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

    public HashTable clone() {
        HashTable newHashTable = new HashTable();
        if (this.size >= 0) {
            while (newHashTable.getSize() < this.getSize()) {
                newHashTable.size *= 2;
            }
            newHashTable.cellsArray = new Cell[newHashTable.size];
            System.arraycopy(this.cellsArray, 0, newHashTable.cellsArray, 0, this.size);
        }
        return newHashTable;
    }
}


