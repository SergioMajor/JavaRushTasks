package com.javarush.task.task28.task2810;

import com.javarush.task.task28.task2810.model.HHStrategy;
import com.javarush.task.task28.task2810.model.Model;
import com.javarush.task.task28.task2810.model.MoikrugStrategy;
import com.javarush.task.task28.task2810.model.Provider;
import com.javarush.task.task28.task2810.view.HtmlView;


public class Aggregator {

    public static void main(String[] args) {
        HtmlView htmlView = new HtmlView();
        HHStrategy hhStrategy = new HHStrategy();
        MoikrugStrategy moikrugStrategy = new MoikrugStrategy();
        Provider providerHHStrategy = new Provider(hhStrategy);
        Provider providerMoikrugStrategy = new Provider(moikrugStrategy);
        Model model = new Model(htmlView, providerHHStrategy, providerMoikrugStrategy);
        Controller controller = new Controller(model);
        htmlView.setController(controller);
        htmlView.userCitySelectEmulationMethod();
    }
}