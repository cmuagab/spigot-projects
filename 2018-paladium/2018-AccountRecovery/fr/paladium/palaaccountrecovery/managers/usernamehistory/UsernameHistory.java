package fr.paladium.palaaccountrecovery.managers.usernamehistory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import fr.paladium.palaaccountrecovery.PalaAccountRecovery;

public class UsernameHistory {

	String uuid;
	ArrayList<Username> history;

	public UsernameHistory(String name) throws IOException, ParseException {
		JSONUtility utility = PalaAccountRecovery.getInstance().getUsernameHistoryManager().getJsonUtility();
		uuid = (String) ((JSONObject) utility.getJsonObject("https://api.mojang.com/users/profiles/minecraft/" + name)).get("id");
		loadHistory();
	}

	public UsernameHistory(UUID uuid) throws IOException, ParseException {
		this.uuid = uuid.toString();
		loadHistory();
	}

	public ArrayList<Username> getHistory() {
		return history;
	}

	public void loadHistory() throws IOException, ParseException {
		history = new ArrayList<Username>();
		String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
		JSONArray response = (JSONArray) PalaAccountRecovery.getInstance().getUsernameHistoryManager().getJsonUtility().getJsonObject(url);
		for (Object objN : response) {
			JSONObject obj = (JSONObject) objN;
			if (obj.containsKey("error")) {
				throw new IOException("No player exists with that username!");
			}
			String name = (String) obj.get("name");
			long changedToAt = 0;
			if (obj.containsKey("changedToAt")) {
				changedToAt = (Long) obj.get("changedToAt");
			}
			Username usrn = new Username(name, changedToAt);
			history.add(usrn);

		}
	}

}
