package jeex.ejb.beans;

import jeex.ejb.domain.Discussion;
import jeex.ejb.domain.Topic;

import javax.ejb.ObjectNotFoundException;
import java.util.List;

public interface DiscussionManager {
   List<Discussion> listByTopic(Topic topic);

   Discussion find(Long id) throws ObjectNotFoundException;

   Discussion create(Discussion discussion);

   void update(Discussion discussion) throws ObjectNotFoundException;

   void delete(Discussion discussion) throws ObjectNotFoundException;
}
