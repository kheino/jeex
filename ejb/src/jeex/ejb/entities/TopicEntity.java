package jeex.ejb.entities;

import jeex.ejb.HasId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "Topic")
@NamedQueries({
      @NamedQuery(name = TopicEntity.findAll, query = "SELECT t FROM Topic t")
})
public class TopicEntity implements HasId<Long> {
   public static final String findAll = "Topic.findAll";

   @Id
   @GeneratedValue
   private Long id;

   @NotNull
   private String name;

   @OneToMany(mappedBy = "topic")
   private List<DiscussionEntity> discussions;

   @Override
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<DiscussionEntity> getDiscussions() {
      return discussions;
   }

   public void setDiscussions(List<DiscussionEntity> discussions) {
      this.discussions = discussions;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this)
         return true;

      if (!(obj instanceof TopicEntity))
         return false;

      TopicEntity other = (TopicEntity)obj;

      return (id == null ? other.getId() == null : id.equals(other.getId())) &&
            (name == null ? other.getName() == null : name.equals(other.getName()));
   }

   @Override
   public int hashCode() {
      int hash = 1;
      hash = (37 * hash) + (id == null ? 0 : id.hashCode());
      hash = (41 * hash) + (name == null ? 0 : name.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return (getClass().getSimpleName() + "[id:" + id + "|name:" + name + "]");
   }
}
