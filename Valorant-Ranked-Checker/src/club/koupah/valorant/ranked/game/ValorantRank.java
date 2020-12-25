package club.koupah.valorant.ranked.game;

public enum ValorantRank {

	Unranked(2), Iron_1(3), Iron_2(4), Iron_3(5), Bronze_1(6), Bronze_2(7), Bronze_3(8), Silver_1(9), Silver_2(10),
	Silver_3(11), Gold_1(12), Gold_2(13), Gold_3(14), Platinum_1(15), Platinum_2(16), Platinum_3(17), Diamond_1(18),
	Diamond_2(19), Diamond_3(20), Immortal_1(21), Immortal_2(22), Immortal_3(23), Radiant(24);

	int tier;
	String name;

	ValorantRank(int tier) {
		this.tier = tier;
		this.name = name().replace("_", " ");
	}

	public static ValorantRank getRank(int tier) {
		// Radiant and Unranked checks incase of weird states I havent checked
		if (tier >= Radiant.tier)
			return Radiant;

		if (tier <= Unranked.tier)
			return Unranked;

		for (ValorantRank rank : values()) {
			if (rank.tier == tier)
				return rank;
		}
		return null;
	}

	public int compare(ValorantRank compareTo) {
		return compareTo.tier - tier;
	}

}
