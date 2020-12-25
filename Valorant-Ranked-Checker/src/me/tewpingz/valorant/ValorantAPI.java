package me.tewpingz.valorant;

import com.google.gson.JsonObject;
import me.tewpingz.valorant.auth.ValAuthentication;
import me.tewpingz.valorant.auth.ValHeader;
import me.tewpingz.valorant.auth.ValRegion;
import org.apache.http.HttpHeaders;

import java.io.IOException;

public class ValorantAPI {

    private static final Valorant valorantInstance = new Valorant();

    /**
     * Authenticate yourself to access the api with your login and password
     *
     * @param username the username of the account.
     * @param password the password of the account.
     * @return an authentication object with uniqueId, token and entertainment token.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static ValAuthentication login(String username, String password) throws Exception {
        valorantInstance.resetExecutor();
        return valorantInstance.auth(username, password);
    }
    
    /*
     * Remove other API functions as they're not needed in this program
     */
    
    //Added startFrom
    /**
     * Fetches your past 10 valorant competitive matches.
     * Make sure the region is the same region as the account because it will give you incorrect data.
     *
     * @param valAuthentication the valorant authentication object you receive when executing ValorantAPI#login
     * @param valRegion the region the account is located in for the correct information.
     * @param startFrom the number of matches to start from, eg: 20 to start from the 20th match to the 40th
     * @return a JsonObject competitive the past 10 competitive matches.
     * @throws IOException if the request gets invalidated or you get rate limited.
     */
    public static JsonObject getCompetitiveHistory(ValAuthentication valAuthentication, ValRegion valRegion, int startFrom) throws IOException {
       System.out.println("Fetching match " + startFrom + " to " + (startFrom+20));
   	 ValHeader authHeader = new ValHeader(HttpHeaders.AUTHORIZATION, "Bearer " + valAuthentication.getToken());
        ValHeader enHeader = new ValHeader("X-Riot-Entitlements-JWT", valAuthentication.getEntertainmentToken());
        return valorantInstance.get(valRegion.getUrl() + "/mmr/v1/players/" + valAuthentication.getUniqueId() + "/competitiveupdates?startIndex=" + startFrom + "&endIndex=" + (startFrom+20),
                authHeader, enHeader).getAsJsonObject();
    }
}
