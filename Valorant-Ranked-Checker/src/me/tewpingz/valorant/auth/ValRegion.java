package me.tewpingz.valorant.auth;

import java.util.HashMap;

public enum ValRegion {

	NA("https://pd.na.a.pvp.net", "America"), EU("https://pd.eu.a.pvp.net", "Europe"),
	AP("https://pd.ap.a.pvp.net", "Asia", "Oceania", "AU/NZ"), KO("https://pd.ko.a.pvp.net", "Korea"), BR("https://pd.br.a.pvp.net", "Brazil");

	private final String url;

	public static HashMap<String, ValRegion> allNames;

	String[] names;

	ValRegion(String url, String... names) {
		this.url = url;
		this.names = names;
	}

	public String getURL() {
		return this.url;
	}

	public String getUrl() {
		return this.url;
	}

	public static HashMap<String, ValRegion> allNames() {
		if (allNames == null) {
			allNames = new HashMap<String, ValRegion>();
			for (ValRegion a : values())
				for (String name : a.names)
					allNames.put(name,a);

		}

		return allNames;
	}

}
