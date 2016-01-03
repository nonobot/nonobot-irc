require 'nonobot/connection_request'
require 'nonobot/bot_adapter'
require 'vertx/vertx'
require 'vertx/util/utils.rb'
# Generated from io.nonobot.irc.IrcAdapter
module NonobotIrc
  #  @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
  class IrcAdapter
    # @private
    # @param j_del [::NonobotIrc::IrcAdapter] the java delegate
    def initialize(j_del)
      @j_del = j_del
    end
    # @private
    # @return [::NonobotIrc::IrcAdapter] the underlying java delegate
    def j_del
      @j_del
    end
    # @param [::Nonobot::ConnectionRequest] arg0 
    # @return [void]
    def handle(arg0=nil)
      if arg0.class.method_defined?(:j_del) && !block_given?
        return @j_del.java_method(:handle, [Java::IoNonobotCoreAdapter::ConnectionRequest.java_class]).call(arg0.j_del)
      end
      raise ArgumentError, "Invalid arguments when calling handle(arg0)"
    end
    # @param [::Vertx::Vertx] vertx 
    # @param [Hash] options 
    # @return [::Nonobot::BotAdapter]
    def self.create(vertx=nil,options=nil)
      if vertx.class.method_defined?(:j_del) && options.class == Hash && !block_given?
        return ::Vertx::Util::Utils.safe_create(Java::IoNonobotIrc::IrcAdapter.java_method(:create, [Java::IoVertxCore::Vertx.java_class,Java::IoNonobotIrc::IrcOptions.java_class]).call(vertx.j_del,Java::IoNonobotIrc::IrcOptions.new(::Vertx::Util::Utils.to_json_object(options))),::Nonobot::BotAdapter)
      end
      raise ArgumentError, "Invalid arguments when calling create(vertx,options)"
    end
  end
end
