package jeex.ws.impl;


import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import jeex.ws.*;
import javax.jws.WebService;

@WebService(serviceName = "TopicManagerService", endpointInterface = "jeex.ws.Topic", targetNamespace = "http://beans.ejb.jeex/")
public class TopicImpl implements Topic {
	public java.util.List<jeex.ws.Topic_Type> listTopics() {
		return null;
	}
}