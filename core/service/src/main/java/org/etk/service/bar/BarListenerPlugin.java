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
package org.etk.service.bar;

import org.etk.kernel.management.ManagedPlugin;
import org.etk.service.bar.api.BarLifeCycleListener;
import org.etk.service.bar.api.BarLifeCycleEvent;


/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Jul 21, 2011  
 */
public abstract class BarListenerPlugin extends ManagedPlugin implements BarLifeCycleListener {

  /**
   * {@inheritDoc}
   */
  public abstract void barCreated(BarLifeCycleEvent event);

  /**
   * {@inheritDoc}
   */
  public abstract void barRemoved(BarLifeCycleEvent event);
  
  /**
   * {@inheritDoc}
   */
  public abstract void barUpdated(BarLifeCycleEvent event);
}
