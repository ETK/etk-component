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
package org.etk.sample.apt;

import junit.framework.TestCase;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 7/28/11
 */
public class TestGeneratedConfiguration extends TestCase
{

   private final static String EXPECTED_FILE = "expected-configuration.xml";

   private final static String GENERATED_FILE = "conf/configuration.xml";

   public void testGeneratedContent() throws IOException
   {
      InputStream generatedInput = Thread.currentThread().getContextClassLoader().getResourceAsStream(GENERATED_FILE);
      assertNotNull(generatedInput);

      InputStream expectedInput = Thread.currentThread().getContextClassLoader().getResourceAsStream(EXPECTED_FILE);
      assertNotNull(expectedInput);

      int c1 = expectedInput.read();
      int c2 = generatedInput.read();
      boolean equal = true;

      //TODO: Use MessageDigest to compare two files
      while(c1 != -1 || c2 != -1)
      {
         if(c1 != c2)
         {
            equal = false;
            break;
         }
         else if(c1 == -1 && c2 == -1)
         {
            break;
         }
         else
         {
            c1 = expectedInput.read();
            c2 = generatedInput.read();
         }
      }
      assertTrue(equal);
   }
}
