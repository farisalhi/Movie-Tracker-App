package movietracker.core.data;

import java.util.Objects;

public class List {
    protected final int listNum;
    private final String listType;
    private final String listName;

    public List(int listNum, String listType, String listName) {
        this.listNum = listNum;
        this.listType = listType;
        this.listName = listName;
    }

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

    public String getName() {
        return this.listName;
    }
    public String getType() {
        return this.listType;
    }
    public int getNum() {
        return this.listNum;
    }
}
