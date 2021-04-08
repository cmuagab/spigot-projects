package fr.lowtix.warcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lowtix.warcore.WarCore;
import fr.lowtix.warcore.WarPlayer;
import fr.lowtix.warcore.enums.Ranks;

public class AFKCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			WarPlayer wPlayer = WarCore.getInstance().getUser(player);
			
			if(!wPlayer.getRank().isHigher(Ranks.DIAMOND)) {
				player.sendMessage("�bInfo �8� �cVous devez avoir le grade �b�lDIAMOND �cou sup�rieur pour faire cela.");
				return true;
			} else {
				if(!wPlayer.getPlayerStats().isAfk()) {
					wPlayer.getPlayerStats().setAfk(true);
					wPlayer.getTabCustom().reload();
					player.sendMessage("�6Options �8� �7Mode AFK �aactiv�7.");
					player.sendMessage("�8�l��6-�8�l��8�m------------------------------------------�8�l��6-�8�l�");
					player.sendMessage("  �7Vous �tes d�sormais en �emode AFK�7. Les mentions dans le chat");
					player.sendMessage("  �7sont alors d�sactiv�es. Vous pourrez le d�sactiver en faisant");
					player.sendMessage("  �7de nouveau la commande. Pour rappel, l'AFK-Farm est interdit.");
					player.sendMessage("�8�l��6-�8�l��8�m------------------------------------------�8�l��6-�8�l�");
					return true;
				} else {
					wPlayer.getPlayerStats().setAfk(false);
					wPlayer.getTabCustom().reload();
					player.sendMessage("�6Options �8� �7Mode AFK �cd�sactiv�7.");
					return true;
				}
			}
		}
		return true;
	}

}
