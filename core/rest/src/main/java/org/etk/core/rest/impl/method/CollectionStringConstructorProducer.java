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

package org.etk.core.rest.impl.method;

import java.lang.reflect.Constructor;

/**
 * Produce collections each element of it is object which has constructor with
 * single String argument.
 * 
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public final class CollectionStringConstructorProducer extends BaseCollectionProducer {

  /**
   * This constructor will be used for creation collection elements.
   */
  private Constructor<?> constructor;

  /**
   * Constructs new instance of CollectionStringConstructorProducer.
   * 
   * @param collectionClass class of collection which must be created
   * @param constructor this constructor will be used for produce elements of
   *          collection
   */
  CollectionStringConstructorProducer(Class<?> collectionClass, Constructor<?> constructor) {
    super(collectionClass);
    this.constructor = constructor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object createValue(String value) throws Exception {
    if (value == null)
      return null;

    return constructor.newInstance(value);
  }

}
