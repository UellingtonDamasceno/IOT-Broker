/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.frontend;

import facade.FacadeBackend;
import facade.FacadeFrontend;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Topic;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Uellington Conceição
 */
public class TopicVizualizeController implements Initializable, Observer {

    @FXML
    private BorderPane bordePane;
    @FXML
    private TextField txtRate;

    private LineChart chart;
    private ObservableList<XYChart.Series<Integer, Integer>> lineChartData;
    private ObservableList<XYChart.Data<Integer, Integer>> valuesSeries;
    private XYChart.Series<Integer, Integer> series;
    private NumberAxis xAxis;
    private NumberAxis yAxis;

    private int samples;

    private int i;
    private Topic topic;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FacadeBackend.getInstance().addSubscriberControllerObserver(this);

        this.samples = 21;

        this.valuesSeries = FXCollections.observableArrayList();
        this.lineChartData = FXCollections.observableArrayList();

        this.xAxis = new NumberAxis("Amostras", 0, this.samples, 1);
        this.yAxis = new NumberAxis("Medição", 0, 100, 5);
    }

    @FXML
    private void update(ActionEvent event) {
        String stringRate = this.txtRate.getText();
        this.samples = Integer.parseInt(stringRate);
        this.xAxis.setUpperBound(samples);
    }

    private void chartUpdate(int value) {
        Platform.runLater(() -> {
            series.getData().add(new XYChart.Data<>(i++, value));

            if (series.getData().size() > samples) {
                series.getData().remove(0);
                Iterator<XYChart.Data<Integer, Integer>> iterator = series.getData().iterator();
                XYChart.Data<Integer, Integer> current;
                while (iterator.hasNext()) {
                    current = iterator.next();
                    current.setXValue(current.getXValue() - 1);
                }
                i = samples;
            }
        });
    }

    @Override
    public void update(Observable o, Object o1) {
        JSONObject response = (JSONObject) o1;
        switch (response.getString("route")) {
            case "UPDATE/TOPIC": {
                if (this.topic.getName().equals(response.getString("topic_id"))) {
                    this.chartUpdate(response.getInt("value"));
                }
                break;
            }
            case "PUB/DISCONNECT": {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Publicador desconectado!");
                        alert.setContentText("Um publicador se deconectou.");
                        alert.setResizable(false);
                        alert.show();
                    }
                });
                break;
            }
        }

    }

    void setTopic(Topic topic) {
        this.topic = topic;
        this.series = new LineChart.Series<>(this.topic.getName(), valuesSeries);
        this.lineChartData.add(series);

        this.chart = new LineChart(xAxis, yAxis, lineChartData);

        this.bordePane.setCenter(this.chart);
    }

    @FXML
    private void offPublisher(ActionEvent event) {
        try {
            FacadeBackend.getInstance().offPublisher(this.topic);
        } catch (IOException ex) {
            Logger.getLogger(TopicVizualizeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
