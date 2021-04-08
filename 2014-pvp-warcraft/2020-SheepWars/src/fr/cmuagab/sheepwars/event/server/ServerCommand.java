package fr.cmuagab.sheepwars.event.server;

import fr.cmuagab.sheepwars.SheepWarsPlugin;
import fr.cmuagab.sheepwars.event.SheepListener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerCommandEvent;

public class ServerCommand extends SheepListener {
    public ServerCommand(final SheepWarsPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onServerCommand(final ServerCommandEvent event) {
        if (event.getCommand().split(" ")[0].contains("reload")) {
            event.setCommand("/reload");
            event.getSender().sendMessage(ChatColor.RED + "Cette fonctionnalit� est d�sactiv�e par le plugin WarOfSheeps � cause de contraintes techniques (reset de map).");
        }
    }
}
