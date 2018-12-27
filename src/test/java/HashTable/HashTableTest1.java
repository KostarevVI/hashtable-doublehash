package HashTable;

import org.junit.After;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class HashTableTest1 {

    HashTable firstTable = new HashTable();
    HashTable secondTable = new HashTable();
    HashTable thirdTable = new HashTable();

    @After
    public void after() {
        firstTable.clear();
        secondTable.clear();
        thirdTable.clear();
        System.out.println("---------------------");
    }

    @Test
    public void put() throws Exception {
        firstTable.put(1, 990);
        firstTable.print();
        assertTrue(firstTable.containsValue(990));
        assertTrue(firstTable.containsKey(1));
        firstTable.put(1, 990);
        firstTable.put(1, 990);
        assertEquals((Integer) 3, firstTable.getCell(1).getAmount());
        firstTable.remove(1);
        firstTable.put(2, 16);
        firstTable.put(3, 32);
        firstTable.put(6, 64);
        firstTable.print();
        Cell testCell = new Cell(1, 990);
        testCell.incAmount();
        assertTrue(firstTable.getCell(1).equals(testCell) &&
                firstTable.getCell(2).equals(new Cell(2, 16)) &&
                firstTable.getCell(3).equals(new Cell(3, 32)) &&
                firstTable.getCell(6).equals(new Cell(6, 64)));
        assertFalse(firstTable.containsKey(0));
        firstTable.put("foo", "bar");
        assertTrue(firstTable.containsKey("foo"));
    }

    @Test
    public void delete() throws Exception {
        firstTable.put(1, 99);
        firstTable.remove(1);
        firstTable.put(2, 0);
        firstTable.remove(2);
        assertFalse(firstTable.containsKey(1));
        assertFalse(firstTable.containsKey(2));
        firstTable.put(2, 17);
        firstTable.print();
        assertTrue(firstTable.containsValue(17)); //заменяет удаленную ранее ячейку с цифрой 1
        firstTable.put(2, 8);
        firstTable.print();
        assertTrue(firstTable.containsValue(8));
    }

    @Test
    public void getSize() throws Exception {
        for (int i = 0; i < 100; i++) {
            firstTable.put(i, i + 1);
        }
        assertEquals(100, firstTable.size());
    }

    @Test
    public void containsKey() throws Exception {
        firstTable.put(6, 10);
        firstTable.put(256, 15);
        firstTable.put(256, 15);
        firstTable.print();
        firstTable.remove(256);
        firstTable.print();
        assertTrue(firstTable.containsKey(6) && firstTable.getCell(256).getAmount() == 1);
        assertTrue(firstTable.containsKey(256));
        assertFalse(firstTable.containsKey(999));
    }

    @Test
    public void clear() throws Exception {
        firstTable.put(1, 0);
        firstTable.put(9998, 939999);
        firstTable.print();
        firstTable.clear();
        firstTable.print();
        assertFalse(firstTable.containsKey(1));
        assertFalse(firstTable.containsKey(9999));
    }

    @Test
    public void isEmpty() throws Exception {
        firstTable.put(2, 2);
        assertFalse(firstTable.isEmpty());
        firstTable.remove(2);
        firstTable.print();
        assertTrue(firstTable.isEmpty());
        assertTrue(secondTable.isEmpty());
    }

    @Test
    public void equals() throws Exception {
        int notHashtable = 13;
        firstTable.put(256, 1);
        secondTable.put(256, 1);
        firstTable.put(5, 2);
        secondTable.put(5, 2);
        firstTable.put(1, 3);
        secondTable.put(1, 3);
        firstTable.put(2, 4);
        firstTable.print();
        secondTable.print();
        assertFalse(firstTable.equals(thirdTable));
        assertFalse(firstTable.equals(secondTable)); //первая таблица содержит все элементы второй и что-то ещё
        secondTable.remove(1);
        assertFalse(firstTable.equals(secondTable));
        firstTable.remove(1);
        firstTable.remove(2);
        firstTable.print();
        secondTable.print();
        assertTrue(firstTable.equals(secondTable));
        assertFalse(firstTable.equals(null));
        assertFalse(firstTable.equals(notHashtable));
    }

    @Test
    public void rehash() throws Exception {
        for (int i = 0; i < 50; i++)
            firstTable.put(i, i + 1);
        firstTable.print();
        assertTrue(firstTable.containsKey(1));
        assertEquals(50, firstTable.size());
    }

    @Test
    public void get() {
        firstTable.put(11, 5);
        firstTable.print();
        assertEquals((Integer) 5, firstTable.getCell(11).getValue());
        assertNull(firstTable.get(1));
        assertNull(firstTable.get(100));
    }

    @Test
    public void toStringTest() throws Exception {
        firstTable.put(1, 3);
        firstTable.put(2, 4);
        firstTable.put(4, 2);
        firstTable.put(3, 1);
        firstTable.print();
        assertEquals("[2, 1, 4, 3]", firstTable.toString());
    }

    @Test
    public void cloneTest() {
        for (int i = 0; i < 40; i++) {
            firstTable.put(i, i + 1);
        }
        for (int i = 0; i < 40; i++) {
            if (i % 2 == 0) {
                firstTable.remove(i);
            }
        }
        for (int i = 1; i < 46; i++) {
            firstTable.put(i, i + 1);
        }
        firstTable.print();
        secondTable = firstTable.clone();
        assertEquals(firstTable, secondTable);
        firstTable.hashCode();
    }

    @Test
    public void iteratorsTest() {
        firstTable.put(1, 3);
        firstTable.put(2, 4);
        Set set = firstTable.entrySet();
        assertTrue(set.contains(new Cell(1, 3)));
        firstTable.put(4, 2);
        firstTable.put(3, 1);
        assertTrue(set.contains(new Cell(3, 1)));
        assertFalse(set.contains(new Cell(4, 0)));
        firstTable.put(3, 15);
        assertTrue(set.contains(new Cell(3, 15)));
        assertFalse(set.contains(new Cell(3, 1)));

        int sum;

        sum = 0;
        for(Object i: set){
            System.out.println(i + " ");
            sum++;
        }
        System.out.println();
        assertEquals(4, sum);

        sum = 0;
        for(Object i: firstTable.keySet()){
            System.out.print(i + " ");
            sum++;
        }
        System.out.println();
        assertEquals(4, sum);

        sum = 0;
        for(Object i: firstTable.values()){
            System.out.print(i + " ");
            sum++;
        }
        System.out.println();
        assertEquals(4, sum);
    }
}