package io.nonobot.irc;

import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.Adapter;
import io.nonobot.irc.impl.IrcAdapterImpl;
import io.vertx.codegen.annotations.VertxGen;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
@VertxGen
public interface IrcAdapter extends Adapter {

  static IrcAdapter create(NonoBot bot, IrcOptions options) {
    return new IrcAdapterImpl(bot, options);
  }


}
