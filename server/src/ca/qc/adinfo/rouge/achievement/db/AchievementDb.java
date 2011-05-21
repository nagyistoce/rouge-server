/*
 * Copyright [2011] [ADInfo, Alexandre Denault]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.qc.adinfo.rouge.achievement.db;

import java.util.Collection;

import ca.qc.adinfo.rouge.achievement.Achievement;
import ca.qc.adinfo.rouge.server.DBManager;

public class AchievementDb {
	
	// TODO: Implement Achievement DB
	
	public static Achievement getAchievement(DBManager bdManager, String key) {
		
		return null;
	}
	
	public static Collection<Achievement> getAchievements(DBManager bdManager) {
		
		return null;
	}
	
	public static void createAchievement(DBManager bdManager, String key, double max) {
		
	}
	
	public static void updateAchievement(DBManager bdManager, String key, double value) {
		
	}

}
