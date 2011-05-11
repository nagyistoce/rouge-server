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

package ca.qc.adinfo.rouge.variable;

import java.util.HashMap;

public class VariableManager {
	
	private HashMap<String, Variable> variables;
	
	public VariableManager() {
		
		this.variables = new HashMap<String, Variable>();
	}
	
	public Variable getVariable(String key) {
			
		synchronized (this.variables) {
			if (this.variables.containsKey(key)) {
				return this.variables.get(key);
			} else {
				Variable variable = new Variable(key);
				this.variables.put(key, variable);
				return variable;
			}
		}
	}	 

}
