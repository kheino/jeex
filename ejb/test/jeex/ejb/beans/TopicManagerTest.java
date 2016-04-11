package jeex.ejb.beans;

import jeex.ejb.domain.Topic;
import jeex.ejb.entities.TopicEntity;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TopicManagerTest {
   private TopicManager manager;

   // Mocks
   private EntityManager em;
   private TypedQuery<TopicEntity> q;

   // Test data
   private List<TopicEntity> entities;

   @Before
   @SuppressWarnings("unchecked")
   public void setUp() {
      em = mock(EntityManager.class);
      q = mock(TypedQuery.class);

      manager = new TopicManager();
      manager.setEntityManager(em);

      // Populate entities list
      entities = new ArrayList<>();
      entities.add(createEntity(1, "Foo"));
      entities.add(createEntity(2, "Bar"));
      entities.add(createEntity(3, "Baz"));
   }

   private TopicEntity createEntity(long id, String name) {
      TopicEntity entity = new TopicEntity();
      entity.setId(id);
      entity.setName(name);
      return entity;
   }

   @Test
   public void testListAll() {
      when(em.createNamedQuery(TopicEntity.findAll, TopicEntity.class)).thenReturn(q);
      when(q.getResultList()).thenReturn(entities);

      List<Topic> actual = manager.listAll();

      assertEquals(3, actual.size());

      assertEquals(1, actual.get(0).getId());
      assertEquals("Foo", actual.get(0).getName());

      assertEquals(2, actual.get(1).getId());
      assertEquals("Bar", actual.get(1).getName());

      assertEquals(3, actual.get(2).getId());
      assertEquals("Baz", actual.get(2).getName());
   }
}
