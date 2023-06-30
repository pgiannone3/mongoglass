package configurationConnect;


public class ConfigurationRecord{

	private String connectionName;
	private String serverName;
	private int portNumber;
	
	
	public ConfigurationRecord(String connectionName, String serverName, int portNumber) {
		
		this.connectionName = connectionName;
		this.serverName = serverName;
		this.portNumber = portNumber;
		
	}
	
	public String getConnectionName() {
		return connectionName;
	}


	public String getServerName() {
		return serverName;
	}


	public int getPortNumber() {
		return portNumber;
	}
	
}
