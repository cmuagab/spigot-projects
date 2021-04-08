package fr.lowtix.warcore.modules;

import java.util.ArrayList;
import java.util.List;

import fr.lowtix.warcore.WarCore;
import fr.lowtix.warcore.managers.PlayerWrapper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ToggleModule {
	
	private List<String> toggled;
	
	public ToggleModule() {
		toggled = new ArrayList<String>();
		toggled = WarCore.getInstance().getToggleTable().getAllIgnoreAll();
	}

	public void toggle(ProxiedPlayer from) {
		
		PlayerWrapper wfrom = WarCore.getInstance().getWrapper(from);
		
		if(!from.hasPermission("warcore.togglemessages")) {
			WarCore.getInstance().sendMessage(from, "Vous n'avez pas les privil�ges n�c�ssaires.", ChatColor.RED);
			return;
		}
		
		wfrom.setToggle(!wfrom.isToggle());
		
		if(wfrom.isToggle()) {
			WarCore.getInstance().getToggleTable().removeIgnoreAll(from.getName());
			toggled.remove(from.getName().toLowerCase());
			WarCore.getInstance().sendMessage(from, "Vous avez activ� les messages priv�s.", ChatColor.GREEN);
		} else {
			WarCore.getInstance().getToggleTable().setIgnoreAll(from.getName());
			toggled.add(from.getName().toLowerCase());
			WarCore.getInstance().sendMessage(from, "Vous avez d�sactiv� les messages priv�s.", ChatColor.RED);
		}
		
	}
	
	public List<String> getToggled() {
		return toggled;
	}
	
}
