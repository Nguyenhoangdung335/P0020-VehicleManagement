package interfaces;

import java.util.List;

public interface ICRUDOperations <T>{
    public Boolean addNew (T newElement);
    public Boolean deleting (T removeElement);
    public Boolean updating (T updateElement);
    public Boolean showAll ();
    public default T get (String key) {
        return null;
    }
    public default List<T> getAll () {
        return null;
    }
}