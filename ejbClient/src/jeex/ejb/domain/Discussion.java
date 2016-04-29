package jeex.ejb.domain;

import jeex.ejb.HasId;

import java.io.Serializable;
import java.util.List;

public class Discussion implements HasId<Long>, Serializable {
   private Long id;

   private String title;

   private List<Post> posts;

   private Long topicId;

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

   public List<Post> getPosts() {
      return posts;
   }

   public void setPosts(List<Post> posts) {
      this.posts = posts;
   }

   public Long getTopicId() {
      return topicId;
   }

   public void setTopicId(Long topicId) {
      this.topicId = topicId;
   }
}
