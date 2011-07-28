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
package org.etk.jndi.core;

import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date 7/28/11
 */
public class ETKInitialContext extends InitialContext
{

   protected ETKInitialContext(boolean lazy) throws NamingException
   {
      super(lazy);    //To change body of overridden methods use File | Settings | File Templates.
   }

   public ETKInitialContext() throws NamingException
   {
      super();    //To change body of overridden methods use File | Settings | File Templates.
   }

   public ETKInitialContext(Hashtable<?, ?> environment) throws NamingException
   {
      super(environment);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   protected void init(Hashtable<?, ?> environment) throws NamingException
   {
      super.init(environment);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   protected Context getDefaultInitCtx() throws NamingException
   {
      return super.getDefaultInitCtx();    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   protected Context getURLOrDefaultInitCtx(String name) throws NamingException
   {
      return super.getURLOrDefaultInitCtx(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   protected Context getURLOrDefaultInitCtx(Name name) throws NamingException
   {
      return super.getURLOrDefaultInitCtx(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Object lookup(String name) throws NamingException
   {
      return super.lookup(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Object lookup(Name name) throws NamingException
   {
      return super.lookup(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void bind(String name, Object obj) throws NamingException
   {
      super.bind(name, obj);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void bind(Name name, Object obj) throws NamingException
   {
      super.bind(name, obj);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void rebind(String name, Object obj) throws NamingException
   {
      super.rebind(name, obj);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void rebind(Name name, Object obj) throws NamingException
   {
      super.rebind(name, obj);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void unbind(String name) throws NamingException
   {
      super.unbind(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void unbind(Name name) throws NamingException
   {
      super.unbind(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void rename(String oldName, String newName) throws NamingException
   {
      super.rename(oldName, newName);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void rename(Name oldName, Name newName) throws NamingException
   {
      super.rename(oldName, newName);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public NamingEnumeration<NameClassPair> list(String name) throws NamingException
   {
      return super.list(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public NamingEnumeration<NameClassPair> list(Name name) throws NamingException
   {
      return super.list(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public NamingEnumeration<Binding> listBindings(String name) throws NamingException
   {
      return super.listBindings(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public NamingEnumeration<Binding> listBindings(Name name) throws NamingException
   {
      return super.listBindings(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void destroySubcontext(String name) throws NamingException
   {
      super.destroySubcontext(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void destroySubcontext(Name name) throws NamingException
   {
      super.destroySubcontext(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Context createSubcontext(String name) throws NamingException
   {
      return super.createSubcontext(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Context createSubcontext(Name name) throws NamingException
   {
      return super.createSubcontext(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Object lookupLink(String name) throws NamingException
   {
      return super.lookupLink(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Object lookupLink(Name name) throws NamingException
   {
      return super.lookupLink(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public NameParser getNameParser(String name) throws NamingException
   {
      return super.getNameParser(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public NameParser getNameParser(Name name) throws NamingException
   {
      return super.getNameParser(name);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public String composeName(String name, String prefix) throws NamingException
   {
      return super.composeName(name, prefix);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Name composeName(Name name, Name prefix) throws NamingException
   {
      return super.composeName(name, prefix);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Object addToEnvironment(String propName, Object propVal) throws NamingException
   {
      return super.addToEnvironment(propName, propVal);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Object removeFromEnvironment(String propName) throws NamingException
   {
      return super.removeFromEnvironment(propName);    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public Hashtable<?, ?> getEnvironment() throws NamingException
   {
      return super.getEnvironment();    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public void close() throws NamingException
   {
      super.close();    //To change body of overridden methods use File | Settings | File Templates.
   }

   @Override
   public String getNameInNamespace() throws NamingException
   {
      return super.getNameInNamespace();    //To change body of overridden methods use File | Settings | File Templates.
   }
}
