package core.commands;

import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import core.*;
import vk.VKManager;

import java.util.Collection;

public class Help extends Command {
    private Collection<Command> commands = CommandManager.getCommands();
    public Help() {
        super("Help");
    }
    @Override
    public void exec(Message message) {
        Integer usr_id = message.getUserId();
        new VKManager().sendMessage("Available commands:", usr_id);
        for (Command command : commands){
            new VKManager().sendMessage(command.getName(),usr_id);
        }
    }
}
