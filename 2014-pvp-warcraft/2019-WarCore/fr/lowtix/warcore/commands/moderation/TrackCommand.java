package fr.lowtix.warcore.commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.lowtix.warcore.WarCore;
import fr.lowtix.warcore.WarPlayer;
import fr.lowtix.warcore.enums.Ranks;

public class TrackCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {

			Player player = (Player) sender;
			WarPlayer wPlayer = WarCore.getInstance().getUser(player);
			
			if(wPlayer.getRank().isHigher(Ranks.TRIAL_MOD)) {
				
				String trackName = "";
				
				if(args.length == 1) {
					
					trackName = args[0];
					
					if(trackName.equalsIgnoreCase("on")) {
						if(!wPlayer.getModPlayer().isMod()) {
							player.sendMessage("�6Mod�ration �8� �7Activation du mode Mod�ration...");
							WarCore.getInstance().staffModManager.activeMod(wPlayer);
							return true;
						} else {
							
							if(wPlayer.getModPlayer().isTrack()) {
								WarCore.getInstance().staffChatManager.sendModMessage(Ranks.TRIAL_MOD, "�d[Track] �e"+player.getName()+" �8� �7TrackMode sur �f"+wPlayer.getModPlayer().getTrack()+" �8[�cOFF�8]");
								wPlayer.getModPlayer().removeTrack();
								player.sendMessage("�6Mod�ration �8� �7Votre track est d�sormais retir�.");
							} else {
								player.sendMessage("�6Mod�ration �8� �cVous �tes d�j� en mode Mod�ration.");
							}
							
							
							return true;
						}
					} else if(trackName.equalsIgnoreCase("off")) {
						if(wPlayer.getModPlayer().isMod()) {
							
							if(wPlayer.getModPlayer().isTrack()) {
								WarCore.getInstance().staffChatManager.sendModMessage(Ranks.TRIAL_MOD, "�d[Track] �e"+player.getName()+" �8� �7TrackMode sur �f"+wPlayer.getModPlayer().getTrack()+" �8[�cOFF�8]");
								wPlayer.getModPlayer().removeTrack();
							}
							
							player.sendMessage("�6Mod�ration �8� �7D�sactivation du mode Mod�ration...");
							WarCore.getInstance().staffModManager.desactiveMod(wPlayer);
							return true;
						} else {
							player.sendMessage("�6Mod�ration �8� �cVous n'�tes pas en mode Mod�ration.");
							return true;
						}
					} else {
						if(Bukkit.getPlayer(trackName) != null && Bukkit.getPlayer(trackName).isOnline()) {
							
							WarCore.getInstance().staffModManager.track(wPlayer, Bukkit.getPlayer(trackName));
							
						} else {
							player.sendMessage("�6Mod�ration �8� �cFaites �e/track <on|off|joueur>.");
							return true;
						}
					}
				}
				
			} else {
				player.sendMessage("�6Mod�ration �8� �cCette commande est r�serv�e aux membres de la Mod�ration.");
				return true;
			}
			
		}
		return true;
	}

}
