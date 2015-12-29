require 'nonobot/bot_adapter'
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
    # @param [Hash] options 
    # @return [::NonobotIrc::IrcAdapter]
    def self.create(options=nil)
      if options.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotIrc::IrcAdapter.java_method(:create, [Java::IoNonobotIrc::IrcOptions.java_class]).call(Java::IoNonobotIrc::IrcOptions.new(::Vertx::Util::Utils.to_json_object(options))),::NonobotIrc::IrcAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create(options)"
    end
  end
end
