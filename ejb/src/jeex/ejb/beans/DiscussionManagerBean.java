package jeex.ejb.beans;

import jeex.ejb.domain.Discussion;
import jeex.ejb.domain.Topic;
import jeex.ejb.entities.DiscussionEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class DiscussionManagerBean extends ManagerBean<Discussion, DiscussionEntity, Long>
      implements DiscussionManagerLocal, DiscussionManagerRemote {

   public DiscussionManagerBean() {
      super(Discussion.class, DiscussionEntity.class);
   }

   @Override
   public List<Discussion> listByTopic(Topic topic) {
      TypedQuery<DiscussionEntity> q = createQuery(DiscussionEntity.findByTopic);
      q.setParameter("topicId", topic.getId());
      return query(q);
   }
}
