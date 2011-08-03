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

import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.etk.core.rest.method.MethodInvoker;
import org.etk.core.rest.method.MethodParameter;
import org.etk.core.rest.resource.AbstractResourceDescriptor;
import org.etk.core.rest.resource.ResourceDescriptorVisitor;
import org.etk.core.rest.resource.ResourceMethodDescriptor;

public class ResourceMethodDescriptorImpl implements ResourceMethodDescriptor {

  /**
   * This method will be invoked.
   */
  private final Method                     method;

  /**
   * HTTP request method designator.
   */
  private final String                     httpMethod;

  /**
   * List of method's parameters. See {@link MethodParameter} .
   */
  private final List<MethodParameter>      parameters;

  /**
   * Parent resource for this method resource, in other words class which
   * contains this method.
   */
  private final AbstractResourceDescriptor parentResource;

  /**
   * List of media types which this method can consume. See
   * {@link javax.ws.rs.Consumes} .
   */
  private final List<MediaType>            consumes;

  /**
   * List of media types which this method can produce. See
   * {@link javax.ws.rs.Produces} .
   */
  private final List<MediaType>            produces;

  /**
   * Method invoker.
   */
  private final MethodInvoker              invoker;

  /**
   * Constructs new instance of {@link ResourceMethodDescriptor}.
   * 
   * @param method See {@link Method}
   * @param httpMethod HTTP request method designator
   * @param parameters list of method parameters. See {@link MethodParameter}
   * @param parentResource parent resource for this method
   * @param consumes list of media types which this method can consume
   * @param produces list of media types which this method can produce
   * @param invoker method invoker
   */
  ResourceMethodDescriptorImpl(Method method,
                               String httpMethod,
                               List<MethodParameter> parameters,
                               AbstractResourceDescriptor parentResource,
                               List<MediaType> consumes,
                               List<MediaType> produces,
                               MethodInvoker invoker) {
    this.method = method;
    this.httpMethod = httpMethod;
    this.parameters = parameters;
    this.parentResource = parentResource;
    this.consumes = consumes;
    this.produces = produces;
    this.invoker = invoker;
  }

  /**
   * {@inheritDoc}
   */
  public Method getMethod() {
    return method;
  }

  /**
   * {@inheritDoc}
   */
  public List<MethodParameter> getMethodParameters() {
    return parameters;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractResourceDescriptor getParentResource() {
    return parentResource;
  }

  /**
   * {@inheritDoc}
   */
  public void accept(ResourceDescriptorVisitor visitor) {
    visitor.visitResourceMethodDescriptor(this);
  }

  /**
   * {@inheritDoc}
   */
  public List<MediaType> consumes() {
    return consumes;
  }

  /**
   * {@inheritDoc}
   */
  public String getHttpMethod() {
    return httpMethod;
  }

  /**
   * {@inheritDoc}
   */
  public List<MediaType> produces() {
    return produces;
  }

  /**
   * {@inheritDoc}
   */
  public MethodInvoker getMethodInvoker() {
    return invoker;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getResponseType() {
    return getMethod().getReturnType();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("[ ResourceMethodDescriptorImpl: ");
    sb.append("resource: " + getParentResource() + "; ")
      .append("HTTP method: " + getHttpMethod() + "; ")
      .append("produces media type: " + produces() + "; ")
      .append("consumes media type: " + consumes() + "; ")
      .append("return type: " + getResponseType() + "; ")
      .append("invoker: " + getMethodInvoker() + "; ")
      .append("parameters: [ ");
    for (MethodParameter p : getMethodParameters())
      sb.append(p.toString() + " ");
    sb.append("] ]");
    return sb.toString();
  }

}
