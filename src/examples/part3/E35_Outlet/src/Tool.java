public abstract class Tool {
    /**
     * Number of operating hours.
     */
    private int operatingHours = 0;

    /**
     * Use the tool.
     *
     * @param hours Number of hours the tool is used.
     */
    public void use(int hours) {
        this.operatingHours = hours;
    }

    /**
     * Service the tool.
     *
     * @return Whether servicing succeeded.
     */
    public abstract boolean service();
}