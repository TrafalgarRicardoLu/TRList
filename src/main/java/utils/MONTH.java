package utils;

public enum MONTH {
    Jan("Jan", 1), Feb("Feb", 2), Mar("Mar", 3),
    Apr("Apr", 4), May("May", 5), Jun("Jun", 6),
    Jul("Jul", 7), Aug("Aug", 8), Sep("Sep", 9),
    Oct("Oct", 10), Nov("Nov", 11), Dec("Dec", 12);

    private String monthName;
    private int monthIndex;

    MONTH(String monthName, int monthIndex) {
        this.monthName = monthName;
        this.monthIndex = monthIndex;
    }


    public int getMonthIndex() {
        return monthIndex;
    }

    public String getMonthName(){
        return monthName;
    }

    public String getMonthName(int monthIndex) {
        for (MONTH month : MONTH.values()) {
            if (month.monthIndex == monthIndex) {
                return month.monthName;
            }
        }
        return null;
    }
}
