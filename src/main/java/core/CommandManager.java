package core;

import core.commands.*;

import java.util.HashSet;

public class CommandManager {
    private static HashSet<Command> commands = new HashSet<>();

    static {
        commands.add(new Weather());
        commands.add(new Subscribe());
        commands.add(new Unsubscribe());
        commands.add(new Help());
    }

    public static HashSet<Command> getCommands(){
        return commands;
    }
}
