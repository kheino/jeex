package jeex.ejb.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity(name = "Topic")
@NamedQueries({
      @NamedQuery(name = TopicEntity.findAll, query = "SELECT t FROM Topic t")
})
public class TopicEntity {
   public static final String findAll = "Topic.findAll";

   @Id @GeneratedValue
   private long id;

   @NotNull
   private String name;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this)
         return true;

      if (!(obj instanceof TopicEntity))
         return false;

      TopicEntity other = (TopicEntity) obj;

      return (id == other.getId() &&
              name.equals(other.getName()));
   }

   @Override
   public int hashCode() {
      int hash = 1;
      hash = (37 * hash + (int)(id ^ (id >>> 32)));
      hash = (41 * hash + name.hashCode());
      return hash;
   }
}
