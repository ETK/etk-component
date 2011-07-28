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

import org.etk.apt.annotation.KernelComponent;
import org.etk.apt.api.ConfigurationBuilder;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 7/25/11
 */
public class XMLConfigurationBuilder implements ConfigurationBuilder
{
   public void build(ProcessingEnvironment processEnv, RoundEnvironment roundEnv, Writer writer) throws IOException
   {
      BufferedWriter buf = new BufferedWriter(writer);
      buildHeader(buf);
      buildBody(processEnv, roundEnv, writer);
      buildFooter(buf);
      buf.flush();
   }

   private void buildHeader(Writer writer) throws IOException
   {
      writer.write("<configuration>\n");
      writer.flush();
   }

   private void buildBody(ProcessingEnvironment processEnv, RoundEnvironment roundEnv, Writer writer) throws IOException
   {
      Set<? extends Element> kernelElements = roundEnv.getElementsAnnotatedWith(KernelComponent.class);
      for(Element kernelEle : kernelElements)
      {
         try
         {
            buildElement(kernelEle, writer);
         }
         catch (IOException ioEx)
         {
             ioEx.printStackTrace();
         }
      }
   }

   private void buildElement(Element kernelElement, Writer writer) throws IOException
   {
      StringBuilder builder = new StringBuilder("<component>\n");

      KernelComponent kerAnnotation = kernelElement.getAnnotation(KernelComponent.class);
      String key, type, jmxName;
      key = kerAnnotation.key();
      type = kernelElement.asType().toString();
      jmxName = kerAnnotation.jmxName();
      if(key == null || key.length() < 1)
      {
         key = type;
      }

      builder.append("<key>").append(key).append("</key>\n");
      builder.append("<type>").append(type).append("</type>\n");
      if(jmxName != null && jmxName.length() > 0)
      {
         builder.append("<jmx-name>").append(jmxName).append("</jmx-name>\n");
      }

      builder.append("</component>");
      builder.append("\n").append("\n");

      writer.write(builder.toString());
      writer.flush();
   }

   private void buildFooter(Writer writer) throws IOException
   {
      writer.append("</configuration>");
      writer.flush();
   }
}
