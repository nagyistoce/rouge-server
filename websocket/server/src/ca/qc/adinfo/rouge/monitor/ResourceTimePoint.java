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

package ca.qc.adinfo.rouge.monitor;

public class ResourceTimePoint {

	private long time;
	private double cpuUsage;
	private long memUsed;
	private int networkInbound;
	private int networkOutbound;
	
	public ResourceTimePoint(long time, double cpuUsage, long memUsed,
			int networkInbound, int networkOutbound) {
		super();
		this.time = time;
		this.cpuUsage = cpuUsage;
		this.memUsed = memUsed;
		this.networkInbound = networkInbound;
		this.networkOutbound = networkOutbound;
	}

	public long getTime() {
		return time;
	}
	
	public double getCpuUsage() {
		return cpuUsage;
	}
	
	public long getMemUsed() {
		return memUsed;
	}
	
	public int getNetworkInbound() {
		return networkInbound;
	}
	
	public int getNetworkOutbound() {
		return networkOutbound;
	}
	
	
	
	
}