package com.javarush.task.task33.task3309;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment)
            throws JAXBException, ParserConfigurationException, IOException, SAXException {

        StringWriter writer = new StringWriter();
        StringWriter writer1 = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(obj, writer1);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, writer);
//        System.out.println(writer.toString());


        List<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(writer.toString()));
        String readString;

        while ((readString = reader.readLine()) != null) {
            list.add(readString);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(list.get(0).replaceFirst("yes", "no"));

        String reg = "<" + tagName;
        String comm = "<!--" + comment + "-->\n";

        for (int i = 1; i < list.size(); i++) {
            String ss = list.get(i);

            int f = ss.indexOf(reg);

            if (f >= 0) {
                builder.append("\n").append(ss.substring(0, f)).append(comm);
            }

            int n = ss.indexOf(">");
            int k = ss.lastIndexOf("<");
            String sd = "";
            if (k > n) sd = ss.substring(n + 1, k);

            if (!sd.equals("")) {
                if (sd.matches((".*(&amp;|&lt;|&gt;|'|\").*"))) {
                    sd = sd.replaceAll("&amp;", "&");
                    sd = sd.replaceAll("&lt;", "<");
                    sd = sd.replaceAll("&gt;", ">");
                    sd = "<![CDATA[" + sd + "]]>";
                    ss = ss.substring(0, n + 1) + sd + ss.substring(k);
                }
            }

            if (!ss.contains(tagName))
                builder.append("\n");

            builder.append(ss);
        }

        return builder.toString();
    }

    public static void main(String[] args) throws JAXBException, IOException, SAXException, ParserConfigurationException {
        String result = Solution.toXmlWithComment(new AnExample(), "needCDATA", "it's a comment - <needCDATA>");
        System.out.println(result);
    }

    @XmlType(name = "anExample")
    @XmlRootElement
    public static class AnExample {
        public String[] needCDATA = new String[]{"need CDATA because of < and >", ""};
    }

}
