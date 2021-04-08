package eu.pvpwarcraft.tournoipvp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InscriptionCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player){
			
			Player player = (Player) sender;
			
			// /tournoi <name> <coo�quipier>
			
			if(args.length == 2){
				if(args[0].length() <= 10){
					
					if(Bukkit.getPlayer(args[1]) != null && Bukkit.getPlayer(args[1]).isOnline()){
						
						Player target = Bukkit.getPlayer(args[1]);
						
						if(RegistersManager.containName(args[0])){
							player.sendMessage("�8[�4��8] �cCette team existe d�j�.");
							return true;
						}
						
						if(!RegistersManager.containPlayer(player)){
							if(!RegistersManager.containPlayer(target)){
								
								player.sendMessage("�8[�2��8] �aEnvoi de la requ�te en cours...");
								
								try {
									
									if(player.getName().equals(target.getName())){
										player.sendMessage("�8[�4��8] �cVous ne pouvez pas �tre tout seul... Choisissez un coo�quipier.");
										TournoiPvP.sendHelp(player);
										return true;
									}
									
									RegistersManager.saveTeam(args[0], player, target, 0);
									player.sendMessage("�8[�2��8] �7Vous �tes d�sormais inscris au tournoi avec �b"+target.getName());
									target.sendMessage("�8[�2��8] �7Le joueur �b"+player.getName()+" �7s'est inscris avec vous au Tournoi PvP.");
									return true;
									
								} catch (Exception e) {
									e.printStackTrace();
									player.sendMessage("�8[�4��8] �cUne erreur est survenue lors de votre inscription. Contactez un LowTix_");
									return true;
								}
								
							}else{
								player.sendMessage("�8[�4��8] �cVotre coo�quipier est d�j� inscris dans une �quipe.");
								return true;
							}
						}else{
							player.sendMessage("�8[�4��8] �cVous �tes d�j� inscris dans une �quipe.");
							return true;
						}
						
					}else{
						player.sendMessage("�8[�4��8] �cVotre coo�quipier doit �tre connect�.");
						return true;
					}
					
				}else{
					player.sendMessage("�8[�4��8] �cLe nom de votre �quipe doit �tre inf�rieur ou �gal � 10 caract�res.");
					return true;
				}
			}else{
				TournoiPvP.sendHelp(player);
				return true;
			}
		}
		return true;
	}

}
