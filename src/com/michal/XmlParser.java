package com.michal;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlParser {
    private HashMap<String,Double>currenciesAndRates=new HashMap<>();
    private List<String> currenciesList=new ArrayList<>();
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public XmlParser() {

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("eurofxref-daily.xml");
            doc.getDocumentElement().normalize();
            NodeList CubeList = doc.getElementsByTagName("Cube");
            for (int i = 0; i < CubeList.getLength(); i++) {
                Node c = CubeList.item(i);
                if (c.getNodeType() == Node.ELEMENT_NODE) {
                    Element Cube = (Element) c;
                    NodeList currencyList = Cube.getChildNodes();
                    for (int j = 0; j < currencyList.getLength(); j++) {
                        Node n = currencyList.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element currency = (Element) n;
                            if (currency.getAttribute("currency").isEmpty()) {
                                continue;
                            } else {
                                currenciesList.add(currency.getAttribute("currency"));
                                currenciesAndRates.put(currency.getAttribute("currency"), Double.parseDouble(currency.getAttribute("rate")));

                            }
                        }
                    }

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

    public HashMap<String, Double> getCurrenciesAndRates() {
        return currenciesAndRates;
    }

    public List<String> getCurrenciesList() {
        return currenciesList;
    }
}
