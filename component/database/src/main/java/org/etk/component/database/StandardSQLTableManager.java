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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.etk.component.database.annotation.Table;
import org.etk.component.database.annotation.TableField;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Apr 4, 2006
 */
public class StandardSQLTableManager extends DBTableManager {

  private ExoDatasource exoDatasource;

  public StandardSQLTableManager(ExoDatasource datasource) {
    exoDatasource = datasource;
  }

  public <T extends DBObject> void createTable(Class<T> type, boolean dropIfExist) throws Exception {
    Table table = type.getAnnotation(Table.class);
    if (table == null) {
      throw new Exception("Cannot find the annotation for class " + type.getClass().getName());
    }
    StringBuilder builder = new StringBuilder(1000);
    builder.append("CREATE TABLE ").append(table.name()).append(" (");
    appendId(builder);
    TableField[] fields = table.field();
    for (int i = 0; i < fields.length; i++) {
      TableField field = fields[i];
      String fieldType = field.type();
      if ("string".equals(fieldType)) {
        appendStringField(field, builder);
      } else if ("int".equals(fieldType)) {
        appendIntegerField(field, builder);
      } else if ("long".equals(fieldType)) {
        appendLongField(field, builder);
      } else if ("float".equals(fieldType)) {
        appendFloatField(field, builder);
      } else if ("double".equals(fieldType)) {
        appendDoubleField(field, builder);
      } else if ("boolean".equals(fieldType)) {
        appendBooleanField(field, builder);
      } else if ("date".equals(fieldType)) {
        appendDateField(field, builder);
      } else if ("binary".equals(fieldType)) {
        appendBinaryField(field, builder);
      }
      if (i != fields.length - 1)
        builder.append(", ");
    }
    builder.append(")");

    // print out the sql string
    Connection conn = exoDatasource.getConnection();
    conn.setAutoCommit(false);
    Statement statement = conn.createStatement();
    System.out.println("QUERY: \n  " + builder + "\n");
    if (dropIfExist && hasTable(type))
      statement.execute("DROP TABLE IF EXISTS " + table.name());
    statement.execute(builder.toString());
    statement.close();
    conn.commit();
    exoDatasource.closeConnection(conn);
  }

  public <T extends DBObject> void dropTable(Class<T> type) throws Exception {
    Table table = type.getAnnotation(Table.class);
    if (table == null) {
      throw new Exception("Can not find the annotation for class " + type.getClass().getName());
    }
    Connection conn = exoDatasource.getConnection();
    Statement s = conn.createStatement();
    s.execute("DROP TABLE " + table.name());
    s.close();
    conn.commit();
    exoDatasource.closeConnection(conn);
  }

  public <T extends DBObject> boolean hasTable(Class<T> type) throws Exception {
    Table table = type.getAnnotation(Table.class);
    if (table == null) {
      throw new Exception("Can not find the annotation for class " + type.getClass().getName());
    }
    Connection connection = exoDatasource.getConnection();
    Statement statement = connection.createStatement();
    try {
      if (statement.execute("SELECT 1 FROM " + table.name()) == true)
        return true;
    } catch (SQLException ex) {
      return false;
    } finally {
      statement.close();
      exoDatasource.closeConnection(connection);
    }
    return false;
  }

  protected void appendId(StringBuilder builder) {
    builder.append("ID BIGINT NOT NULL PRIMARY KEY, ");
  }

  protected void appendStringField(TableField field, StringBuilder builder) throws Exception {
    if (field.length() < 1) {
      throw new Exception("You forget to specify  the length for field " + field.name()
          + " , type " + field.type());
    }
    builder.append(field.name()).append(" ").append("VARCHAR(" + field.length() + ")");
    if (!field.nullable())
      builder.append(" NOT NULL ");
  }

  protected void appendIntegerField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" INTEGER");
  }

  protected void appendLongField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" BIGINT");
  }

  protected void appendFloatField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" REAL");
  }

  protected void appendDoubleField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" DOUBLE");
  }

  protected void appendBooleanField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" BIT");
  }

  protected void appendDateField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" DATE");
  }

  protected void appendDateTimeField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" DATETIME");
  }

  protected void appendBinaryField(TableField field, StringBuilder builder) {
    builder.append(field.name()).append(" VARBINARY");
  }

}
