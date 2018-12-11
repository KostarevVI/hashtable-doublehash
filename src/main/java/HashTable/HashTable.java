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
            for (int i = 0; i < cell.getAmount(); i++) {
                this.push(cell.getValue());
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
        int h1 = hashOfValue1(value);
        int h2 = hashOfValue2(value);
        int endHash;
        for (int i = 0; i < this.size; i++) {
            endHash = (h1 + i * h2) % this.size;
            if (cellsArray[endHash] != null && cellsArray[endHash].getValue().equals(value) &&
                    !cellsArray[endHash].isDeleted()) {
                cellsArray[endHash].incAmount();
                System.out.println("Увеличил на 1");
                return true;
            } else if (cellsArray[endHash] == null || cellsArray[endHash].isDeleted()) {
                cellsArray[endHash] = new Cell(endHash, value);
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
            int h1 = hashOfValue1(value);
            int h2 = hashOfValue2(value);
            int endHash;
            for (int i = 0; i < this.size; i++) {
                endHash = (h1 + i * h2) % this.size;
                if (cellsArray[endHash].getValue().equals(value)) {
                    if (cellsArray[endHash].getAmount() > 1) {
                        cellsArray[endHash].decAmount();
                        System.out.println("Уменьшил на 1");
                        return true;
                    } else {
                        cellsArray[endHash].delete();
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
        int h1 = hashOfValue1(value);
        int h2 = hashOfValue2(value);
        int endHash;
        for (int i = 0; i < this.size; i++) {
            endHash = (h1 + i * h2) % this.size;
            if (cellsArray[endHash] != null) {
                if (cellsArray[endHash].getValue().equals(value) && !cellsArray[endHash].isDeleted()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Printing in console all Cells in HashTable (debugging info)
     */

    public void print() {
        for (int i = 0; i < this.size; i++) {
            if(cellsArray[i] != null) {
                System.out.println(cellsArray[i].toString());
            }else{
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

    private int hashOfValue1(Integer value) {
        return (value.hashCode() * 31) % (this.cellsArray.length);
    }

    private int hashOfValue2(Integer value) {
        return value % (this.cellsArray.length - 1) + 1;
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
}


