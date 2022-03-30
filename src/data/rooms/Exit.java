package data.rooms;

import data.tiled.TiledMap;

public class Exit extends Room{
    private static Room exit;

    public Exit() {
        super(TiledMap.getInstance(), "Exit", 100000, TiledMap.getInstance().getExitTarget(), TiledMap.getInstance().getStudentSpawn(), 42, 49, 1, 1);
    }

    public static Room getInstance(){
        if(exit == null){
            exit = new Exit();
        }
        return exit;
    }
}
