package tn.esprit.esprite_learn.IService;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IService<T> {
    public void add(T t) throws Exception;
    public ArrayList<T> display();
    public void update(T t);
    public void delete(T t);

}