package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./src/" + this.getClass().getPackage().getName().replaceAll("\\.", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        try {
            String content = getUpdatedFileContent(vacancies);
            updateFile(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    private String getUpdatedFileContent(List<Vacancy> vacancyList) {
        try {
            Document doc = getDocument();
            Element template = doc.getElementsByClass("template").first();
            Element cloneTemp = template.clone();
            cloneTemp.removeClass("template");
            cloneTemp.removeAttr("style");
            doc.select("tr[class=vacancy]").remove();
            for (Vacancy vacancy : vacancyList) {
                Element vac = cloneTemp.clone();
                vac.getElementsByAttributeValue("class", "city").first().text(vacancy.getCity());
                vac.getElementsByAttributeValue("class", "companyName").first().text(vacancy.getCompanyName());
                vac.getElementsByAttributeValue("class", "salary").first().text(vacancy.getSalary());
                vac.getElementsByAttribute("href").first().attr("href", vacancy.getUrl()).text(vacancy.getTitle());
                template.before(vac.outerHtml());
            }
            return doc.html();
        } catch (Exception e){
            e.printStackTrace();
            return "Some exception occurred";
        }
    }

    private void updateFile(String data) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
        pw.write(data);
        pw.close();
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }
}
