package core.commands;

import DB.DataBase;
import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import org.jetbrains.annotations.NotNull;
import vk.VKManager;
import vk.VKServer;

public class Unsubscribe extends Command {

    public Unsubscribe() {
        super("Unsubscribe");
    }

    @Override
    public void exec(@NotNull Message message) {
        VKServer.usersDataBase.deleteSubscriber(DataBase.connection,message.getUserId());
        new VKManager().sendMessage("You unsubscribed", message.getUserId());
    }
}