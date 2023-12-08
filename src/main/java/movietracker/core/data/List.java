package movietracker.core.data;

import java.util.Objects;

/**
 * List Object class
 * The class for List object declaration
 *
 * @author Faris Salhi (30117469), Ariel Motsi ()
 * Dec. 8, 2023
 * Tutorial T06
 * @version 1.0
 */

public class List {
    // final variables for list number, type, and name
    protected final int listNum;
    private final String listType;
    private final String listName;

    /**
     * Constructor for creating an instance of a List object
     * @param listNum integer number for defining a new list
     * @param listType String for the type of list
     * @param listName String for name of list
     */
    public List(int listNum, String listType, String listName) {
        this.listNum = listNum;
        this.listType = listType;
        this.listName = listName;
    }


    // Hashcode for list duplicates based on name and type
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        List list = (List) o;
        return Objects.equals(listType, list.listType) && Objects.equals(listName, list.listName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listType, listName);
    }

    /**
     * Getter for List name
     * @return String listName
     */
    public String getName() {
        return this.listName;
    }
    /**
     * Getter for List type
     * @return String listType
     */
    public String getType() {
        return this.listType;
    }
    /**
     * Getter for List number
     * @return int listNum
     */
    public int getNum() {
        return this.listNum;
    }
}
