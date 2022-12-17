package JheyBot.Commands.CommandHandlers.prefixHandlers;

import JheyBot.Bot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JPrefixCommand extends ListenerAdapter {

   private static final List<JPrefixCommandInterface> commands = new ArrayList<>();

   public static void add(JPrefixCommandInterface command){
      commands.add(command);
   }


   @Override
   public void onMessageReceived(@NotNull MessageReceivedEvent event) {
      String prefix = Bot.prefix;
     for(JPrefixCommandInterface command : commands){
       if(event.getMessage().getContentRaw().startsWith(prefix + command.getName())){
          command.callBack(event);
          break;
       }
      }

   }
}