package movietracker.core.data;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Movie Tracker Application List Test class
 * The test class containing tests for all non-I/O and non-GUI functions in the Movie class
 *
 * @author Faris Salhi (30117469), Ariel Motsi (30147625)
 * Dec. 10, 2023
 * Tutorial T06
 * @version 1.0
 */

/**
 * ListTest class containing all junit tests for List class
 */
class ListTest {

    @Test
    void testEquals() {
        // test 2 lists of same name and same type
        List list1 = new List(1, "Favourites", "Favs");
        List list2 = new List(1, "Favourites", "Favs");
        assertEquals(list1, list2);
        // test 2 lists of same name but different type
        List list3 = new List(1, "Favourites", "Favs");
        List list4 = new List(1, "Watched", "Favs");
        assertNotEquals(list3, list4);
    }

    @Test
    void testHashCode() {
        // test 2 lists of same name and same type
        List list1 = new List(1, "Favourites", "Favs");
        List list2 = new List(1, "Favourites", "Favs");
        assertEquals(Objects.hash(list1), Objects.hash(list2));
    }

    @Test
    void getName() {
        List list = new List(1, "Favourites", "Favs");
        String actualName = list.getName();
        String expectedName = "Favs";
        assertEquals(expectedName, actualName);
    }

    @Test
    void getType() {
        List list = new List(1, "Favourites", "Favs");
        String actualType = list.getType();
        String expectedType = "Favourites";
        assertEquals(expectedType, actualType);
    }

    @Test
    void getNum() {
        List list = new List(1, "Favourites", "Favs");
        int actualNum = list.getNum();
        int expectedNum = 1;
        assertEquals(expectedNum, actualNum);
    }
}