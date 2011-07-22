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
package org.etk.storage.plugins.cache.model.data;

import org.etk.service.foo.model.Bar;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Jul 22, 2011  
 */
public class BarData implements CacheData<Bar> {

  private final String id;
  private final String description;
  
  @Override
  public Bar build() {
    Bar bar = new Bar();
    bar.setId(this.id);
    bar.setDescription(this.description);
    return bar;
  }
  
  public BarData(final Bar model) {
    this.id = model.getId();
    this.description = model.getDescription();
  }

  public String getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }
}
