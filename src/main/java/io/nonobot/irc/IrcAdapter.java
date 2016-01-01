package io.nonobot.irc;

import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.adapter.ConnectionRequest;
import io.nonobot.irc.impl.IrcAdapterImpl;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface IrcAdapter extends Handler<ConnectionRequest> {

  static BotAdapter create(Vertx vertx, IrcOptions options) {
    return BotAdapter.create(vertx).requestHandler(new IrcAdapterImpl(options));
  }
}
