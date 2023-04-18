package com.example.loancalculator;

import InputData.Calculator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private BorderPane borderpane;
    @FXML
    private TextField amount;

    @FXML
    private RadioButton annuity;

    @FXML
    private CheckBox delay;

    @FXML
    private TextField delayMonths;

    @FXML
    private TextField delayInterest;

    @FXML
    private TextField delayStart;

    @FXML
    private TextField interestRate;

    @FXML
    private RadioButton linear;

    @FXML
    private TextField showFrom;

    @FXML
    private TextField showTo;

    @FXML
    private TableView<TableEntry> tableView;
    @FXML
    private TableColumn<TableEntry, BigDecimal> tableCredit;

    @FXML
    private TableColumn<TableEntry, BigDecimal> tableInterest;

    @FXML
    private TableColumn<TableEntry, BigDecimal> tableLeft;

    @FXML
    private TableColumn<TableEntry, Integer>tableMonth;

    @FXML
    private Label inputAlert;

    @FXML
    private TableColumn<TableEntry, BigDecimal> tableTotal;

    @FXML
    private TextField termMonths;

    @FXML
    private TextField termYears;

    @FXML
    private Button reset;

    @FXML
    private Button filter;

    @FXML
    private Button graph;

    @FXML
    private Button save;

    @FXML
    private Label saveError;

    ObservableList<TableEntry> list;
    ObservableList<TableEntry> linearList;
    ObservableList<TableEntry> annuityList;

    @FXML
    public void calculate()
    {
        //necessary data and validation
        try
        {
            list = FXCollections.observableArrayList();
            BigDecimal amount = new BigDecimal(this.amount.getText());
            BigDecimal interest = new BigDecimal(interestRate.getText());
            int months = Integer.parseInt(termMonths.getText());
            int years = Integer.parseInt(this.termYears.getText());
            int delayStart = 0;
            int delayMonths = 0;
            BigDecimal delayInterest = new BigDecimal("0.0");

            if(months < 0 || years < 0 || amount.compareTo(BigDecimal.ZERO) < 0 || interest.compareTo(BigDecimal.ZERO) < 0)
            {
                throw new NumberFormatException();
            }
            if(months == 0 && years == 0)
            {
                throw new NumberFormatException();
            }

            if(delay.isSelected())
            {
                delayStart = Integer.parseInt(this.delayStart.getText());
                delayMonths = Integer.parseInt(this.delayMonths.getText());
                delayInterest = new BigDecimal(this.delayInterest.getText());
                if(delayStart <= 0 || delayMonths <= 0 || delayInterest.compareTo(BigDecimal.ZERO) < 0 || delayStart > (months + years * 12))
                {
                    throw new NumberFormatException();
                }
            }

            Calculator calculator = new Calculator(amount, interest, months, years, delayStart, delayMonths, delayInterest);

            linearList = calculator.linearLoan();
            annuityList = calculator.annuityLoan();
            if(linear.isSelected())
            {
                list = linearList;
            }
            else if(annuity.isSelected())
            {
                list = annuityList;
            }
            this.tableView.setItems(list);
            inputAlert.setVisible(false);
            graph.setDisable(false);
            filter.setDisable(false);
            reset.setDisable(false);
            save.setDisable(false);
        }
        catch(NumberFormatException e)
        {
            save.setDisable(true);
            graph.setDisable(true);
            filter.setDisable(true);
            reset.setDisable(true);
            inputAlert.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //borderpane.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 255), new CornerRadii(0), Insets.EMPTY)));
        this.tableMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        this.tableLeft.setCellValueFactory(new PropertyValueFactory<>("left"));
        this.tableCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        this.tableInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        this.tableTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    @FXML
    public void delayHandle()
    {
        this.delayStart.setDisable(!this.delayStart.isDisable());
        this.delayMonths.setDisable(!this.delayMonths.isDisable());
        this.delayInterest.setDisable(!this.delayInterest.isDisable());
    }

    @FXML
    public void filter()
    {
        try
        {
            if(list == null)
            {
                throw new NullPointerException();
            }
            ObservableList<TableEntry> filteredList = FXCollections.observableArrayList();
            int showFrom = Integer.parseInt(this.showFrom.getText());
            int showTo = Integer.parseInt(this.showTo.getText());

            if(showFrom <= 0 || showTo <= 0 || showFrom > showTo)
            {
                throw new NumberFormatException();
            }

            tableView.setItems(list);

            for(TableEntry data : tableView.getItems())
            {
                int currentMonth = data.getMonth();

                if(showFrom <= currentMonth && currentMonth <= showTo)
                {
                    filteredList.add(data);
                }
            }
            tableView.setItems(filteredList);
            inputAlert.setVisible(false);
        }
        catch(NumberFormatException e)
        {
            inputAlert.setVisible(true);
        }
    }
    @FXML
    public void reset()
    {
        tableView.setItems(list);
    }

    @FXML
    public void handleGraph()
    {
        Graph graph = new Graph(annuityList, linearList);
        graph.showGraph();
    }

    @FXML
    public void handleSave()
    {
        if(list !=  null)
        {
            FileChooser fileChooser = new FileChooser();
            Stage stage = (Stage)this.save.getScene().getWindow();
            File file = fileChooser.showSaveDialog(stage);

            fileChooser.setInitialFileName("LoanData.csv");
            if(file != null)
            {
                try
                {
                    FileOutputStream stream = new FileOutputStream(file);
                    PrintWriter writer = new PrintWriter(stream);
                    writer.println("Month,Left,Credit,Interest,Total");

                    for(TableEntry data : list)
                    {
                        writer.println(data.getMonth() + "," + data.getLeft() + "," + data.getCredit() + "," + data.getInterest() + "," + data.getTotal());
                    }

                    writer.flush();
                    stream.close();
                    saveError.setVisible(false);
                }
                catch(IOException ignored)
                {
                    saveError.setVisible(true);
                }
            }
        }
    }
}
