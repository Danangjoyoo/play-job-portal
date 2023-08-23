package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class BaseRepository {
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen())
            entityManager = Persistence
                    .createEntityManagerFactory("defaultPersistenceUnit")
                    .createEntityManager();
        return entityManager;
    }

    public void persist(List<Object> objects) {;
        try {
            EntityTransaction et = entityManager.getTransaction();
            et.begin();
            if (objects != null) {
                for (Object object : objects)
                    entityManager.persist(object);
            }
            et.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public void persist() {
        this.persist(null);
    }

    public void persist(Object object) {
        this.persist(List.of(object));
    }
}
