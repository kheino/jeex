package jeex.ejb.beans;

import jeex.ejb.domain.Topic;
import jeex.ejb.entities.TopicEntity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ejb.ObjectNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TopicManagerBeanTest {
   private TopicManagerBean manager;

   // Mocks
   private EntityManager em;
   private TypedQuery<TopicEntity> q;

   // Test data
   private List<TopicEntity> entities;

   @Rule
   public ExpectedException thrown = ExpectedException.none();

   @Before
   @SuppressWarnings("unchecked")
   public void setUp() {
      em = mock(EntityManager.class);
      q = mock(TypedQuery.class);

      manager = new TopicManagerBean();
      manager.setEntityManager(em);

      // Populate entities list
      entities = new ArrayList<>();
      entities.add(createEntity(1, "Foo"));
      entities.add(createEntity(2, "Bar"));
      entities.add(createEntity(3, "Baz"));
   }

   private static TopicEntity createEntity(long id, String name) {
      TopicEntity entity = new TopicEntity();
      entity.setId(id);
      entity.setName(name);
      return entity;
   }

   private static Topic createTopic(long id, String name) {
      Topic topic = new Topic();
      topic.setId(id);
      topic.setName(name);
      return topic;
   }

   @Test
   public void testListAll() {
      when(em.createNamedQuery(TopicEntity.findAll, TopicEntity.class)).thenReturn(q);
      when(q.getResultList()).thenReturn(entities);

      List<Topic> topics = manager.listAll();

      verify(em).createNamedQuery(TopicEntity.findAll, TopicEntity.class);
      verify(q).getResultList();

      assertEquals(3, topics.size());

      assertEquals(1, topics.get(0).getId());
      assertEquals("Foo", topics.get(0).getName());

      assertEquals(2, topics.get(1).getId());
      assertEquals("Bar", topics.get(1).getName());

      assertEquals(3, topics.get(2).getId());
      assertEquals("Baz", topics.get(2).getName());
   }

   @Test
   public void testFind() throws Exception {
      when(em.find(TopicEntity.class, 1L)).thenReturn(entities.get(0));

      Topic topic = manager.find(1);

      verify(em).find(TopicEntity.class, 1L);
      assertEquals(1, topic.getId());
      assertEquals("Foo", topic.getName());
   }

   @Test
   public void testFindThrowsObjectNotFoundExceptionOnBadId() throws Exception {
      when(em.find(TopicEntity.class, 0L)).thenReturn(null);

      thrown.expect(ObjectNotFoundException.class);
      manager.find(0);
   }

   @Test
   public void testPersist() {
      Topic topic = createTopic(0, "Foo");
      TopicEntity entity = createEntity(0, "Foo");

      manager.persist(topic);

      verify(em).persist(entity);
   }

   @Test
   public void testPersistIgnoresTopicId() {
      Topic topic = createTopic(1, "");
      TopicEntity entity = createEntity(0, "");

      long assignedId = manager.persist(topic);

      verify(em).persist(entity);
      // Since no id is actually assigned, it remains 0
      assertEquals(0, assignedId);
   }

   @Test
   public void testUpdate() throws Exception {
      Topic topic = createTopic(1, "Qux");
      TopicEntity entity = entities.get(0);

      when(em.find(TopicEntity.class, 1L)).thenReturn(entity);

      manager.update(topic);

      verify(em).find(TopicEntity.class, 1L);
      assertEquals("Qux", entity.getName());
   }

   @Test
   public void testUpdateThrowsObjectNotFoundExceptionOnBadId() throws Exception {
      Topic topic = createTopic(0, "");

      when(em.find(TopicEntity.class, 0L)).thenReturn(null);

      thrown.expect(ObjectNotFoundException.class);
      manager.update(topic);
   }

   @Test
   public void testDelete() throws Exception {
      Topic topic = createTopic(1, "");
      TopicEntity entity = entities.get(0);

      when(em.find(TopicEntity.class, 1L)).thenReturn(entity);

      manager.delete(topic);

      verify(em).find(TopicEntity.class, 1L);
      verify(em).remove(entity);
   }

   @Test
   public void testDeleteThrowsObjectNotFoundExceptionOnBadId() throws Exception {
      Topic topic = createTopic(0, "");

      when(em.find(TopicEntity.class, 0L)).thenReturn(null);

      thrown.expect(ObjectNotFoundException.class);
      manager.delete(topic);
   }
}
