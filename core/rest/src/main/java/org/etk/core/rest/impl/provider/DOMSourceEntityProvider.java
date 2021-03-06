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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.etk.common.logging.Logger;
import org.etk.core.rest.provider.EntityProvider;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
@Provider
@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_XHTML_XML })
@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_XHTML_XML })
public class DOMSourceEntityProvider implements EntityProvider<DOMSource> {

  /**
   * Logger.
   */
  private static final Logger LOG = Logger.getLogger(DOMSourceEntityProvider.class);
  
  /**
   * {@inheritDoc}
   */
  public boolean isReadable(Class<?> type,
                            Type genericType,
                            Annotation[] annotations,
                            MediaType mediaType) {
    return type == DOMSource.class;
  }

  /**
   * {@inheritDoc}
   */
  public DOMSource readFrom(Class<DOMSource> type,
                            Type genericType,
                            Annotation[] annotations,
                            MediaType mediaType,
                            MultivaluedMap<String, String> httpHeaders,
                            InputStream entityStream) throws IOException {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(true);
      Document d = factory.newDocumentBuilder().parse(entityStream);
      return new DOMSource(d);
    } catch (SAXParseException saxpe) {
      // if can't read from stream (e.g. steam is empty)
      if (LOG.isDebugEnabled())
        saxpe.printStackTrace();
      return null;
    } catch (SAXException saxe) {
      throw new IOException("Can't read from input stream " + saxe);
    } catch (ParserConfigurationException pce) {
      throw new IOException("Can't read from input stream " + pce);
    }
  }

  /**
   * {@inheritDoc}
   */
  public long getSize(DOMSource t,
                      Class<?> type,
                      Type genericType,
                      Annotation[] annotations,
                      MediaType mediaType) {
    return -1;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isWriteable(Class<?> type,
                             Type genericType,
                             Annotation[] annotations,
                             MediaType mediaType) {
    return DOMSource.class.isAssignableFrom(type);
  }

  /**
   * {@inheritDoc}
   */
  public void writeTo(DOMSource t,
                      Class<?> type,
                      Type genericType,
                      Annotation[] annotations,
                      MediaType mediaType,
                      MultivaluedMap<String, Object> httpHeaders,
                      OutputStream entityStream) throws IOException {
    StreamResult out = new StreamResult(entityStream);
    try {
      TransformerFactory.newInstance().newTransformer().transform(t, out);
    } catch (TransformerConfigurationException e) {
      throw new IOException("Can't write to output stream " + e);
    } catch (TransformerException e) {
      throw new IOException("Can't write to output stream " + e);
    } catch (TransformerFactoryConfigurationError e) {
      throw new IOException("Can't write to output stream " + e);
    }
  }
}
