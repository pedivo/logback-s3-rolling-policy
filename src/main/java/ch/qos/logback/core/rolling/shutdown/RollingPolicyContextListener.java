/*
 * Copyright 2016 linkID Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.qos.logback.core.rolling.shutdown;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RollingPolicyContextListener implements ServletContextListener {

  private static final List<RollingPolicyShutdownListener> listeners;

  static {
    listeners = new ArrayList<RollingPolicyShutdownListener>();
  }

  /**
   * Registers a new shutdown hook.
   *
   * @param listener The shutdown hook to register.
   */
  public static void registerShutdownListener(final RollingPolicyShutdownListener listener) {

    if (!listeners.contains(listener)) {

      listeners.add(listener);
    }
  }

  /**
   * Deregisters a previously registered shutdown hook.
   *
   * @param listener The shutdown hook to deregister.
   */
  public static void deregisterShutdownListener(final RollingPolicyShutdownListener listener) {

    if (listeners.contains(listener)) {

      listeners.remove(listener);
    }
  }

  public void contextInitialized(ServletContextEvent servletContextEvent) {
    //Empty
  }

  /*

   */
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    for (RollingPolicyShutdownListener listener : listeners) {
      listener.doShutdown();
    }
  }
}
