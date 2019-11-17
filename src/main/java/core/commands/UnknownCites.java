package core.commands;

import com.vk.api.sdk.objects.messages.Message;
import core.Command;
import org.jetbrains.annotations.NotNull;
import vk.VKManager;

public class UnknownCites extends Command {
    public UnknownCites() {
        super("UnknownCites");
    }

    @Override
    public void exec(@NotNull Message message) {
        new VKManager().sendMessage("UnknownCites", message.getUserId());
    }
}
