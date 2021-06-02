package dev.socketmods.socketmodels.api.date;

public class Holidays {

    //TODO: Load definitions from remote, Some sort of refresh during runtime?

    private static final boolean isAprilFools
        = Dates.between(Dates.of(Month.MARCH, 31), Dates.of(Month.APRIL, 2));

    /**
     * @return true if the date is between to March 31 - April 2
     */
    public static boolean isAprilFools() {
        return isAprilFools;
    }

    //------------------------------------------------------------------------------------------------------------------

    private static final boolean isChristmas
        = Dates.between(Dates.of(Month.DECEMBER, 23), Dates.of(Month.JANUARY, 2));

    /**
     * @return true if the date is between to December 23 - January 2
     */
    public static boolean isChristmas() {
        return isChristmas;
    }

    //------------------------------------------------------------------------------------------------------------------

}
