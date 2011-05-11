<!DOCTYPE html>
<!--
   Copyright [2011] [ADInfo, Alexandre Denault]
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
  
     http://www.apache.org/licenses/LICENSE-2.0
  
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 -->
<html>
	<head>
		<title>
			Nova Server
		</title>
		<link rel="stylesheet" href="/css/screen.css" type="text/css" />
		<link rel="stylesheet" href="/css/ie.css" type="text/css" />
		<link rel="stylesheet" href="/css/BenevolentDictator.css" type="text/css" />
		<link rel="stylesheet" href="/css/nova.css" type="text/css" />
		<script src="/css/jquery-1.4.2.min.js">
		</script>
	</head>
	<body>
		<div class="container" id="BodyContainer">

			<hr class="h100" />

			<div class="span-24 last">

				<div class="prepend-8 span-8 append-8">

					<h1>Nova Server</h1>
					
					<form action="/secure" method="POST">
					<div class="Section">
						<div class="SectionHeading">Administration</div>
						<div class="SectionContent">
	
							<dl>
								<dt>
									<label>Username:</label>
								</dt>
								<dd>
									<input type="text" name="username">
									</dd>
									<dt>
										<label>Password:</label>
									</dt>
									<dd>
										<input type="password" name="password">
										</dd>

										<dt>
										</dt>
										<dd>
											<button>Submit</button>
										</dd>
									</dl>
								</div>
							</div>
						</div>
						</form>
					</div>
				</div>

			</body>
		</html>