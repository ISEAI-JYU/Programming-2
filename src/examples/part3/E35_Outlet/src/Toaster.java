public class Toaster extends KitchenAppliance implements MainsPoweredDevice {

    @Override
    public void connectPower() {
        IO.println( "Toaster: Heating elements "
            + "begin glowing red."
        );
    }

    @Override
    public void clean() {
        IO.println( "Toaster: Removing crumbs "
            + "and wiping gently "
            + "with a damp cloth."
        );
    }
}