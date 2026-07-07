public class PowerOutlet {

    // Any mains-powered device
    // can be connected.
    // Outlot isn't intrested if it's toaster or TV
    public void connectDevice(MainsPoweredDevice device) {
        IO.println( "--- Power outlet supplies electricity ---");

        // The outlet calls the method defined by the contract.
        // Polymorphism occurs here:
        // each device reacts in its
        // own appropriate way.
        device.connectPower();
    }
}