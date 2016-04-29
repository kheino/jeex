package jeex.ejb.domain;

import jeex.ejb.HasId;

import java.io.Serializable;

public class Post implements HasId<Long>, Serializable {
   private Long id;

   private String body;

   @Override
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }
}
