package jeex.ejb.beans;

import jeex.ejb.domain.Topic;

import javax.ejb.ObjectNotFoundException;
import java.util.List;

public interface TopicManagerCommon {
   List<Topic> listAll();

   Topic find(long id) throws ObjectNotFoundException;

   long persist(Topic topic);

   void update(Topic topic) throws ObjectNotFoundException;

   void delete(Topic topic) throws ObjectNotFoundException;
}
