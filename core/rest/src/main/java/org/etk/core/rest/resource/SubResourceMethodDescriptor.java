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

package org.etk.core.rest.resource;

import javax.ws.rs.Path;

import org.etk.core.rest.impl.resource.PathValue;
import org.etk.core.rest.impl.uri.UriPattern;


/**
 * Describe sub-resource method. Sub-resource method is
 * {@link java.lang.reflect.Method} of resource class which has own {@link Path}
 * annotation and {@link javax.ws.rs.HttpMethod} annotation. This method can't
 * handle request directly.
 * 
 */
public interface SubResourceMethodDescriptor extends ResourceMethodDescriptor {

  /**
   * @return See {@link PathValue}
   */
  PathValue getPathValue();

  /**
   * @return See {@link UriPattern}
   */
  UriPattern getUriPattern();

}
