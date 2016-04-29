package jeex.ejb.entities;

import jeex.ejb.HasId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity(name = "Discussion")
@NamedQueries({
      @NamedQuery(name = DiscussionEntity.findByTopic, query = "SELECT d FROM Discussion d WHERE d.topic.id = :topicId")
})
public class DiscussionEntity implements HasId<Long> {
   public static final String findByTopic = "findByTopic";

   @Id
   @GeneratedValue
   private Long id;

   @NotNull
   private String title;

   @NotNull
   @ManyToOne
   private TopicEntity topic;

   @Override
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public TopicEntity getTopic() {
      return topic;
   }

   public void setTopic(TopicEntity topic) {
      this.topic = topic;
   }
}
