package core;

import com.vk.api.sdk.objects.messages.Message;

public class Commander {

    public static void execute(Message message){
        CommandDerterminate.getCommand(CommandManager.getCommands(), message).exec(message);
    }

}