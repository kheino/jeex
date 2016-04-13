package jeex.ejb.beans;

import jeex.ejb.domain.Topic;
import jeex.ejb.entities.TopicEntity;

import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;

@Stateless
@WebService(name = "topic", endpointInterface = "jeex.ejb.beans.TopicManagerWs")
public class TopicManagerBean extends ManagerBean<Topic, TopicEntity, Long>
      implements TopicManagerLocal, TopicManagerRemote, TopicManagerWs {

   public TopicManagerBean() {
      super(Topic.class, TopicEntity.class);
   }

   @Override
   public List<Topic> listAll() {
      return query(TopicEntity.findAll);
   }
}
