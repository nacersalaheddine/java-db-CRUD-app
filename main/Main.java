package main;
import dao.DaoInterface;
import dao.DAOFactory;
import java.util.Optional;
import beans.Entity;

public class Main {

    private static DaoInterface<Entity> entityDaoImpl;
 
    public static void main(String[] args) throws Exception{
        
        entityDaoImpl = DAOFactory.getInstance().getEntityDao();
       // entityDaoImpl.showAll();
       // entityDaoImpl.create(new Entity("John", "john@domain.com"));
       // entityDaoImpl.showAll();
        Entity entity = getUser(50);
        System.out.println(entity.getName());
        System.out.println(entity.getEmail());
        //entityDaoImpl.delete(2);
        entityDaoImpl.update(50, new String[]{"blaky", "black@domain.com"});

    }
  
    private static Entity getUser(long id) {
        Optional<Entity> entity = entityDaoImpl.get(id);
        
        return entity.orElseGet(
          () -> new Entity("non-existing user", "no-email"));
    }

}