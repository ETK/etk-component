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

import java.util.TreeMap;

import org.etk.core.rest.impl.uri.UriPattern;

/**
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public class SubResourceMethodMap
                          extends
                          TreeMap<UriPattern, ResourceMethodMap<SubResourceMethodDescriptor>> {
  
  /**
   * Serial version UID.
   */
  private static final long serialVersionUID = 4083992147354775165L;

  public SubResourceMethodMap() {
    super(UriPattern.URIPATTERN_COMPARATOR);
  }

  public ResourceMethodMap<SubResourceMethodDescriptor> getMethodMap(UriPattern uriPattern) {
    ResourceMethodMap<SubResourceMethodDescriptor> m = get(uriPattern);
    if (m == null) {
      m = new ResourceMethodMap<SubResourceMethodDescriptor>();
      put(uriPattern, m);
    }
    return m;
  }
  
  public void sort() {
    for (ResourceMethodMap<SubResourceMethodDescriptor> srmd : values())
      srmd.sort();
  }
  
}

