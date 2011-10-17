package uk.org.openmentor.service

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import uk.org.openmentor.util.ResourceOpener;

class ResourceService implements ApplicationContextAware, ResourceOpener {

    static transactional = false
	
	ApplicationContext applicationContext

    public InputStream openResource(String resource) {
		return applicationContext.getResource("/WEB-INF/" + resource).getInputStream()
    }
}
