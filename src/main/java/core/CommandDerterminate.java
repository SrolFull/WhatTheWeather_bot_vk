package core;

import com.vk.api.sdk.objects.messages.Message;
import core.commands.SpecifyCity;
import core.commands.Unknown;
import core.commands.UnknownCites;
import org.jetbrains.annotations.NotNull;
import vk.VKServer;

import java.util.Collection;

class CommandDerterminate {
    @NotNull
    static Command getCommand(@NotNull Collection<Command> commands, @NotNull Message message) {
        String body = message.getBody();
        try {
            for (Command command : commands
            ) {
                String[] msgPart = body.split(" ");
                String msgCommand = msgPart[0];
                if (command.getName().equals(msgCommand)) {
                    if (command.getName().equals("Subscribe") && !VKServer.Cites.contains(msgPart[1])) {
                            return new UnknownCites();
                    }
                    return command;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return new SpecifyCity();
        }
        return new Unknown();
    }
}
