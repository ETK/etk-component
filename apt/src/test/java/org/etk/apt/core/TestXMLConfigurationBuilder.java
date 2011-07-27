/*
 * Copyright (C) 2011 eXo Platform SAS.
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
package org.etk.apt.core;

import junit.framework.TestCase;
import org.etk.apt.api.ConfigurationBuilder;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 7/25/11
 */
public class TestXMLConfigurationBuilder extends TestCase
{

   private ConfigurationBuilder builder;

   private volatile boolean neverInit = true;

   @Override
   protected void setUp() throws Exception
   {
      super.setUp();

      if(neverInit)
      {
         neverInit = false;
         ServiceLoader<ConfigurationBuilder> loader = ServiceLoader.load(ConfigurationBuilder.class);
         Iterator<ConfigurationBuilder> iterator = loader.iterator();

         while(iterator.hasNext())
         {
            ConfigurationBuilder tmp = iterator.next();
            if(tmp instanceof XMLConfigurationBuilder)
            {
               builder = tmp;
               return;
            }
         }

         builder = null;
      }
   }

   public void testType()
   {
      assertNotNull(builder);
   }

   public void testGenerateConfiguration()
   {


   }

   @Override
   protected void tearDown() throws Exception
   {
      super.tearDown();
   }
}
