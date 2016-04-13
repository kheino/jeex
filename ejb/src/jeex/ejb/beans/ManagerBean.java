package jeex.ejb.beans;

import jeex.ejb.HasId;

import javax.ejb.EJBException;
import javax.ejb.ObjectNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class ManagerBean<T extends HasId<K>, E extends HasId<K>, K extends Serializable> {
   @PersistenceContext
   protected EntityManager em;

   protected final Class<T> domainClass;
   protected final Class<E> entityClass;

   protected ManagerBean(Class<T> domainClass, Class<E> entityClass) {
      this.domainClass = domainClass;
      this.entityClass = entityClass;
   }

   // Allow injecting a mock EntityManager for testing
   void setEntityManager(EntityManager em) {
      this.em = em;
   }

   public T find(K id) throws ObjectNotFoundException {
      return toDomain(findEntity(id));
   }

   public T create(T domainObj) {
      E entity = toEntity(domainObj);
      em.persist(entity);
      // Commit now so we can return assigned id
      em.flush();
      return toDomain(entity);
   }

   public void update(T domainObj) throws ObjectNotFoundException {
      mapToEntity(domainObj, findEntity(domainObj.getId()));
   }

   public void delete(T domainObj) throws ObjectNotFoundException {
      em.remove(findEntity(domainObj.getId()));
   }

   protected List<T> query(String queryName) {
      TypedQuery<E> q = em.createNamedQuery(queryName, entityClass);
      List<T> result = new ArrayList<>();

      q.getResultList().forEach(entity ->
            result.add(toDomain(entity)));

      return result;
   }

   protected E findEntity(K id) throws ObjectNotFoundException {
      E entity = em.find(entityClass, id);

      if (entity == null)
         throw new ObjectNotFoundException();

      return entity;
   }

   protected T toDomain(E entity) {
      try {
         return mapToDomain(entity, domainClass.newInstance());
      } catch (InstantiationException | IllegalAccessException e) {
         throw new EJBException(e);
      }
   }

   protected E toEntity(T domainObj) {
      try {
         return mapToEntity(domainObj, entityClass.newInstance());
      } catch (InstantiationException | IllegalAccessException e) {
         throw new EJBException(e);
      }
   }

   protected T mapToDomain(E entity, T domainObj) {
      for (Field src : entityClass.getDeclaredFields()) {
         // Skip field if it represents a relation
         if (isRelation(src)) {
            continue;
         }

         for (Field dest : domainClass.getDeclaredFields()) {
            if (dest.getName().equals(src.getName())) {
               map(src, entity, dest, domainObj);
               break;
            }
         }
      }

      return domainObj;
   }

   protected E mapToEntity(T domainObj, E entity) {
      for (Field dest : entityClass.getDeclaredFields()) {
         // Skip field if relation or id
         if (isRelation(dest) || isId(dest)) {
            continue;
         }

         for (Field src : domainClass.getDeclaredFields()) {
            if (src.getName().equals(dest.getName())) {
               map(src, domainObj, dest, entity);
               break;
            }
         }
      }

      return entity;
   }

   private static boolean isRelation(Field f) {
      return (f.isAnnotationPresent(OneToOne.class)
            || f.isAnnotationPresent(OneToMany.class)
            || f.isAnnotationPresent(ManyToOne.class)
            || f.isAnnotationPresent(ManyToMany.class));
   }

   private static boolean isId(Field f) {
      return f.isAnnotationPresent(Id.class);
   }

   private static void map(Field src, Object srcObj, Field dest, Object destObj) {
      try {
         src.setAccessible(true);
         dest.setAccessible(true);
         dest.set(destObj, src.get(srcObj));
      } catch (IllegalAccessException e) {
         throw new EJBException(e);
      }
   }
}
