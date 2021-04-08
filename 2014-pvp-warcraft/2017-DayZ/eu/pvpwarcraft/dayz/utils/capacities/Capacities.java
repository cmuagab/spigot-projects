package eu.pvpwarcraft.dayz.utils.capacities;

public enum Capacities {
	
	MEDIC("M�decine"),
	GRENADIER("Grenadier"),
	ECLAIREUR("Eclaireur"),
	SOUTIEN("Soutine");
	
	private String name;

	private Capacities(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
