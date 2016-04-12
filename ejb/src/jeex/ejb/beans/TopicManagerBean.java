package jeex.ejb.beans;

import jeex.ejb.domain.Topic;
import jeex.ejb.entities.TopicEntity;

import javax.ejb.ObjectNotFoundException;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
@WebService(name = "topic")
public class TopicManagerBean implements TopicManagerLocal, TopicManagerRemote {
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

   @Override
   public Topic find(long id) throws ObjectNotFoundException {
      return toDomain(findEntity(id));
   }

   @Override
   public long persist(Topic topic) {
      TopicEntity entity = toEntity(topic);
      em.persist(entity);
      em.flush();
      return entity.getId();
   }

   @Override
   public void update(Topic topic) throws ObjectNotFoundException {
      map(topic, findEntity(topic.getId()));
   }

   @Override
   public void delete(Topic topic) throws ObjectNotFoundException {
      em.remove(findEntity(topic.getId()));
   }

   private TopicEntity findEntity(long id) throws ObjectNotFoundException {
      TopicEntity entity = em.find(TopicEntity.class, id);

      if (entity == null)
         throw new ObjectNotFoundException();

      return entity;
   }

   private Topic toDomain(TopicEntity entity) {
      return map(entity, new Topic());
   }

   private TopicEntity toEntity(Topic topic) {
      return map(topic, new TopicEntity());
   }

   private Topic map(TopicEntity entity, Topic topic) {
      topic.setId(entity.getId());
      topic.setName(entity.getName());
      return topic;
   }

   private TopicEntity map(Topic topic, TopicEntity entity) {
      entity.setName(topic.getName());
      return entity;
   }
}
