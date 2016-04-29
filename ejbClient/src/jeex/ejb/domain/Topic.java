package jeex.ejb.domain;

import jeex.ejb.HasId;

import java.io.Serializable;
import java.util.List;

public class Topic implements HasId<Long>, Serializable {
   private Long id;

   private String name;

   private List<Discussion> discussions;

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

   public List<Discussion> getDiscussions() {
      return discussions;
   }

   public void setDiscussions(List<Discussion> discussions) {
      this.discussions = discussions;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this)
         return true;

      if (!(obj instanceof Topic))
         return false;

      Topic other = (Topic)obj;

      return (id == null ? other.id == null : id.equals(other.id)) &&
            (name == null ? other.name == null : name.equals(other.name)) &&
            (discussions == null ? other.discussions == null : discussions.equals(other.discussions));
   }

   @Override
   public int hashCode() {
      int hash = 1;
      hash = (11 * hash) + (id == null ? 0 : id.hashCode());
      hash = (13 * hash) + (name == null ? 0 : name.hashCode());
      hash = (17 * hash) + (discussions == null ? 0 : discussions.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return (getClass().getSimpleName() + "[id:" + id + "|name:" + name + "]");
   }
}
