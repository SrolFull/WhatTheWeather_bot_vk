package core;

import com.vk.api.sdk.objects.messages.Message;
import core.commands.Unknown;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

class CommandDerterminate {
    @NotNull
    static Command getCommand(@NotNull Collection<Command> commands, @NotNull Message message){
        String body = message.getBody();
        for (Command command : commands
        ) {
            if (command.getName().equals(body.split(" ")[0])) {
                if (command.getName().equals("Subscibe"))
                return command;
            }
        }
        return new Unknown();
    }
}
