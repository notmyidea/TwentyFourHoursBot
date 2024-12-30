package me.notmyidea.tfhb;


import me.notmyidea.tfhb.listeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.EnumSet;
import java.util.Timer;
import java.util.TimerTask;

public class Bot {

    public static void main(String[] args) {
        JDA jda = JDABuilder.createLight(System.getenv("BOT_TOKEN"), EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT))
                .addEventListeners(new SlashCommandListener())
                .build();

        // Register Commands

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                Commands.slash("register", "Register this channel to be cleaned every 24 hours")
                        .addOption(OptionType.BOOLEAN, "embed", "Whether to send a pinned embed message declaring this channel to be cleaned every 24 hours ")
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED),
                Commands.slash("unregister", "Unregister this channel from being cleaned every 24 hours")
                        .setGuildOnly(true)
                        .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
        );
        commands.queue();

        scheduleDeletionTask(jda, SlashCommandListener.getInstance());
    }

    private static void scheduleDeletionTask(JDA jda, SlashCommandListener listener) {
        Timer timer = new Timer();
        LocalTime targetTime = LocalTime.of(12, 0); // 12 PM UTC
        long initialDelay = Duration.between(LocalTime.now(ZoneOffset.UTC), targetTime).toMillis();
        if (initialDelay < 0) {
            initialDelay += Duration.ofDays(1).toMillis();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (String channelId : listener.getRegisteredChannels()) {
                    jda.getTextChannelById(channelId).getIterableHistory().queue(messages -> {
                        messages.forEach(message -> {
                            if (!message.isPinned()) {
                                message.delete().queue();
                            }
                        });
                    });
                }
            }
        }, initialDelay, Duration.ofDays(1).toMillis());
    }


}
