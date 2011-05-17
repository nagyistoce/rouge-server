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

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ca.qc.adinfo.rouge.command.RougeCommand;
import ca.qc.adinfo.rouge.command.RougeCommandProcessor;
import ca.qc.adinfo.rouge.module.RougeModule;
import ca.qc.adinfo.rouge.monitor.ResourceMonitor;
import ca.qc.adinfo.rouge.room.RoomManager;
import ca.qc.adinfo.rouge.server.CoreServer;
import ca.qc.adinfo.rouge.server.DBManager;
import ca.qc.adinfo.rouge.server.WebServer;
import ca.qc.adinfo.rouge.server.core.SessionManager;
import ca.qc.adinfo.rouge.server.servlet.RougePage;
import ca.qc.adinfo.rouge.user.UserManager;
import ca.qc.adinfo.rouge.util.JarLoader;

public class RougeServer {

	private final static Logger log = Logger.getLogger(RougeServer.class);

	private static RougeServer instance;

	private boolean running;

	private DBManager dbManager;
	private UserManager userManager;
	private RoomManager roomManager;
	private SessionManager sessionManager;

	private Properties serverProperties;
	private HashMap<String,Properties> moduleProperties;
	private RougeCommandProcessor commandProcessor;

	private HashMap<String, Object> attachements;
	private HashMap<String, RougeModule> modules;
	private HashMap<String, RougePage> pages;
	
	private ResourceMonitor resourceMonitor;

	private long timeTick;
	
	private RougeServer() throws Exception {

		PropertyConfigurator.configure("./conf/log4j.properties");
		
		running = false;

		attachements = new HashMap<String, Object>();
		modules = new HashMap<String, RougeModule>();
		pages = new HashMap<String, RougePage>();
		moduleProperties = new HashMap<String, Properties>();
		
	}
	
	public void init() throws Exception {
		
		serverProperties = new Properties();
		serverProperties.load(new FileReader(new File("./conf/config.properties")));
		
		userManager = new UserManager();
		roomManager = new RoomManager();
		sessionManager = new SessionManager();

		log.trace("Managers are initialized.");

		commandProcessor = new  RougeCommandProcessor(dbManager, userManager, roomManager);
		resourceMonitor = new ResourceMonitor();

		dbManager = new DBManager(serverProperties);
		
		timeTick = Long.parseLong(serverProperties.getProperty("server.time.tick").trim());
		
		loadPropertiesFile(this.serverProperties);
		
		File extensionDir = new File("extension");
		
		loadJars(extensionDir);
		loadExtension(extensionDir);
		
		log.trace("Everything loaded.");
	}
	
	private void loadExtension(File extensionDir) throws Exception {
		
		File[] propertyFiles = extensionDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				
				return name.endsWith(".properties");
			}
		});
		
		for(File propertyFile: propertyFiles) {
			log.debug("Loading property file: " + propertyFile.getAbsoluteFile());
			
			Properties props = new Properties();
			props.load(new FileReader(propertyFile));
			
			String moduleName = props.getProperty("extension.name");
			
			moduleProperties.put(moduleName, props);
			loadPropertiesFile(props);
		}
		
	}
	
	private void loadJars(File extensionDir) throws Exception {
		
		File[] jarFiles = extensionDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				
				return name.endsWith(".jar");
			}
		});
		
		for(File jarFile: jarFiles) {
			log.debug("Loading jar file: " + jarFile.getAbsoluteFile());
			JarLoader.loadJar(jarFile);
		}
	}

	private void loadPropertiesFile(Properties props) throws Exception {
		
		String commandsFromConfig = props.getProperty("command.load");
		String[] commandsToLoad = commandsFromConfig.split(",");

		for(String commandName: commandsToLoad) {

			String commandClass = props.getProperty("command." + commandName.trim());

			try {
				Class<?> cls = Class.forName(commandClass);
				RougeCommand command = (RougeCommand) cls.newInstance();
				command.setKey(commandClass);
				commandProcessor.registerCommand(command);
			} catch(Exception e) {
				log.error("Could not load command " + commandName + " " + commandClass);
				throw e;
			}
		}

		String modulesFromConfig = props.getProperty("module.load");
		String[] modulesToLoad = modulesFromConfig.split(",");

		for(String moduleName: modulesToLoad) {

			String moduleClass = props.getProperty("module." + moduleName.trim());

			try {
				Class<?> cls = Class.forName(moduleClass);
				modules.put(moduleName.trim(), (RougeModule)cls.newInstance());
			} catch(Exception e) {
				log.error("Could not load module " + moduleName + " " + moduleClass);
				throw e;
			}
		}
		
		String pagesFromConfig = props.getProperty("page.load");
		String[] pagesToLoad = pagesFromConfig.split(",");

		for(String pageName: pagesToLoad) {

			String pageClass = props.getProperty("page." + pageName.trim());

			try {
				Class<?> cls = Class.forName(pageClass);
				pages.put(pageName.trim(), (RougePage)cls.newInstance());
			} catch(Exception e) {
				log.error("Could not load page " + pageName + " " + pageClass);
				throw e;
			}
		}
		
		String pagesForMenu = props.getProperty("page.menu");
		String[] menuItems = pagesForMenu.split(",");

		for(String menuItem: menuItems) {
			RougePage.addToMenu(menuItem.trim());
		}
	}
	
	public static RougeServer getInstance() {

		if (instance == null) {

			try {
				instance = new RougeServer();
				instance.init();
			} catch (Exception e) {

				log.error("Unable to start server. " + e.getMessage());
				e.printStackTrace();

				System.exit(-1);
			}
		}

		return instance;
	}

	public void start() {

		if (running == true) return;

		running = true;
		
		Thread resourceMonitorThread = new Thread(resourceMonitor);
		resourceMonitorThread.setDaemon(true);
		resourceMonitorThread.setName("Resource Monitor");
		resourceMonitorThread.start();

		CoreServer coreServer = new CoreServer(serverProperties, commandProcessor, sessionManager, userManager);
		WebServer webServer = new WebServer(serverProperties);

		coreServer.start();
		webServer.start();

		dbManager.connect();

		long timeStartLoop = System.currentTimeMillis();
		
		while(this.running) {

			try {
				Thread.sleep(timeTick);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			long currentTime = System.currentTimeMillis();
			long interval = currentTime - timeStartLoop;
			timeStartLoop = currentTime;
			
			// I can loop over this safely since modules shouldn't be added
			// at run time.
			for (RougeModule module: this.modules.values()) {
				module.tick(interval);
			}
		}


		dbManager.disconnect();

		coreServer.stop();
		webServer.stop();
		resourceMonitor.stop();
	}

	public Object getAttachement(String key) {
		synchronized (this.attachements) {
			return attachements.get(key);
		}
	}

	public void setAttachement(String key, Object value) {
		synchronized (this.attachements) {
			this.attachements.put(key, value);
		}
	}
	
	public void registerPage(String action, RougePage page) {
		
		this.pages.put(action, page);
	}
	
	public RougePage getPage(String action) {
		
		return this.pages.get(action);
	}

	public DBManager getDbManager() {
		return dbManager;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public Properties getServerProperties() {
		return serverProperties;
	}
	
	public Properties getModuleProperties(String key) {
		return this.moduleProperties.get(key);
	}

	public ResourceMonitor getResourceMonitor() {
		return resourceMonitor;
	}

	public Object getModule(String key) {
		return this.modules.get(key);
	}
	
	public Properties getProperties() {
		return this.serverProperties;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		RougeServer.getInstance().start();
	}

}
