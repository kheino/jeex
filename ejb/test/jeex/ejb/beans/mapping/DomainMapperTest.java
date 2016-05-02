package jeex.ejb.beans.mapping;

import jeex.ejb.domain.Discussion;
import jeex.ejb.entities.DiscussionEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class DomainMapperTest {
   private DomainMapper<Discussion, DiscussionEntity> mapper;

   @Before
   public void setUp() {
      mapper = new DomainMapper<>(Discussion.class, DiscussionEntity.class);
   }

   @Test
   public void testEntityReferenceCanBeNull() {
      Discussion d = mapper.mapToDomain(new DiscussionEntity(), new Discussion());

      assertNull(d.getTopicId());
   }
}
