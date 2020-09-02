package dao;

import java.util.List;
import java.util.Optional;

public interface DaoInterface<T>{

    Optional<T> get(long id);
    
    List<T> getAll();

    void showAll();
    
    void create(T t);
    
    void update(long t, String[] params);
    
    void delete(long t);

    public void close();
}