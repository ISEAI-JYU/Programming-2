public abstract class KitchenAppliance {
    /**
     * Does the appliance contain heating elements?
     */
    boolean heating;

    /**
     * All kitchen appliances must be cleanable.
     */
    public abstract void clean();
}