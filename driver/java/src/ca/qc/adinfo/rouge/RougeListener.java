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

package ca.qc.adinfo.rouge;

import ca.qc.adinfo.rouge.data.RougeObject;

public interface RougeListener {
	
	public void onConnect();
	public void onDisconnect();
	
	public void onError(int seq, String command, RougeObject error);
	public void onOtherMessage(String command, RougeObject payload);
	
//	public void onSay(String from, RougeObject message);
//	
//	public void onRoomCreated(long seq, String name);
//	public void onRoomDestroyed(long seq, String name);
//	public void onRoomJoined(long seq, String name);
//	public void onRoomLeft(long seq, String name);
//	public void onRoomSaid(long seq, String name);
//	
//	public void onRoomSay(String from, RougeObject message);
//	
//	public void onVariableGot(long seq, String key, RougeObject variable);
//	public void onVariableSet(long seq, String key);
//	public void onVariableSubcribed(long seq, String key);
//	public void onVariableUnsubscribed(long seq, String key);
//	
//	public void onVariableUpdated(String key, RougeVariable variable);
//
//	public void onPersistentVariableGot(long seq, String key, RougeObject variable, long version);
//	public void onPersistentVariableSet(long seq, String key);
//	
//	public void onFriendAdded(long seq, String friend);
//	public void onFriendRemoved(long seq, String friend);
//	public void onFriendListReceived(long seq, Collection<RougeFriend> friends);

//	public void onIM(String from, String message);
//	
//	public void onSentMail(long seq);
//	public void onGetAllMail(long seq,  Collection<RougeObject> mails);
//	public void onGetUnreadMail(long seq,  Collection<RougeObject> mails);
//	public void onMailIsMarkedRead(long seq);
//	public void onDeleteMail(long seq);
//	
//	public void onGetLeaderboard(String key, RougeLeaderboard leaderboard);
//	public void onSubmitScore(String key);
//	
//	public void onGotAchievements(long seq, Collection<RougeAchievement> achievements);
//	public void onUpdateAchivementProgress(String key);
	
	
	

	
	
}
