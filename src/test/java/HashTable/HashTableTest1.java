package HashTable;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashTableTest1 {

    HashTable firstTable = new HashTable();
    HashTable secondTable = new HashTable();
    HashTable thirdTable = new HashTable();

    @After
    public void after() {
        firstTable.clear();
        secondTable.clear();
        System.out.println("---------------------");
    }

    @Test
    public void push() throws Exception {
        firstTable.push(990);
        firstTable.print();
        assertTrue(firstTable.contains(990));
        firstTable.push(990);
        firstTable.push(990);
        firstTable.delete(990);
        firstTable.push(16);
        firstTable.push(32);
        firstTable.push(64);
        firstTable.print();
        assertTrue(firstTable.contains(990) && firstTable.contains(16) &&
                firstTable.contains(32) && firstTable.contains(64));
        assertFalse(firstTable.contains(0));
    }

    @Test
    public void delete() throws Exception {
        firstTable.push(1);
        firstTable.delete(1);
        firstTable.push(2);
        firstTable.delete(2);
        firstTable.push(17);
        firstTable.print();
        assertFalse(firstTable.contains(1));
        assertFalse(firstTable.contains(2));
        assertTrue(firstTable.contains(17)); //заменяет удаленную ранее ячейку с цифрой 1
        firstTable.push(1);
        firstTable.print();
        assertTrue(firstTable.contains(1));
    }

    @Test
    public void getSize() throws Exception {
        for (int i = 0; i < 100; i++) {
            firstTable.push(i);
        }
        assertEquals(128, firstTable.getSize());
    }

    @Test
    public void contains() throws Exception {
        firstTable.push(6);
        firstTable.push(256);
        firstTable.push(256);
        firstTable.print();
        firstTable.delete(256);
        firstTable.print();
        assertTrue(firstTable.contains(6));
        assertTrue(firstTable.contains(256));
        assertFalse(firstTable.contains(999));
    }

    @Test
    public void clear() throws Exception {
        firstTable.push(1);
        firstTable.push(9998);
        firstTable.print();
        firstTable.clear();
        firstTable.print();
        assertFalse(firstTable.contains(1));
        assertFalse(firstTable.contains(9999));
    }

    @Test
    public void isEmpty() throws Exception {
        firstTable.push(2);
        assertFalse(firstTable.isEmpty());
        firstTable.delete(2);
        firstTable.print();
        assertTrue(firstTable.isEmpty());
        assertTrue(secondTable.isEmpty());
    }

    @Test
    public void equals() throws Exception {
        int notHashtable = 13;
        firstTable.push(256);
        secondTable.push(256);
        firstTable.push(5);
        secondTable.push(5);
        firstTable.push(1);
        secondTable.push(1);
        firstTable.push(2);
        firstTable.print();
        secondTable.print();
        assertFalse(firstTable.equals(thirdTable));
        assertFalse(firstTable.equals(secondTable)); //первая таблица содержит все элементы второй и что-то ещё
        secondTable.delete(1);
        assertFalse(firstTable.equals(secondTable));
        firstTable.delete(1);
        firstTable.delete(2);
        firstTable.print();
        secondTable.print();
        assertTrue(firstTable.equals(secondTable));
        assertFalse(firstTable.equals(null));
        assertFalse(firstTable.equals(notHashtable));
    }

    @Test
    public void rehash() throws Exception {
        for (int i = 0; i < 50; i++)
            firstTable.push(i);
        firstTable.print();
        assertTrue(firstTable.contains(1));
        assertEquals(64, firstTable.getSize());
    }

    @Test
    public void get() {
        firstTable.push(5);
        firstTable.print();
        assertEquals((Integer) 5 ,firstTable.get(11));
        assertNull(firstTable.get(1));
        assertNull(firstTable.get(100));
    }

    @Test
    public void toStringTest() throws Exception {
        firstTable.push(1);
        firstTable.push(2);
        firstTable.push(4);
        firstTable.push(3);
        firstTable.print();
        assertEquals("[4, 3, 2, 1]", firstTable.toString());
    }

    @Test
    public void cloneTest() {
        for(int i=0;i<40;i++){
            firstTable.push(i);
        }
        for(int i=0;i<40;i++){
            if(i%2==0){
                firstTable.delete(i);
            }
        }
        for(int i=35;i<46;i++){
            firstTable.push(i);
        }
        firstTable.print();
        secondTable = firstTable.clone();
        assertEquals(firstTable, secondTable);
    }
}