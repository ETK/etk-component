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

package org.etk.core.rest.impl.resource;

/**
 * Describe the Path annotation, see {@link javax.ws.rs.Path}.
 * 
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public class PathValue {

  /**
   * URI template, see {@link javax.ws.rs.Path#value()} .
   */
  private final String  path;

  /**
   * @param path URI template
   */
  public PathValue(String path) {
    this.path = path;
  }

  /**
   * @return URI template string
   */
  public String getPath() {
    return path;
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {
    return "( " + path + " )";
  }

}
