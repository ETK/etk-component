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
package org.etk.apt.processor;

import org.etk.apt.api.ConfigurationBuilder;
import org.etk.apt.core.XMLConfigurationBuilder;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 7/22/11
 */
@SupportedSourceVersion(SourceVersion.RELEASE_5)
@SupportedAnnotationTypes("*")
public class ConfigurationGenerator extends AbstractProcessor
{

   private final static String CONFIG_FILE = "configuration.xml";

   private final static String CONFIG_DIR = "conf";

   @Override
   public void init(ProcessingEnvironment processingEnv)
   {
      super.init(processingEnv);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
   {
      if(annotations.size() > 0)
      {
         Writer writer = null;
         try
         {
            Filer filer = processingEnv.getFiler();
            FileObject configFile = filer.createResource(StandardLocation.SOURCE_OUTPUT, CONFIG_DIR, CONFIG_FILE);
            writer = configFile.openWriter();

            generateConfig(processingEnv, roundEnv, writer);
         }
         catch(Exception ex)
         {
            ex.printStackTrace();
            //Do something
         }
         finally
         {
            try
            {
               if(writer != null)
               {
                  writer.close();
               }
            }
            catch (IOException ioEx)
            {

            }
         }
      }
      return true;
   }

   public void generateConfig(ProcessingEnvironment processEnv, RoundEnvironment roundEnv, Writer writer)
   {
      ConfigurationBuilder builder = new XMLConfigurationBuilder();
      try{
         builder.build(processEnv, roundEnv, writer);
      }
      catch (IOException ioEx)
      {
         ioEx.printStackTrace();
      }

      //TODO: Move the ConfigurationBuilder to a dependency so that the ServiceLoader could work
      /*
      Iterator<ConfigurationBuilder> iterator = ServiceLoader.load(ConfigurationBuilder.class).iterator();
      if(iterator.hasNext())
      {
         try{
            iterator.next().build(processEnv, roundEnv, writer);
         }
         catch (IOException ioEx)
         {

         }
      }
      */
   }
}
