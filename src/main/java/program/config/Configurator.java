package program.config;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class Configurator {
    private final String configFilePath = "config.xml";
    private File configFile = new File(configFilePath);

    private int storageSize;
    private int collectorsCount;
    private int dealersCount;
    private int providersCount;

    public int GetStorageSize() {
        return storageSize;
    }

    public int GetCollectorsCount() {
        return collectorsCount;
    }

    public int GetDealersCount() {
        return dealersCount;
    }

    public int GetProvidersCount() {
        return providersCount;
    }

    public void LoadConfigFromFile(File configFile) {
        this.configFile = configFile;
        getConfigurationFromFile();
        this.configFile = new File(this.configFilePath);
        setConfigurationToFile(storageSize, collectorsCount, dealersCount, providersCount);
    }

    public Configurator() {
        if (configFile.exists())
            getConfigurationFromFile();
        else
            setConfigurationToFile(20, 5, 5, 5);
    }

    private void getConfigurationFromFile() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(configFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList;
            Node node;

            nodeList = doc.getElementsByTagName("storage");
            node = nodeList.item(0);
            storageSize = Integer.parseInt(((Element) node).getAttribute("size"));

            nodeList = doc.getElementsByTagName("collector");
            node = nodeList.item(0);
            collectorsCount = Integer.parseInt(((Element) node).getAttribute("count"));

            nodeList = doc.getElementsByTagName("dealer");
            node = nodeList.item(0);
            dealersCount = Integer.parseInt(((Element) node).getAttribute("count"));

            nodeList = doc.getElementsByTagName("provider");
            node = nodeList.item(0);
            providersCount = Integer.parseInt(((Element) node).getAttribute("count"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConfigurationToFile(int storageSize, int collectorsCount, int dealersCount, int providersCount) {
        this.storageSize = storageSize;
        this.collectorsCount = collectorsCount;
        this.dealersCount = dealersCount;
        this.providersCount = providersCount;

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            Element factory = document.createElement("factory");
            document.appendChild(factory);

            Element storage = document.createElement("storage");
            storage.setAttribute("size", Integer.toString(storageSize));
            factory.appendChild(storage);

            Element collector = document.createElement("collector");
            collector.setAttribute("count", Integer.toString(collectorsCount));
            factory.appendChild(collector);

            Element provider = document.createElement("provider");
            provider.setAttribute("count", Integer.toString(providersCount));
            factory.appendChild(provider);

            Element dealer = document.createElement("dealer");
            dealer.setAttribute("count", Integer.toString(dealersCount));
            factory.appendChild(dealer);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(configFilePath));
            transformer.transform(domSource, streamResult);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
