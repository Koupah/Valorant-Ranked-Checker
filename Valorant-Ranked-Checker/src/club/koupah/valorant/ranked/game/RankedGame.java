package club.koupah.valorant.ranked.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;

import com.google.gson.JsonObject;

public class RankedGame extends JComponent {

	private static final long serialVersionUID = 1L;

	public static ArrayList<RankedGame> rankedGames = new ArrayList<RankedGame>();

	int beforeElo;
	int afterElo;

	String compMovement;

	ValorantMap map;

	ValorantRank beforeRank;
	ValorantRank afterRank;

	boolean placementGame;

	public RankedGame(JsonObject m) {
		beforeRank = ValorantRank.getRank(m.get("TierBeforeUpdate").getAsInt());
		afterRank = ValorantRank.getRank(m.get("TierAfterUpdate").getAsInt());

		placementGame = beforeRank.equals(ValorantRank.Unranked) && afterRank.tier > ValorantRank.Unranked.tier;

		beforeElo = m.get("TierProgressBeforeUpdate").getAsInt();
		afterElo = m.get("TierProgressAfterUpdate").getAsInt();

		compMovement = m.get("CompetitiveMovement").getAsString();

		map = ValorantMap.getMap(m.get("MapID").getAsString());

		rankedGames.add(this);

		System.out.println("Ranked Game: " + m.toString());
	}

	Font rankFont = new Font("Tahoma", Font.PLAIN, 20);
	Font textFont = new Font("Tahoma", Font.PLAIN, 14);

	// Anti aliasing
	RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	Color background = new Color(0, 50, 100);

	@Override
	public void paint(Graphics graphics) {
		int height = getHeight();
		int width = getWidth();

		int rankDif = beforeRank.compare(afterRank);
		int difference = (beforeRank.compare(afterRank) * 100) + (afterElo - beforeElo);

		Graphics2D g = (Graphics2D) graphics;
		super.paint(g);

		g.setRenderingHints(aa);

		g.setColor(background);
		g.fillRect(0, 0, getWidth(), height);

		g.setColor(rankDif == 0 ? Color.GRAY : (rankDif > 0 ? Color.GREEN : Color.RED));

		g.setStroke(new BasicStroke(4));
		g.drawRect(0, 0, getWidth(), height);

		g.setFont(rankFont);
		g.setColor(Color.WHITE);

		g.drawString(beforeRank.name, (width / 2) - (g.getFontMetrics().stringWidth(beforeRank.name) / 2), 25);

		g.setFont(textFont);

		/*
		 * LEFT SIDE
		 */

		String map = "Map: " + this.map.properName;
		g.drawString(map, 5, height - 38 - 2);

		String progress = "Progress: " + afterElo + "/100 (" + ( placementGame ? "Final Placement" : difference + (difference >= 0 ? " gained" : " lost")) + ")";
		g.drawString(progress, 5, height - 21 - 2);
		
		String movement = "Movement: " + (placementGame ? "Final Placement (Received Rank!)"
				: compMovement.replace("_", " ")
						+ (rankDif != 0 ? " (" + beforeRank.name + " -> " + afterRank.name + ")" : ""));
		g.drawString(movement, 5, height - 7);

		/*
		 * RIGHT SIDE
		 */

		int rankup = (100 - afterElo);
		String nextRankup = "You need " + (rankup) + " elo to reach " + (ValorantRank.getRank(afterRank.tier + 1).name);

		g.drawString(nextRankup, width - g.getFontMetrics().stringWidth(nextRankup) - 5, height - 21 - 2);

		String gamesTillRankup = "Approximately " + ((double) ((double) rankup / (double) 20))
				+ " wins until you rank up!";
		g.drawString(gamesTillRankup, width - g.getFontMetrics().stringWidth(gamesTillRankup) - 5, height - 7 - 2);

	}

}
