package uk.org.openmentor.service

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import uk.org.openmentor.util.ResourceOpener;

class ResourceService implements ApplicationContextAware, ResourceOpener {

    static transactional = false
	
    ApplicationContext applicationContext

    public InputStream openResource(String resourceString) {

    	Resource resource;
        try {
        	resource = applicationContext.getResource(resourceString);
    	} catch (Exception e) {
    		log.error(e.getMessage())
    	}
        InputStream stream = resource.getInputStream();
        if (stream == null) {
        	throw new RuntimeException("Failed to load resource: " + resourceString)
        }
        return stream
    }
}
