package com.hazuki.basicbot;

import com.hazuki.basicbot.command.CommandManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

    private static final Bot INSTANCE = new Bot();

    private JDA jda;

    private final CommandManager commandManager = new CommandManager();

    public static Bot getInstance() {
        return INSTANCE;
    }

    public void buildBot() throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(BotInfo.BOT_TOKEN);
        builder.setStatus(BotInfo.BOT_STATUS);
        builder.setAutoReconnect(BotInfo.BOT_AUTO_RECONNECT);
        this.jda = builder.build();
    }

    public void init() {
        this.jda.addEventListener(this);
        this.commandManager.loadCommand();
    }

    public void stopBot() {
        System.exit(0);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent e) {
        super.onGuildMessageReceived(e);
        this.commandManager.executeCommand(e, e.getMessage().getContentRaw());
    }

    public JDA getJDA() {
        return jda;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

}