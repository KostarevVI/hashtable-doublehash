package HashTable;

import java.util.Map;
import java.util.Objects;

public class Cell<K, V> implements Map.Entry {

    private K key;
    private V value;
    private Integer amount; //да, я хочу хранить кол-во значений, ведь это моя таблица и я её сделал
    private Boolean deleted;

    /**
     * Constructor of Cell
     *
     * @param key   Received key
     * @param value Received value
     */

    public Cell(K key, V value) {
        this.key = key;
        this.value = value;
        this.amount = 1;
        this.deleted = false;
    }

    /**
     * Getter of key in Cell
     *
     * @return Key in Cell
     */

    @Override
    public K getKey() {
        return key;
    }

    /**
     * Getter of value in Cell
     *
     * @return Value in Cell
     */

    @Override
    public V getValue() {
        return value;
    }


    /**
     * Overrides value in cell
     *
     * @param value Received value
     * @return Returns prev value
     */

    @Override
    public Object setValue(Object value) {
        V oldValue = this.value;
        this.value = (V) value;
        return oldValue;
    }

    /**
     * Getter of amount in Cell
     *
     * @return Amount of values in Cell
     */

    public Integer getAmount() {
        return amount;
    }

    /**
     * Getter of deleted status of Cell
     *
     * @return Is cell deleted
     */

    public Boolean isDeleted() {
        return deleted;
    }

    /**
     * Increase amount of this value in Cell
     */

    public void incAmount() {
        this.amount++;
    }

    /**
     * Decrease amount of this value in Cell
     */

    public void decAmount() {
        if (amount > 0)
            this.amount--;
    }

    /**
     * Delete flag of Cell
     */

    public void delete() {
        this.deleted = true;
    }

    /**
     * Override of equals for Cell
     *
     * @param obj Other Cell
     * @return If Cells equals returns True, else False
     */

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return this.isDeleted();
        }
        if (obj == this)
            return true;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry otherCell = (Map.Entry) obj;
        return Objects.equals(this.getValue(), otherCell.getValue()) &&
                Objects.equals(this.getKey(), otherCell.getKey()) && !this.isDeleted();
    }

    /**
     * Override of hashCode for Cell
     *
     * @return Generated hashCode
     */

    @Override
    public int hashCode() {
        int keyHash = this.getKey() == null ? 0 : this.getKey().hashCode();
        int valueHash = this.getValue() == null ? 0 : this.getValue().hashCode();
        return keyHash ^ valueHash;
    }

    /**
     * Override of toString for Cell
     *
     * @return String with key value
     */

    @Override
    public String toString() {
        return "Cell{" +
                "key=" + key +
                " value=" + value +
                " amount=" + amount +
                " isDeleted=" + deleted +
                '}';
    }
}