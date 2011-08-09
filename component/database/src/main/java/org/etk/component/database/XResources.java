/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
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
package org.etk.component.database;

import java.util.HashMap;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 22, 2004
 * @version $Id: XResources.java 5332 2006-04-29 18:32:44Z geaz $
 */
@SuppressWarnings("serial")
public class XResources extends HashMap<Class, Object> {

  public Object getResource(Class cl) {
    return get(cl);
  }

  public XResources addResource(Class cl, Object resource) {
    put(cl, resource);
    return this;
  }

  public Object removeResource(Class cl) {
    return remove(cl);
  }
}
