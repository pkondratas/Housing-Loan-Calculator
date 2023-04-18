package com.example.loancalculator;

import java.math.BigDecimal;

public class TableEntry
{
    private int month;
    private BigDecimal left;
    private BigDecimal credit;
    private BigDecimal interest;
    private BigDecimal total;

    public TableEntry(int month, BigDecimal left, BigDecimal credit, BigDecimal interest, BigDecimal total)
    {
        this.month = month;
        this.left = left;
        this.credit = credit;
        this.interest = interest;
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }
    public int getMonth()
    {
        return month;
    }
    public BigDecimal getLeft()
    {
        return left;
    }
    public BigDecimal getCredit()
    {
        return credit;
    }
    public BigDecimal getInterest()
    {
        return interest;
    }

}
