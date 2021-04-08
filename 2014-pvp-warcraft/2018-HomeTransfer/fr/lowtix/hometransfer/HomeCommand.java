package fr.lowtix.hometransfer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.earth2me.essentials.User;

public class HomeCommand implements CommandExecutor {

	private HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(cooldowns.containsKey(player.getName())) {
				
				long time = cooldowns.get(player.getName());
				
				if((System.currentTimeMillis() - time) < (60*1000)) {
					player.sendMessage("�8[�3�lW�4Admin�8] �6Cooldown: �cAttendez 1 minute entre deux ex�cutions de cette commande!");
					return true;
				}
				
			}
			
			cooldowns.put(player.getName(), System.currentTimeMillis());
			
			sender.sendMessage("�8[�3�lW�4Admin�8] �6Tentative de transfert de vos homes �e�koOo�r�6...");
			
			UUID uuid = player.getUniqueId();

			player.sendMessage("�8[�3�lW�4Admin�8] �6Connexion a notre base de donn�e...");

			MySQL.tryConnect();
			try {

				if (MySQL.contains("MORETPPLUS_HOME", "Owner", uuid.toString(), false)) {

					player.sendMessage("�8[�3�lW�4Admin�8] �aConnexion r�ussie! �6Nous tentons de trouver vos homes...");

					PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM MORETPPLUS_HOME WHERE Owner = ?");
					ps.setString(1, uuid.toString());
					ResultSet result = ps.executeQuery();

					while (result.next()) {

						User user = HomeTransfer.getInstance().getEss().getUser(player.getName());
						
						if(!user.getHomes().contains(result.getString("Name"))) {
							
							try {
								user.setHome("transfer_" + result.getString("Name"), transform(result.getString("Location")));
								player.sendMessage("�8[�3�lW�4Admin�8] �6Transfert du home �b"+ result.getString("Name") + " �6r�ussi. Il est nomm� �ctransfer_"+ result.getString("Name")+"�6.");
							} catch (Exception e) {
								player.sendMessage("�8[�3�lW�4Admin�8] �6Le transfert du home �b"+ result.getString("Name") + " �6a �chou�.");
								e.printStackTrace();
							}
							
							
						} else {
							
							player.sendMessage("�8[�3�lW�4Admin�8] �6Le transfert du home �b"+ result.getString("Name") + " �6a �chou�: Vous avez d�j� un home avec ce nom.");
							
						}

					}
					result.close();
					ps.close();

					player.sendMessage("�8[�3�lW�4Admin�8] �6Tous vos homes ont �t� transf�r�s!");
				} else {
					player.sendMessage("�8[�3�lW�4Admin�8] �cErreur, nous vous avons aps trouv� dans nos bases de donn�e des homes...");
				}

			} catch (Exception e) {
				e.printStackTrace();
				player.sendMessage("�8[�3�lW�4Admin�8] �cErreur, la connexion a �chou�e!");
			}
			
			
		}
		
		return true;
	}
	
	
	private Location transform(String s) {
		
		String[] h = s.split("<>");
		return new Location(Bukkit.getWorld(h[0]), Double.parseDouble(h[1]), Double.parseDouble(h[2]), Double.parseDouble(h[3]), (float) Double.parseDouble(h[4]), (float) Double.parseDouble(h[5]));
		
		
	}

}
