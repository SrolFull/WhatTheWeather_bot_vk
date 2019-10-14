package core;

import core.commands.Subscribe;
import core.commands.Unknown;
import core.commands.Unsubscribe;
import core.commands.Weather;

import java.util.HashSet;

class CommandManager {
    private static HashSet<Command> commands = new HashSet<>();

    static {
        commands.add(new Unknown());
        commands.add(new Weather());
        commands.add(new Subscribe());
        commands.add(new Unsubscribe());
    }

    static HashSet<Command> getCommands(){
        return commands;
    }
}
