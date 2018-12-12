package HashTable;

import java.util.Objects;

public class Cell {

    private Integer key;
    private Object value;
    private Integer amount; //да, я хочу хранить кол-во значений, ведь это моя таблица и я её сделал
    private Boolean deleted;

    /**
     * Constructor of Cell
     *
     * @param key   Received key
     * @param value Received value
     */

    public Cell(Integer key, Object value) {
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

    public Integer getKey() {
        return key;
    }

    /**
     * Getter of value in Cell
     *
     * @return Value in Cell
     */

    public Object getValue() {
        return value;
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
        if (this.getClass() != obj.getClass())
            return false;
        if (obj == this)
            return true;
        Cell otherCell = (Cell) obj;
        return Objects.equals(this.getValue(), otherCell.getValue()) &&
                Objects.equals(this.getKey(), otherCell.getKey()) &&
                Objects.equals(this.getAmount(), otherCell.getAmount()) &&
                Objects.equals(this.isDeleted(), otherCell.isDeleted());
    }

    /**
     * Override of hashCode for Cell
     *
     * @return Generated hashCode
     */

    @Override
    public int hashCode() {
        return value != null ? key.hashCode() : 0;
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