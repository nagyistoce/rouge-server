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

import java.util.Collection;
import java.util.LinkedList;

import org.hyperic.sigar.Sigar;

public class ResourceMonitor implements Runnable {

	private final static int MAX_SAMPLE = 100;

	private LinkedList<ResourceTimePoint> timePoints;
	private Sigar sigar;
	private boolean running;

	public ResourceMonitor() {

		this.timePoints = new LinkedList<ResourceTimePoint>();
		this.sigar = new Sigar();
		this.running = true;
	}

	@Override
	public void run() {

		while (running) {
	
			try {
				ResourceTimePoint timePoint = new ResourceTimePoint(System.currentTimeMillis(),
						sigar.getCpuPerc().getCombined(),
						sigar.getMem().getTotal(),
						sigar.getNetStat().getAllInboundTotal(),
						sigar.getNetStat().getAllOutboundTotal());
				
				synchronized (this.timePoints) {
					this.timePoints.addLast(timePoint);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (this.timePoints.size() > MAX_SAMPLE) {
				this.timePoints.removeFirst();
			}

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void stop() {
		this.running = false;
	}

	public Collection<ResourceTimePoint> getTimePoints() {
		
		LinkedList<ResourceTimePoint> toReturn = new LinkedList<ResourceTimePoint>();
		
		synchronized (this.timePoints) {
			toReturn.addAll(this.timePoints);
		}
		
		return toReturn;
	
		
	}
	
}



