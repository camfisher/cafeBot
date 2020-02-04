package com.github.camfisher.commands;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.exception.MissingPermissionsException;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HelpCommand implements MessageCreateListener
{
    /*
     * This is a help command
     *
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event)
    {

        String prefix = "!";
        try (InputStream input = new FileInputStream("config.cfg"))
        {

            Properties prop = new Properties();

            // Load config.cfg
            prop.load(input);

            // Get property values
            //----------------------------------------------------------------------------------------------------------
            // Get bot prefix
            if (prop.getProperty("bot.pfx").length() > 0)
            {
                prefix = prop.getProperty("bot.pfx");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }



        // Check if the message content equals "!Help"
        if (event.getMessageContent().equalsIgnoreCase( prefix + "Help"))
        {
            MessageAuthor author = event.getMessage().getAuthor();
            event.getChannel().sendMessage("<@" + author.getIdAsString() + ">");
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Help Message")
                    .addField("TODO", "Make the bot actually useful", true)
                    .setAuthor(author);
            event.getChannel().sendMessage(embed)
                    .exceptionally(ExceptionLogger.get(MissingPermissionsException.class));
        }
    }
}
