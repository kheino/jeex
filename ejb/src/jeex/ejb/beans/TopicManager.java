package jeex.ejb.beans;

import jeex.ejb.domain.Topic;
import jeex.ejb.entities.TopicEntity;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@WebService(name = "topic")
public class TopicManager implements TopicManagerLocal, TopicManagerRemote {
   @PersistenceContext
   private EntityManager em;

   void setEntityManager(EntityManager em) {
      this.em = em;
   }

   @Override
   public List<Topic> listAll() {
      TypedQuery<TopicEntity> q = em.createNamedQuery(TopicEntity.findAll, TopicEntity.class);
      List<Topic> result = new ArrayList<>();

      q.getResultList().forEach(entity ->
            result.add(toDomain(entity)));

      return result;
   }

   private Topic toDomain(TopicEntity entity) {
      Topic topic = new Topic();
      topic.setId(entity.getId());
      topic.setName(entity.getName());
      return topic;
   }
}
