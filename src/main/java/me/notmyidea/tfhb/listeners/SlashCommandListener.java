package me.notmyidea.tfhb.listeners;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SlashCommandListener extends ListenerAdapter {

    @Getter
    @Setter
    private final Set<String> registeredChannels = new HashSet<>();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "register":
                // Register this channel to be cleaned every 24 hours
                registerChannel(event);
                break;
            case "unregister":
                // Unregister this channel from being cleaned every 24 hours
                unregisterChannel(event);
                break;
        }
    }

    private void registerChannel(SlashCommandInteractionEvent event) {
        String channelId = event.getChannelId();
        registeredChannels.add(channelId);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("24 Hour Cleaning Schedule")
                .setDescription("This channel will be cleaned every 24 hours at exactly <t:1735513200:t>")
                .setColor(Color.RED);
        event.getChannel().sendMessageEmbeds(embed.build()).queue(message -> {
            message.pin().queue();
            event.reply("Channel registered successfully!").setEphemeral(true).queue();
        });
    }

    private void unregisterChannel(SlashCommandInteractionEvent event) {
        String channelId = event.getChannel().getId();
        registeredChannels.remove(channelId);
        event.reply("Channel unregistered successfully!").setEphemeral(true).queue();
    }

    public Set<String> getRegisteredChannels() {
        return registeredChannels;
    }

    // singleton
    private static SlashCommandListener instance;

    public static SlashCommandListener getInstance() {
        if (instance == null) {
            instance = new SlashCommandListener();
        }
        return instance;
    }

}