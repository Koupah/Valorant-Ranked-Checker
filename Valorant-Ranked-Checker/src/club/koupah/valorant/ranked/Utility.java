package club.koupah.valorant.ranked;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import club.koupah.valorant.ranked.game.RankedGame;
import me.tewpingz.valorant.ValorantAPI;
import me.tewpingz.valorant.auth.ValAuthentication;
import me.tewpingz.valorant.auth.ValRegion;

public class Utility {
	
	public static void getRankedGames(ValAuthentication auth, ValRegion region, int matchCount) {
		if (!RankedChecker.loggedIn || auth == null || region == null) {
			JOptionPane.showMessageDialog(null, "You need to be logged in!");
			return;
		}

		RankedGame.rankedGames.clear();
		for (int i = 0; i < (matchCount/20); i++) //60 games by default
		try {
			JsonObject compMatches = ValorantAPI.getCompetitiveHistory(auth, region, i*20);
			JsonArray matches = compMatches.getAsJsonArray("Matches");
			
			if (matches == null)
				continue;
			
			Iterator<JsonElement> matchIterator = matches.iterator();
			while (matchIterator.hasNext()) {
				JsonObject m = matchIterator.next().getAsJsonObject();
				if (m.get("TierAfterUpdate").getAsInt() == 0 /* || m.get("CompetitiveMovement").getAsString().equals("MOVEMENT_UNKNOWN") THIS CAUSES FINAL PLACEMENT MATCH TO NOT SHOW*/ ) {
					System.out.println("Unrated: " + m.toString());
				} else {
					new RankedGame(m);
				}

			}
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}

		RankedChecker.rankPanel.removeAll();
		int i = 0;
		for (RankedGame game : RankedGame.rankedGames) {
			RankedChecker.rankPanel.add(game);
			game.setBounds(10, (10) + i * 90, 650, 85);
			i++;
		}
		RankedChecker.rankPanel.setPreferredSize(new Dimension(0, 20 + (i * 90)));
		RankedChecker.rankPanel.repaint();
	}
	
	public static void login() {
		Object[] options = { "Login", "Cancel" };

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));

		JTextField username = new JTextField();
		username.setEditable(true);

		JPasswordField password = new JPasswordField();
		JComboBox<String> regionSelection = new JComboBox<String>();

		for (Map.Entry<String, ValRegion> entry : ValRegion.allNames().entrySet()) {
			regionSelection.addItem(entry.getKey());
		}

		panel.add(new JLabel("Username"));
		panel.add(username);
		panel.add(new JLabel("Password"));
		panel.add(password);
		panel.add(new JLabel("Region"));
		panel.add(regionSelection);

		int result = JOptionPane.showOptionDialog(null, panel, "Valorant Login", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, null);

		if (result == 0) { // they logged in
			try {
				RankedChecker.auth = ValorantAPI.login(username.getText(), new String(password.getPassword()));
				RankedChecker.region = ValRegion.allNames().get(regionSelection.getSelectedItem());
				RankedChecker.loggedIn = true;
			} catch (Exception e) {
				RankedChecker.loggedIn = false;
				JOptionPane.showMessageDialog(null, "Login failed!\nInfo for nerds: " + e.getMessage());
				return;
			}
		}

	}
}
