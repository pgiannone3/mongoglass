package configurationConnect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.ini4j.Wini;
import upperPanel.*;

public class Configs {

	private String connectionName;
	private String serverName;
	private String portNumber;
	private Wini ini;

	public Configs(String connectionName, String serverName, String portNumber) {

		this.connectionName = connectionName;
		this.serverName = serverName;
		this.portNumber = portNumber;



		try{

			File file = new File("config.ini");
			if(!file.exists()) {

				file.createNewFile();
			}

			this.ini = new Wini(file);;
		}

		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public Configs() {

		try{

			File file = new File("config.ini");
			if(!file.exists()) {

				file.createNewFile();
			}

			this.ini = new Wini(file);
		}

		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void writeLimitAndDepth(int limit, int depth) {
		try{
			int count = countConnections();
			for(int i = 1; i<=count; i++) {

				String category = "Connection" + i;

				String connectionName = this.ini.get(category, "Connection_name", String.class); 
				String serverName = this.ini.get(category, "Server_name", String.class) ;
				int portNumber = this.ini.get(category,"Port_number",int.class);

				if(this.connectionName.equals(connectionName) && 
						this.serverName.equals(serverName) &&
						Integer.parseInt(this.portNumber) == portNumber) {

					this.ini.put(category,"LimitDocuments",limit);
					this.ini.put(category,"MaxDepth",depth);

					this.ini.store();
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public LimitDepth readLimitAndDepth(String connection, String server, int port) {

		int count = countConnections();
		LimitDepth ld = null;

		for(int i = 1; i<=count; i++) {

			String category = "Connection" + i;

			String connectionName = this.ini.get(category, "Connection_name", String.class); 
			String serverName = this.ini.get(category, "Server_name", String.class) ;
			int portNumber = this.ini.get(category,"Port_number",int.class);

			if(connectionName!= null && serverName!= null && portNumber != 0) {
				if(connectionName.equals(connection) && server.equals(serverName) && port == portNumber) {

					int limit = this.ini.get(category, "LimitDocuments", int.class);
					int depth = this.ini.get(category, "MaxDepth", int.class);

					ld = new LimitDepth(limit, depth);
					break;
				}
			}	
		}
		
		return ld;
	
	}


	public void writeConfig(){		

		try {
			int count = 0;

			if(ini.get("Settings","Connections_count",int.class) != 0)
				count = ini.get("Settings","Connections_count",int.class);

			count++;

			String setting = "Settings";

			this.ini.put(setting, "Connections_count", count);

			String connection = "Connection";

			ini.put(connection + count, "Connection_name", connectionName);

			ini.put(connection + count, "Server_name",serverName);

			ini.put(connection + count, "Port_number", Integer.parseInt(portNumber));

			ini.put(connection + count, "LimitDocuments", 0);

			ini.put(connection + count, "MaxDepth", 0);

			this.ini.store();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public ArrayList<ConfigurationRecord> readConfig() {

		int count = countConnections();
		ArrayList<ConfigurationRecord> configurationRecord = new ArrayList<>();

		for(int i = 1; i<=count; i++) {

			String category = "Connection" + i;

			String connectionName = this.ini.get(category, "Connection_name", String.class); 
			String serverName = this.ini.get(category, "Server_name", String.class) ;
			int portNumber = this.ini.get(category,"Port_number",int.class);
			if(connectionName!= null && serverName!= null && portNumber != 0) {
				ConfigurationRecord connection = new ConfigurationRecord(connectionName, serverName, portNumber);
				configurationRecord.add(connection);
			}

		}
		return configurationRecord;
	}

	public int countConnections() {

		int count = this.ini.get("Settings", "Connections_count",int.class);
		return count;
	}

	public void deleteSection(String connectionNameString, String serverNameString, int portNumberString) {

		try{
			boolean found = false;
			int count = countConnections();

			for(int i = 1; i<=count; i++) {

				if(found) {
					int index = i+1;
					String nextCategory = "Connection" + index;
					String currentCategory = "Connection" + i;

					String connectionName = this.ini.get(nextCategory, "Connection_name", String.class); 
					String serverName = this.ini.get(nextCategory, "Server_name", String.class) ;
					String portNumber = this.ini.get(nextCategory,"Port_number",String.class);
					int limitDocuments = this.ini.get(nextCategory, "LimitDocuments", int.class);
					int maxDepth = this.ini.get(nextCategory, "MaxDepth", int.class);

					this.ini.remove(this.ini.get(nextCategory));

					this.ini.store();

					this.ini.put(currentCategory, "Connection_name", connectionName);
					this.ini.put(currentCategory, "Server_name",serverName);
					this.ini.put(currentCategory, "Port_number", Integer.parseInt(portNumber));
					this.ini.put(currentCategory, "LimitDocuments", limitDocuments);
					this.ini.put(currentCategory, "MaxDepth", maxDepth);

					this.ini.store();

				}

				else{
					String category = "Connection" + i;

					String connectionName = this.ini.get(category, "Connection_name", String.class); 
					String serverName = this.ini.get(category, "Server_name", String.class) ;
					int portNumber = this.ini.get(category,"Port_number",int.class);

					if(connectionNameString.equals(connectionName) && 
							serverNameString.equals(serverName) &&
							portNumberString == portNumber) {

						this.ini.remove(this.ini.get(category));
						found = true;
						i = i-1;
						count = count -1;


						this.ini.store();

					}
				}


			}

			String settings = "Settings";
			this.ini.remove(this.ini.get(settings));

			this.ini.store();

			this.ini.put(settings,"Connections_count",count);

			this.ini.store();
		}

		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}


