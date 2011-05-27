/*
 * Copyright [2011] [ADInfo, Alexandre Denault]
 *
 * Licensed under the Apache License, Version 2.0 (the "License") { }
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

package ca.qc.adinfo.rouge;

import java.util.Collection;

import ca.qc.adinfo.rouge.data.RougeAchievement;
import ca.qc.adinfo.rouge.data.RougeLeaderboard;
import ca.qc.adinfo.rouge.data.RougeMail;
import ca.qc.adinfo.rouge.data.RougeObject;
import ca.qc.adinfo.rouge.data.RougeVariable;



public abstract class RougeListener {
	
	public void onConnect() { }
	
	public void onDisconnect() { }
	
	public void onLogin() { }
	public void onUserCreated(long id) { }
	
	public void onError(String command, RougeObject error) { }
	public void onOtherMessage(String command, RougeObject payload) { }
	
	public void onRoomCreated(String name) { }
	public void onRoomDestroyed(String name) { }
	public void onRoomJoined(String name) { }
	public void onRoomLeft(String name) { }
	public void onRoomSaid(String name) { }
	public void onRoomWhoIs(String name, Collection<String> users) { }
	
	public void onRoomSay(String name, String from, RougeObject message) { }
	
	public void onVariableGot(RougeVariable variable) { }
	public void onVariableSet(String key) { }
	public void onVariableSubcribed(String key) { }
	public void onVariableUnsubscribed(String key) { }
	
	public void onVariableUpdated(String key, RougeVariable variable) { }

	public void onPersistentVariableGot(RougeVariable variable) { }
	public void onPersistentVariableSet(String key) { }
	
	public void onIM(String from, String message) { }
	
	public void onSentMail() { }
	public void onGetAllMail(Collection<RougeMail> mails) { }
	public void onGetUnreadMail(Collection<RougeMail> mails) { }
	public void onMailIsMarkedRead() { }
	public void onDeleteMail() { }
	
	public void onGetLeaderboard(RougeLeaderboard leaderboard) { }
	public void onGetLeaderboards(Collection<RougeLeaderboard> leaderboard) { }
	public void onSubmitScore(String key) { }
	
	public void onGotAchievements(Collection<RougeAchievement> achievements) { }
	public void onUpdateAchivementProgress(String key) { }
	
}
