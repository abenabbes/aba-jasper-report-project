package aba.perso.project.jasper.commons;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Classe de rechergement des Beans Spring.
 * 
 * @author ali
 *
 */
public class ProjectResourcesLoader implements ResourceLoaderAware {

	private ResourceLoader resourceLoader;
	
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}

}
