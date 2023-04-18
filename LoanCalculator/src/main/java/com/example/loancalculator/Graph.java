package com.example.loancalculator;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;

public class Graph
{
    private final Stage graphWindow = new Stage();
    Graph(ObservableList<TableEntry> annuityList, ObservableList<TableEntry> linearList)
    {
        XYChart.Series annuityGraph = new XYChart.Series();
        XYChart.Series linearGraph = new XYChart.Series();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        linearGraph.setName("Linear");
        annuityGraph.setName("Annuity");
        xAxis.setLabel("Months");
        yAxis.setLabel("Total");
        for(TableEntry data : linearList)
        {
            linearGraph.getData().add(new Data(data.getMonth(), data.getTotal()));
        }
        for(TableEntry data : annuityList)
        {
            annuityGraph.getData().add(new Data(data.getMonth(), data.getTotal()));
        }
        LineChart graph = new LineChart(xAxis, yAxis);
        graph.getData().addAll(linearGraph, annuityGraph);
        Scene graphScene = new Scene(graph, 600, 600);
        graphWindow.setScene(graphScene);
    }
    public void showGraph()
    {
        graphWindow.show();
    }
}
