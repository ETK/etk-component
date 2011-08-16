/*
 * Copyright (C) 2010 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.etk.component.database.creator;

/**
 * @author <a href="anatoliy.bazko@exoplatform.org">Anatoliy Bazko</a>
 * @version $Id: DBCreatorException.java 2110 2010-03-24 16:19:38Z pnedonosko $
 */
public class DBCreatorException extends Exception
{

   /**
    * DBCreationException constructor.
    */
   public DBCreatorException(Throwable e)
   {
      super(e);
   }

   /**
    * DBCreationException constructor.
    */
   public DBCreatorException(String message, Throwable e)
   {
      super(message, e);
   }

   /**
    * DBCreationException constructor.
    */
   public DBCreatorException(String message)
   {
      super(message);
   }
}
