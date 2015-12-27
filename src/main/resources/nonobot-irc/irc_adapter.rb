require 'nonobot/bot_adapter'
require 'nonobot/nono_bot'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.irc.IrcAdapter
module NonobotIrc
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class IrcAdapter < ::Nonobot::BotAdapter
    # @private
    # @param j_del [::NonobotIrc::IrcAdapter] the java delegate
    def initialize(j_del)
      super(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::NonobotIrc::IrcAdapter] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Nonobot::NonoBot] bot 
    # @param [Hash] options 
    # @return [::NonobotIrc::IrcAdapter]
    def self.create(bot=nil,options=nil)
      if bot.class.method_defined?(:j_del) && options.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotIrc::IrcAdapter.java_method(:create, [Java::IoNonobotCore::NonoBot.java_class,Java::IoNonobotIrc::IrcOptions.java_class]).call(bot.j_del,Java::IoNonobotIrc::IrcOptions.new(::Vertx::Util::Utils.to_json_object(options))),::NonobotIrc::IrcAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create(bot,options)"
    end
  end
end
