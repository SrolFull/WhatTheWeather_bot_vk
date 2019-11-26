package core.commands;

import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import org.jetbrains.annotations.NotNull;
import vk.VKManager;
public class Unknown extends Command {

    public Unknown() {
        super("Unknown");
    }

    @Override
    public void exec(@NotNull Message message) {
        new VKManager().sendMessage("Unknown message", message.getUserId());
    }
}
