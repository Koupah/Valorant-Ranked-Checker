package me.tewpingz.valorant.auth;

public class ValAuthentication {

    /* The uniqueId of the account */
    private final String uniqueId;

    /* The verification token for the account. This expire after 10-15 minutes of authentication */
    private final String token;

    /* The entertainment token. This expire after 10-15 minutes of authentication */
    private final String entertainmentToken;
    
    public ValAuthentication(String uuid, String token, String entToken) {
   	 this.uniqueId = uuid;
   	 this.token = token;
   	 this.entertainmentToken = entToken;
    }

	public String getToken() {
		return this.token;
	}

	public String getEntertainmentToken() {
		return this.entertainmentToken;
	}

	public String getUniqueId() {
		return this.uniqueId;
	}
    
}
