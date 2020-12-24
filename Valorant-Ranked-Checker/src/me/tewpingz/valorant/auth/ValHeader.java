package me.tewpingz.valorant.auth;


public class ValHeader {

   /* The key of the valorant header */
   private final String key;

   /* The value of the valorant header */
   private final String value;
	
	public ValHeader(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
	


}
