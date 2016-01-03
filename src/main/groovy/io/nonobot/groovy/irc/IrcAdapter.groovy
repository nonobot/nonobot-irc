/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.nonobot.groovy.irc;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.nonobot.irc.IrcOptions
import io.nonobot.groovy.core.adapter.ConnectionRequest
import io.nonobot.groovy.core.adapter.BotAdapter
import io.vertx.groovy.core.Vertx
import io.vertx.core.Handler
/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
*/
@CompileStatic
public class IrcAdapter implements Handler<ConnectionRequest> {
  private final def io.nonobot.irc.IrcAdapter delegate;
  public IrcAdapter(Object delegate) {
    this.delegate = (io.nonobot.irc.IrcAdapter) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public void handle(ConnectionRequest arg0) {
    ((io.vertx.core.Handler) this.delegate).handle((io.nonobot.core.adapter.ConnectionRequest)arg0.getDelegate());
  }
  public static BotAdapter create(Vertx vertx, Map<String, Object> options) {
    def ret= InternalHelper.safeCreate(io.nonobot.irc.IrcAdapter.create((io.vertx.core.Vertx)vertx.getDelegate(), options != null ? new io.nonobot.irc.IrcOptions(new io.vertx.core.json.JsonObject(options)) : null), io.nonobot.groovy.core.adapter.BotAdapter.class);
    return ret;
  }
}
