package jeex.ejb.beans;

import jeex.ejb.domain.Topic;

import javax.ejb.ObjectNotFoundException;
import java.util.List;

public interface TopicManager {
   List<Topic> listAll();

   Topic find(Long id) throws ObjectNotFoundException;

   Topic create(Topic topic);

   void update(Topic topic) throws ObjectNotFoundException;

   void delete(Topic topic) throws ObjectNotFoundException;
}
