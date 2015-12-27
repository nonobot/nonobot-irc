package io.nonobot.irc;

import io.nonobot.core.Config;
import io.nonobot.core.NonoBot;
import io.nonobot.core.adapter.BotAdapter;
import io.nonobot.core.spi.BotAdapterFactory;

import java.util.Arrays;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class IrcAdapterFactory implements BotAdapterFactory {

  @Override
  public BotAdapter create(NonoBot bot, Config config) {
    String ircChannels = config.getProperty("irc.channels");
    if (ircChannels != null) {
      IrcOptions options = new IrcOptions();
      Arrays.asList(ircChannels.split("\\s*,\\s*")).forEach(options::addChannel);;
      String ircHost = config.getProperty("irc.host");
      if (ircHost != null) {
        options.setHost(ircHost);
      }
      String ircName = config.getProperty("irc.name");
      if (ircName != null) {
        options.setName(ircName);
      }
      String nickservPassword = config.getProperty("irc.nickserv-password");
      if (nickservPassword != null) {
        options.setNickServPassword(nickservPassword);
      }
      String ircPort = config.getProperty("irc.port");
      if (ircPort != null) {
        options.setPort(Integer.parseInt(ircPort));
      }
      return IrcAdapter.create(bot, options);
    }
    return null;
  }
}
