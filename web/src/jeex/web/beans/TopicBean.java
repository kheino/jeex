package jeex.web.beans;

import jeex.ejb.beans.TopicManagerLocal;
import jeex.ejb.domain.Topic;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class TopicBean {
   @EJB
   private TopicManagerLocal ejb;

   public List<Topic> getTopics() {
      return ejb.listAll();
   }
}
