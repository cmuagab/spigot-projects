package fr.lowtix.warcore.enums;

public enum ReportState {
	
	NO_TRAITEMENT("�cNon trait�"),
	TRAITEMENT("�6En traitement"),
	TRAITED("�2Trait�");

	private String display;

	private ReportState(String display) {
		this.display = display;
	}

	public String getDisplay() {
		return display;
	}

}
