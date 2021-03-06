/**
 * Copyright (C) 2003-2008 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.etk.core.rest.impl.provider;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.etk.common.logging.Logger;
import org.etk.core.rest.impl.header.MediaTypeHelper;
import org.etk.kernel.container.component.ComponentPlugin;

@Provider
@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_XHTML_XML })
@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_XHTML_XML, MediaTypeHelper.WADL })
public class JAXBContextResolver implements ContextResolver<JAXBContextResolver> {

  /**
   * Logger.
   */
  private static final Logger LOG = Logger.getLogger(JAXBContextResolver.class);

  /**
   * JAXBContext cache.
   */
  @SuppressWarnings("unchecked")
  private final ConcurrentHashMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();

  /**
   * {@inheritDoc}
   */
  public JAXBContextResolver getContext(Class<?> type) {
    return this;
  }

  /**
   * Return JAXBContext according to supplied type. If no one context found then
   * try create new context and save it in cache.
   * 
   * @param classes classes to be bound
   * @return JAXBContext
   * @throws JAXBException if JAXBContext creation failed
   */
  public JAXBContext getJAXBContext(Class<?> clazz) throws JAXBException {
    JAXBContext jaxbctx = jaxbContexts.get(clazz);
    if (jaxbctx == null) {
      jaxbctx = JAXBContext.newInstance(clazz);
      jaxbContexts.put(clazz, jaxbctx);
    }
    return jaxbctx;
  }

  /**
   * Create and add in cache JAXBContext for supplied set of classes.
   * 
   * @param classes set of java classes to be bound
   * @return JAXBContext
   * @throws JAXBException if JAXBContext for supplied classes can't be created
   *           in any reasons
   */
  public JAXBContext createJAXBContext(Class<?> clazz) throws JAXBException {
    JAXBContext jaxbctx = JAXBContext.newInstance(clazz);
    addJAXBContext(jaxbctx, clazz);
    return jaxbctx;
  }

  /**
   * Add prepared JAXBContext that will be mapped to set of class. In this case
   * this class works as cache for JAXBContexts.
   * 
   * @param jaxbctx JAXBContext
   * @param classes set of java classes to be bound
   */
  public void addJAXBContext(JAXBContext jaxbctx, Class<?> clazz) {
    jaxbContexts.put(clazz, jaxbctx);
  }

  /**
   * @param plugin for injection prepared JAXBContext at startup
   */
  public void addPlugin(ComponentPlugin plugin) {
    if (plugin instanceof JAXBContextComponentPlugin) {
      for (Iterator<Class<?>> i = ((JAXBContextComponentPlugin) plugin).getJAXBContexts()
                                                                       .iterator(); i.hasNext();) {
        Class<?> c = i.next();
        try {
          createJAXBContext(c);
        } catch (JAXBException e) {
          LOG.error("Failed add JAXBContext for class " + c.getName(), e);
        }
      }
    }
  }

}
