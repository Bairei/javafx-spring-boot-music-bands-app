package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.models.Genre;
import com.bairei.javafxapp.services.AlbumService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class GenreStatisticsController {

    private JavafxappApplication main;

    private ObservableList<String> genreNames = FXCollections.observableArrayList();

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    public GenreStatisticsController() {
    }


    @FXML
    private void initialize(){
        Genre[] genres = Genre.values();
        List<String> genresStringList = new ArrayList<>();
        for (Genre g: genres){
            genresStringList.add(g.name());
        }
        genreNames.setAll(genresStringList);

        if(xAxis.getCategories().size() < 1) {
            xAxis.setCategories(genreNames);
        }
    }

    public void setAlbumData(List<Album> albums){
        int[] genresCounter = new int[Genre.values().length];

        albumLoop:
        for (Album a: albums){
            for (int i = 0; i < genresCounter.length; i++){
                if (Genre.values()[i].equals(a.getGenre())){
                    genresCounter[i]++;
                    continue albumLoop;
                }
            }
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (int i = 0; i < genresCounter.length; i++){
            series.getData().add(new XYChart.Data<>(genreNames.get(i), genresCounter[i]));
        }

        barChart.getData().add(series);
    }


}
