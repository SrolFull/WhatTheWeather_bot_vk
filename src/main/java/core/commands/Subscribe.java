package core.commands;

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
        VKServer.UsersDb.addSubcriber(message.getUserId(), message.getBody().split(" ")[1]);
        new VKManager().sendMessage("You subscribed", message.getUserId());
    }
}