package test.com.newsApp.utils;

public class AppDataHolder {

    private static AppDataHolder instance;

    public static void setInstance(AppDataHolder instance) {
        AppDataHolder.instance = instance;
    }

    public Boolean getConnectedToInternet() {
        return isConnectedToInternet;
    }

    public void setConnectedToInternet(Boolean connectedToInternet) {
        isConnectedToInternet = connectedToInternet;
    }

    private Boolean isConnectedToInternet;

    private AppDataHolder(){

    }
    public static AppDataHolder getInstance(){
        if(instance == null ){
            instance = new AppDataHolder();
        }
        return instance;
    }

}
