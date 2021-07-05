package eco;

public class CredentialsProvider {
    private String[] insideCredentialsTable = new String[5];

    public CredentialsProvider(String[] credentialsTable){
        this.insideCredentialsTable = credentialsTable;
    }

    public String getUrl(){
        return insideCredentialsTable[0];
    }

    public String getUsername(){
        return insideCredentialsTable[1];
    }

    public String getPassword(){
        return insideCredentialsTable[2];
    }

    public String getLanguage(){
        return insideCredentialsTable[3];
    }

    public String getStp(){
        return insideCredentialsTable[4];
    }

}
