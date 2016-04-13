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
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TopicManagerBeanTest {
   @Rule
   public ExpectedException thrown = ExpectedException.none();

   private TopicManager manager;

   // Mocks
   private EntityManager em;
   private TypedQuery<TopicEntity> q;

   // Test data
   private List<TopicEntity> entities;

   private static Topic createTopic(Long id, String name) {
      Topic topic = new Topic();
      topic.setId(id);
      topic.setName(name);
      return topic;
   }

   private static TopicEntity createEntity(Long id, String name) {
      TopicEntity entity = new TopicEntity();
      entity.setId(id);
      entity.setName(name);
      return entity;
   }

   @Before
   @SuppressWarnings("unchecked")
   public void setUp() {
      em = mock(EntityManager.class);
      q = mock(TypedQuery.class);

      manager = new TopicManagerBean();
      ((ManagerBean)manager).setEntityManager(em);

      entities = new ArrayList<>();
      entities.add(createEntity(1L, "Foo"));
      entities.add(createEntity(2L, "Bar"));
      entities.add(createEntity(3L, "Baz"));
   }

   @Test
   public void testListAll() {
      when(em.createNamedQuery(TopicEntity.findAll, TopicEntity.class)).thenReturn(q);
      when(q.getResultList()).thenReturn(entities);

      List<Topic> topics = manager.listAll();

      verify(em).createNamedQuery(TopicEntity.findAll, TopicEntity.class);
      verify(q).getResultList();

      assertEquals(3, topics.size());

      assertEquals(1, topics.get(0).getId().longValue());
      assertEquals("Foo", topics.get(0).getName());

      assertEquals(2, topics.get(1).getId().longValue());
      assertEquals("Bar", topics.get(1).getName());

      assertEquals(3, topics.get(2).getId().longValue());
      assertEquals("Baz", topics.get(2).getName());
   }

   @Test
   public void testFind() throws Exception {
      when(em.find(TopicEntity.class, 1L)).thenReturn(entities.get(0));

      Topic topic = manager.find(1L);

      verify(em).find(TopicEntity.class, 1L);
      assertEquals(1, topic.getId().longValue());
      assertEquals("Foo", topic.getName());
   }

   @Test
   public void testFindThrowsObjectNotFoundExceptionOnBadId() throws Exception {
      when(em.find(TopicEntity.class, 0L)).thenReturn(null);

      thrown.expect(ObjectNotFoundException.class);
      manager.find(0L);
   }

   @Test
   public void testCreate() {
      Topic topic = createTopic(null, "Foo");
      TopicEntity entity = createEntity(null, "Foo");

      manager.create(topic);

      verify(em).persist(entity);
   }

   @Test
   public void testCreateIgnoresProvidedId() {
      Topic topic = createTopic(1L, "");
      TopicEntity entity = createEntity(null, "");

      manager.create(topic);

      verify(em).persist(entity);
   }

   @Test
   public void testCreateReturnsNewTopicWithAssignedId() {
      Topic topic = createTopic(null, "");
      TopicEntity entity = createEntity(null, "");

      // Simulate id assignment
      doAnswer(invocation -> {
         TopicEntity ent = (TopicEntity)invocation.getArguments()[0];
         ent.setId(1L);
         return null;
      }).when(em).persist(entity);

      Topic created = manager.create(topic);

      assertNotSame(topic, created);
      assertEquals(1, created.getId().longValue());
   }

   @Test
   public void testUpdate() throws Exception {
      Topic topic = createTopic(1L, "Qux");
      TopicEntity entity = entities.get(0);

      when(em.find(TopicEntity.class, 1L)).thenReturn(entity);

      manager.update(topic);

      verify(em).find(TopicEntity.class, 1L);
      assertEquals("Qux", entity.getName());
   }

   @Test
   public void testUpdateThrowsObjectNotFoundExceptionOnBadId() throws Exception {
      Topic topic = createTopic(0L, "");

      when(em.find(TopicEntity.class, 0L)).thenReturn(null);

      thrown.expect(ObjectNotFoundException.class);
      manager.update(topic);
   }

   @Test
   public void testDelete() throws Exception {
      Topic topic = createTopic(1L, "");
      TopicEntity entity = entities.get(0);

      when(em.find(TopicEntity.class, 1L)).thenReturn(entity);

      manager.delete(topic);

      verify(em).find(TopicEntity.class, 1L);
      verify(em).remove(entity);
   }

   @Test
   public void testDeleteThrowsObjectNotFoundExceptionOnBadId() throws Exception {
      Topic topic = createTopic(0L, "");

      when(em.find(TopicEntity.class, 0L)).thenReturn(null);

      thrown.expect(ObjectNotFoundException.class);
      manager.delete(topic);
   }
}
