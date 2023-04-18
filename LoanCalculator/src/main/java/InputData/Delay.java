package InputData;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Delay
{
    private int startingMonth;
    private int monthCount;
    private BigDecimal interest;

    Delay(int startingMonth, int monthCount, BigDecimal interest)
    {
        this.startingMonth = startingMonth;
        this.monthCount = monthCount;
        this.interest = interest.divide(BigDecimal.valueOf(1200), 100, RoundingMode.HALF_UP);
    }



    public int getStartingMonth() {
        return startingMonth;
    }

    public void setStartingMonth(int startingMonth) {
        this.startingMonth = startingMonth;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
