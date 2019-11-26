package core.commands;

import DB.DataBase;
import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import org.jetbrains.annotations.NotNull;
import vk.VKManager;
import vk.VKServer;

public class Subscribe extends Command {

    public Subscribe() {
        super("Subscribe");
    }

    @Override
    public void exec(@NotNull Message message) {
        boolean isNewSubscribe = VKServer.usersDataBase.addSubscriber(DataBase.connection,message.getUserId(), message.getBody().split(" ")[1]);
        if (isNewSubscribe)
            new VKManager().sendMessage("You subscribed", message.getUserId());
        else
            new VKManager().sendMessage("You just subscribed", message.getUserId());
            new VKManager().sendMessage("If you wanna change the city use \"Unsubscribe\"",message.getUserId());
            new VKManager().sendMessage("And subscribe again",message.getUserId());
    }
}