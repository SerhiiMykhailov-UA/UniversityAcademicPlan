package ua.foxminded.databasegeneration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

public final class GroupGenerate {
	
	public static List<String> groupGenerate() {
		List<String> groupsList = new ArrayList<>();
		
		while (groupsList.size()<10) {
			String g1 = RandomStringUtils.random(2, 65, 91, true, false);
			String g2 = RandomStringUtils.random(2, 49, 59, false, true);
			String g = g1 + "-" + g2;
			if (!groupsList.contains(g)) {
				groupsList.add(g);
			}
		}
		return groupsList;
	}
}
