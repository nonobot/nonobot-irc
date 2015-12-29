package io.nonobot.irc.impl;

import io.nonobot.core.client.ReceiveOptions;
import io.nonobot.irc.IrcOptions;
import io.nonobot.irc.IrcAdapter;
import io.nonobot.core.client.BotClient;
import io.vertx.core.Context;
import io.vertx.core.Future;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericUserEvent;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class IrcAdapterImpl implements IrcAdapter {

  private final IrcOptions options;
  private Runner runner;

  public IrcAdapterImpl(IrcOptions options) {
    this.options = new IrcOptions(options);
  }

  @Override
  public void connect(BotClient client, Future<Void> completionHandler) {
    if (runner != null) {
      throw new IllegalStateException();
    }
    Context context = client.bot().vertx().getOrCreateContext();
    String name = options.getName();
    if (name == null) {
      name = client.bot().name();
    }
    Configuration.Builder<PircBotX> config = new Configuration.Builder<>().
        setName(name).
        setNickservPassword(options.getNickServPassword()).
        setSocketTimeout(options.getSocketTimeout()).
        setServerHostname(options.getHost()).
        setServerPort(options.getPort()).
        setAutoNickChange(true).
        setAutoReconnect(false).
        setSocketFactory(SOCKET_FACTORY);
    for (String channel : options.getChannels()) {
      config = config.addAutoJoinChannel(channel);
    }
    runner = new Runner(client, config, context, completionHandler);
    runner.thread.start();
  }

  @Override
  public synchronized void close() {
    if (runner != null) {
      runner.thread.interrupt();
    }
  }

  class Runner extends ListenerAdapter<PircBotX> implements Runnable {

    final BotClient client;
    final Context context;
    final Future<Void> completion;
    final PircBotX bot;
    final Thread thread;

    public Runner(BotClient client, Configuration.Builder<PircBotX> config, Context context, Future<Void> completion) {

      config.addListener(this);

      this.client = client;
      this.context = context;
      this.completion = completion;
      this.bot = new PircBotX(config.buildConfiguration());
      this.thread = new Thread(this);
    }

    @Override
    public void onConnect(ConnectEvent<PircBotX> event) throws Exception {
      client.rename(event.getBot().getNick());
      if (!completion.isComplete()) {
        completion.complete();
      }
    }

    @Override
    public void onDisconnect(DisconnectEvent<PircBotX> event) throws Exception {
      handleClose();
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
      processMessage(event, event.getMessage());
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
      processMessage(event, event.getMessage());
    }

    private void processMessage(GenericUserEvent<PircBotX> event, String msg) {
      System.out.println("Processing IRC message by " + event.getUser().getNick() + ": " + msg);
      client.receiveMessage(new ReceiveOptions(), msg, ar -> {
        if (ar.succeeded()) {
          String reply = ar.result();
          context.executeBlocking(fut -> {
            String[] lines = reply.split("\\r?\\n");
            for (String line : lines) {
              event.respond(line);
            }
          }, res -> {});
        }
      });
    }

    private void handleClose() {
      synchronized (IrcAdapterImpl.this) {
        runner = null;
      }
      client.close();
    }

    @Override
    public void run() {
      try {
        bot.startBot();
      } catch (Exception e) {
        if (!completion.isComplete()) {
          synchronized (IrcAdapterImpl.this) {
            runner = null;
          }
          completion.fail(e);
        } else {
          handleClose();
        }
      }
    }
  }

  private static final SocketFactory SOCKET_FACTORY = new SocketFactory() {

    final SocketFactory def = SocketFactory.getDefault();

    @Override
    public Socket createSocket(String host, int port) throws IOException {
      return def.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
      if (localHost == null && localPort == 0) {
        return def.createSocket(host, port);
      } else {
        return def.createSocket(host, port, localHost, localPort);
      }
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
      return def.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
      if (localAddress == null && localPort == 0) {
        return def.createSocket(address, port);
      } else {
        return def.createSocket(address, port, localAddress, localPort);
      }
    }
  };


}
