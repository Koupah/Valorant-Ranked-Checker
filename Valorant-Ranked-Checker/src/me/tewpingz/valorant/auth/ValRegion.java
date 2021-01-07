package me.tewpingz.valorant.auth;

import java.util.HashMap;

public enum ValRegion {

	NA("America"), EU("Europe"),
	AP("Asia", "Oceania", "AU/NZ","Asia Pacific Countries"), KO("Korea"),
	BR("Brazil");

	private final String url;

	public static HashMap<String, ValRegion> allNames;

	String[] names;

	ValRegion(String... names) {
		this.url = String.format("https://pd.%s.a.pvp.net", name().toLowerCase());
		this.names = names;
	}

	public String getURL() {
		return this.url;
	}

	public static HashMap<String, ValRegion> allNames() {
		if (allNames == null) {
			allNames = new HashMap<String, ValRegion>();
			for (ValRegion a : values())
				for (String name : a.names)
					allNames.put(name, a);

		}

		return allNames;
	}

}
