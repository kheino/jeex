package jeex.ejb.beans.mapping;

import jeex.ejb.HasId;

import javax.ejb.EJBException;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.lang.reflect.Field;

public class DomainMapper<T, E> {
   private final Class<T> domainClass;
   private final Class<E> entityClass;

   public DomainMapper(Class<T> domainClass, Class<E> entityClass) {
      this.domainClass = domainClass;
      this.entityClass = entityClass;
   }

   public T mapToDomain(E entity, T domainObj) {
      try {
         for (Field srcField : entityClass.getDeclaredFields()) {
            boolean useId = isRelation(srcField);

            for (Field targetField : domainClass.getDeclaredFields()) {
               if (useId) {
                  srcField.setAccessible(true);
                  Object value = srcField.get(entity);

                  if (value != null && targetField.getName().equals(srcField.getName() + "Id")) {
                     map(srcField, targetField, domainObj, ((HasId)value)::getId);
                     break;
                  }
               } else if (targetField.getName().equals(srcField.getName())) {
                  map(srcField, entity, targetField, domainObj);
                  break;
               }
            }
         }

         return domainObj;
      } catch (IllegalAccessException e) {
         // Should never happen
         throw new EJBException(e);
      }
   }

   public E mapToEntity(T domainObj, E entity) {
      for (Field targetField : entityClass.getDeclaredFields()) {
         // Skip field if relation or id
         if (isRelation(targetField) || isId(targetField)) {
            continue;
         }

         for (Field srcField : domainClass.getDeclaredFields()) {
            if (srcField.getName().equals(targetField.getName())) {
               map(srcField, domainObj, targetField, entity);
               break;
            }
         }
      }

      return entity;
   }

   private static boolean isId(Field f) {
      return f.isAnnotationPresent(Id.class);
   }

   private static boolean isRelation(Field f) {
      return (f.isAnnotationPresent(OneToOne.class)
            || f.isAnnotationPresent(OneToMany.class)
            || f.isAnnotationPresent(ManyToOne.class)
            || f.isAnnotationPresent(ManyToMany.class));
   }

   private static void map(Field srcField, Object src, Field targetField, Object target) {
      map(srcField, targetField, target, () -> srcField.get(src));
   }

   private static void map(Field srcField, Field targetField, Object target, ExceptionalSupplier<?> supplier) {
      try {
         srcField.setAccessible(true);
         targetField.setAccessible(true);
         targetField.set(target, supplier.get());
      } catch (Exception e) {
         throw new EJBException(e);
      }
   }

   @FunctionalInterface
   private interface ExceptionalSupplier<T> {
      T get() throws Exception;
   }
}
