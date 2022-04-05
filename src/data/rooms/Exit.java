package data.rooms;

import data.tiled.TiledMap;

/**
 * Class Exit
 * Creates objects that inherit Room
 */

public class Exit extends Room{
    private static Room exit;

    /**
     * Constructor Exit
     * Creates an object Exit
     */

    public Exit() {
        super(TiledMap.getInstance(), "Exit", 100000, TiledMap.getInstance().getExitTarget(), TiledMap.getInstance().getStudentSpawn(), 42, 49, 1, 1);
    }

    /**
     * Static Method getInstance
     * @return instance of exit
     */

    public static Room getInstance(){
        /*
         * If no exit is found, create new exit
         */
        if(exit == null){
            exit = new Exit();
        }
        return exit;
    }
}
