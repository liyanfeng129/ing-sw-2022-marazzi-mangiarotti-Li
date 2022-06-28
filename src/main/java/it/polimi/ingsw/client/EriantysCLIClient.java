package it.polimi.ingsw.client;


public class EriantysCLIClient {

    public boolean needUpdate = false;
    private static String serverAddress = "localhost";
    private static String userName = "";
    public static void main(String[] args)  {
        // TODO Auto-generated method stub
        try
        {
            //connectTOServer();
            //loggingMenu();
            //lobbyMenu();
            new EriantysCLIClientThread().start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static void onClientClose() throws Exception{
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
