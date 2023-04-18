package InputData;

import com.example.loancalculator.TableEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator extends Delay
{

    BigDecimal amount;
    BigDecimal interest;
    int months;

    public Calculator(BigDecimal amount, BigDecimal interest, int months, int years, int delayStart, int delayMonths, BigDecimal delayInterest)
    {
        super(delayStart, delayMonths, delayInterest);
        this.amount = amount;
        this.interest = interest.divide(BigDecimal.valueOf(1200), 50, RoundingMode.HALF_UP);
        this.months = months + years * 12;
    }
    public ObservableList<TableEntry> annuityLoan()
    {
        ObservableList<TableEntry> tab = FXCollections.observableArrayList();

        BigDecimal monthlyTotal = calculateAnnuity();
        BigDecimal amount = this.amount;
        for(int i = 1; i <= (months + super.getMonthCount()); ++i)
        {
            BigDecimal interest = amount.multiply(this.interest);

            if(super.getStartingMonth() <= i && (super.getStartingMonth() + super.getMonthCount()) > i)
            {
                interest = amount.multiply(super.getInterest());
                interest = interest.setScale(2, RoundingMode.HALF_UP);
                tab.add(new TableEntry(i, amount, BigDecimal.valueOf(0.00), interest, interest));
            }
            else
            {
                BigDecimal credit = monthlyTotal.subtract(interest);
                interest = interest.setScale(2, RoundingMode.HALF_UP);
                credit = credit.setScale(2, RoundingMode.HALF_UP);
                if (amount.compareTo(credit) < 0) {
                    credit = amount;
                    monthlyTotal = credit.add(interest);
                }
                tab.add(new TableEntry(i, amount, credit, interest, monthlyTotal));
                amount = amount.subtract(credit);
            }
        }

        return tab;
    }
    public ObservableList<TableEntry> linearLoan()
    {
        ObservableList<TableEntry> tab = FXCollections.observableArrayList();
        BigDecimal amount = this.amount;

        BigDecimal monthlyCredit = amount.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
        for(int i = 1; i <= (months + super.getMonthCount()); ++i)
        {
            BigDecimal interest = amount;
            if(super.getStartingMonth() <= i && (super.getStartingMonth() + super.getMonthCount()) > i)
            {
                interest = interest.multiply(super.getInterest());
                interest = interest.setScale(2, RoundingMode.HALF_UP);
                tab.add(new TableEntry(i, amount, BigDecimal.valueOf(0.00), interest, interest));
            }
            else
            {
                if (amount.compareTo(monthlyCredit) < 0)
                {
                    monthlyCredit = amount;
                }
                interest = interest.multiply(this.interest);
                interest = interest.setScale(2, RoundingMode.HALF_UP);
                tab.add(new TableEntry(i, amount, monthlyCredit, interest, monthlyCredit.add(interest)));
                amount = amount.subtract(monthlyCredit);
            }
        }

        return tab;
    }
    private BigDecimal calculateAnnuity()
    {
        BigDecimal annuityCoefficient;
        BigDecimal annuity;

        BigDecimal counter = this.interest.multiply((this.interest.add(BigDecimal.valueOf(1))).pow(this.months));
        BigDecimal denominator = ((this.interest.add(BigDecimal.valueOf(1))).pow(this.months)).subtract(BigDecimal.valueOf(1));
        annuityCoefficient = counter.divide(denominator, 100, RoundingMode.HALF_UP);
        annuity = annuityCoefficient.multiply(this.amount);
        annuity = annuity.setScale(2, RoundingMode.HALF_UP);

        return annuity;
    }
}
