package club.koupah.valorant.ranked.game;

public enum ValorantMap {

	Haven("/Game/Maps/Triad/Triad"),
	Split("/Game/Maps/Bonsai/Bonsai"),
	IceBox("/Game/Maps/Port/Port"),
	Bind("/Game/Maps/Duality/Duality"),
	Ascent("/Game/Maps/Ascent/Ascent"); //Only one with it's actual name??
	
	String properName;
	String id;
	
	ValorantMap(String ID) {
		this.id = ID;
		this.properName = this.name();
	}
	
	public static ValorantMap getMap(String ID) {
		for (ValorantMap map : values()) {
			if (map.id.endsWith(ID) || map.id.equalsIgnoreCase(ID))
				return map;
		}
		return null;
	}
	
}
