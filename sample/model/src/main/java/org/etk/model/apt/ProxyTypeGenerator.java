/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.etk.model.apt;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.etk.model.plugins.instrument.MethodHandler;
import org.etk.orm.apt.FormatterStyle;
import org.etk.orm.apt.TypeFormatter;
import org.etk.reflect.api.ClassTypeInfo;
import org.etk.reflect.api.MethodInfo;
import org.etk.reflect.api.TypeInfo;
import org.etk.reflect.api.VoidTypeInfo;
import org.etk.reflect.api.definition.ClassKind;
import org.etk.reflect.api.introspection.MethodIntrospector;
import org.etk.reflect.api.visit.HierarchyScope;

/**
 * Generates the proxy type implementation that is loaded by Chromattic at runtime.
 *
 */
class ProxyTypeGenerator {

  /** . */
  private final ClassTypeInfo type;

  ProxyTypeGenerator(ClassTypeInfo type) {
    this.type = type;
  }

  void build(StringBuilder code) {
    //
    String simpleClassName = type.getSimpleName() + "_ENTITY";
    code.append("package ").append(type.getPackageName()).append(";\n");
    code.append("import ").append(Invoker.class.getName()).append(";\n");
    code.append("import ").append(Instrumented.class.getName()).append(";\n");

    //
    code.append("public class ");
    code.append(simpleClassName);
    code.append(" extends ");
    if (type.getKind() == ClassKind.INTERFACE) {
      code.append(Object.class.getName());
      code.append(" implements ");
      code.append(type.getSimpleName());
      code.append(",");
      code.append(Instrumented.class.getSimpleName());
    }
    else {
      code.append(type.getSimpleName());
      code.append(" implements ");
      code.append(Instrumented.class.getSimpleName());
    }
    code.append(" {\n");

    //Generates the constructor
    appendContructor(code);

    //Generates method
    appendMethods(code);

    //
    code.append("}\n");
  }

  private void appendContructor(StringBuilder code) {
    code.append("public final ").append(MethodHandler.class.getName()).append(" handler;\n");
    code.append("public ").append(type.getSimpleName()).append("_ENTITY(").append(MethodHandler.class.getName()).append(" handler) {\n");
    code.append("this.handler = handler;\n");
    code.append("}\n");
  }

  private void appendMethods(StringBuilder code) {
    //
    int id = 0;

    //
    Iterable<MethodInfo> methods = getMethodsToImplement();

    for (MethodInfo method : methods) {

      String methodId = "method_" + id++;
      String methodName = method.getName();
      List<TypeInfo> parameterTypes = method.getParameterTypes();
      TypeInfo rti = method.getReturnType();

      //
      String scope;
      switch (method.getAccess()) {
        case PACKAGE_PROTECTED:
          scope = "";
          break;
        case PROTECTED:
          scope = "protected";
          break;
        case PUBLIC:
          scope = "public";
          break;
        default:
          throw new AssertionError();
      }

      //
      code.append("private static final ").
          append(Invoker.class.getSimpleName()).
          append(" ").append(methodId).append(" = ").
          append(Invoker.class.getSimpleName()).
          append(".getDeclaredMethod(").
          append(method.getOwner().getName()).
          append(".class,").
          append('"').
          append(methodName).
          append('"');
      for (TypeInfo parameterType : parameterTypes) {
        code.append(",");
        new TypeFormatter(type, FormatterStyle.LITERAL, code).format(parameterType);
        code.append(".class");
      }
      code.append(");\n");

      //
      code.append(scope).append(" final ");
      new TypeFormatter(type, FormatterStyle.RETURN_TYPE, code).format(rti);
      code.append(" ").append(methodName).append("(");

      //
      for (int i = 0; i < parameterTypes.size(); i++) {
        TypeInfo parameterType = parameterTypes.get(i);
        if (i > 0) {
          code.append(",");
        }
        new TypeFormatter(type, FormatterStyle.TYPE_PARAMETER, code).format(parameterType);
        code.append(" arg_").append(i);
      }
      code.append(")");

      // Build throws clause
      LinkedHashSet<String> catched = new LinkedHashSet<String>();
      boolean hasThrown = false;
      for (ClassTypeInfo thrownCTI : method.getThrownTypes()) {
        if (hasThrown) {
          code.append(", ");
        } else {
          code.append(" throws ");
          hasThrown = true;
        }
        code.append(thrownCTI.getName());
        catched.add(thrownCTI.getName());
      }

      // Complete catched throwables
      catched.add(RuntimeException.class.getName());
      catched.add(Error.class.getName());


      //
      code.append(" {\n");

      //
      code.append("try {\n");

      //
      switch (parameterTypes.size()) {
        case 0:
          if (rti instanceof VoidTypeInfo) {
            code.append("handler.invoke(this,").append(methodId).append(".getMethod());\n");
          } else {
            code.append("return (");
            new TypeFormatter(type, FormatterStyle.CAST, code).format(rti);
            code.append(")");
            code.append("handler.invoke(this,").append(methodId).append(".getMethod());\n");
          }
          break;
        case 1:
          if (rti instanceof VoidTypeInfo) {
            code.append("handler.invoke(this,").append(methodId).append(".getMethod(),(Object)arg_0);\n");
          } else {
            code.append("return (");
            new TypeFormatter(type, FormatterStyle.CAST, code).format(rti);
            code.append(")");
            code.append("handler.invoke(this,").append(methodId).append(".getMethod(),(Object)arg_0);\n");
          }
          break;
        default:
          code.append("Object[] args = new Object[]{");
          for (int i = 0; i < parameterTypes.size(); i++) {
            if (i > 0) {
              code.append(",");
            }
            code.append("arg_").append(i);
          }
          code.append("};\n");
          if (rti instanceof VoidTypeInfo) {
            code.append("handler.invoke(this,").append(methodId).append(".getMethod(),args);\n");
          } else {
            code.append("return (");
            new TypeFormatter(type, FormatterStyle.CAST, code).format(rti);
            code.append(")");
            code.append("handler.invoke(this,").append(methodId).append(".getMethod(),args);\n");
          }
          break;
      }

      //
      code.append("} catch(Throwable t) {\n");
      for (String c : catched) {
        code.append("if (t instanceof ").append(c).append(") throw (").append(c).append(")t;\n");
      }
      code.append("throw new java.lang.reflect.UndeclaredThrowableException(t);\n");
      code.append("}\n");

      //
      code.append("}\n");
    }
  }

  private Iterable<MethodInfo> getMethodsToImplement() {
    List<MethodInfo> methods = new ArrayList<MethodInfo>();
    MethodIntrospector introspector = new MethodIntrospector(HierarchyScope.ALL, true);
    for (MethodInfo method : introspector.getMethods(type)) {
      if (method.isAbstract()) {
        methods.add(method);
      }
    }
    return methods;
  }
}

