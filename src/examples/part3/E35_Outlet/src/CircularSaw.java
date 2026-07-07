// CircularSaw is a Tool that operates on mains power.
public class CircularSaw extends Tool implements MainsPoweredDevice {

    @Override
    public void connectPower() {
        // Circular saw's reaction to electrical power.
        IO.println("CircularSaw: Motor begins spinning the blade at 4000 rpm.");

        // Also call the superclass
        // use() method.
        super.use(1);
    }

    /**
     * Service the circular saw.
     *
     * @return Whether servicing  succeeded.
     */
    @Override
    public boolean service() {
        IO.println( "Servicing circular saw..."
            + "Sharpening blade and adjusting rotation speed."
        );
        return true;
    }
}