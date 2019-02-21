package xmlTask;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

public class MainXml {
    public static void main(String[] args) {
        args = new String[]{"/Users/xpesh/IdeaProjects/NetCracker/src/xmlTask/file.xml", "/Users/xpesh/IdeaProjects/NetCracker/src/xmlTask/fileOUT.xml"};
        try {
            XmlTask xmlTask = new XmlTask(args[0], args[1]);
            xmlTask.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class XmlTask{
        private final Document document;
        private String inFile;
        private String outFile;

        XmlTask(String inFile, String outFile) throws ParserConfigurationException, IOException, SAXException {
            this.inFile = inFile;
            this.outFile = outFile;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setValidating(true);
            this.document = builder.parse(Paths.get(inFile).toFile());
        }

        void start() throws TransformerException {
            NodeList students = document.getElementsByTagName("student");
            double sum;
            boolean isRewrite= false;
            for(int i=0;i<students.getLength();i++){
                Element student = (Element) students.item(i);
                Element average = (Element) student.getElementsByTagName("average").item(0);
                sum = countAverage(student);
                if(average==null){
                    average = document.createElement("average");
                    student.appendChild(average);
                    isRewrite = true;
                    average.setTextContent(String.valueOf(sum));
                }else if (sum != parseAverage(average)) {
                    isRewrite = true;
                    average.setTextContent(String.valueOf(sum));
                }
            }
            if(isRewrite)
                saveXml();

        }

        private void saveXml() throws TransformerException {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMImplementation domImpl = document.getImplementation();
            DocumentType doctype = domImpl.createDocumentType("doctype", "","/Users/xpesh/IdeaProjects/NetCracker/src/xmlTask/group.dtd");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(outFile));
            transformer.transform(source, result);
        }

        private double countAverage(Element student){
            NodeList subjects = student.getElementsByTagName("subject");
            double sum=0;
            for (int i=0;i<subjects.getLength();i++){
                sum+=markSubject((Element) subjects.item(i));
            }
            return sum/subjects.getLength();
        }

        private double parseAverage(Element average){
            Locale.setDefault(new Locale("en", "USA"));
            return Double.valueOf(average.getTextContent());
        }

        private double markSubject(Element subject){
            return Double.valueOf(subject.getAttribute("mark"));
        }
    }
}
