package com.hazuki.basicbot.command;

import com.hazuki.basicbot.command.commands.basic.HelpCommand;
import com.hazuki.basicbot.command.commands.basic.StopBotCommand;
import com.hazuki.basicbot.command.commands.music.JoinCommand;
import com.hazuki.basicbot.command.commands.music.LeaveCommand;
import com.hazuki.basicbot.command.commands.music.PlayCommand;
import com.hazuki.basicbot.command.commands.music.StopCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public class CommandManager {

    private ArrayList<ICommand> commands = new ArrayList<>();

    private final String prefix = "!";

    public void loadCommand() {
        // Add commands here
        this.commands.add(new HelpCommand());
        this.commands.add(new StopBotCommand());
        // music commands
        this.commands.add(new JoinCommand());
        this.commands.add(new LeaveCommand());
        this.commands.add(new PlayCommand());
        this.commands.add(new StopCommand());
    }

    public void executeCommand(GuildMessageReceivedEvent e, String msg) {
        this.commands.forEach(command -> {
            var splitMessage = msg.split(" ", 2); // Limit 2 == split once

            if (splitMessage.length == 0) {
                return;
            }
            if (splitMessage[0].equalsIgnoreCase(this.prefix + command.getInvoke())) {
                command.onMessageReceived(e, splitMessage.length == 1 ? null : splitMessage[1].split(" "));
            }
        });
    }

    public ArrayList<ICommand> getCommands() {
        return commands;
    }

    public String getPrefix() {
        return prefix;
    }

}