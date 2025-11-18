package com.model;

/**
 * Driver class for the Exit Protocol application
 * Main entry point has been moved to UI.main()
 */
public class Driver {

    public Driver() {
    }

    /**
     * @deprecated Use UI.main() instead
     */
    @Deprecated
    public static void signInStart() {
        UI ui = new UI();
        ui.mainLoop();
    }
}