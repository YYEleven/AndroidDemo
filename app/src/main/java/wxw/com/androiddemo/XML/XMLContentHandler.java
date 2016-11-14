package wxw.com.androiddemo.XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import wxw.com.androiddemo.domain.Person;

/**
 * Created by Eleven on 16/3/7.
 * wxw
 */
public class XMLContentHandler extends DefaultHandler {

    private List<Person> persons;
    private Person currentPerson;
    private String tagName;

    public List<Person> getPersons(){
        return persons;
    }

    @Override
    public void startDocument() throws SAXException {
//        super.startDocument();
        persons = new ArrayList<Person>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//        super.startElement(uri, localName, qName, attributes);
        if(localName.equals("person")){
            currentPerson = new Person();
            currentPerson.setId(Integer.parseInt(attributes.getValue("id")));
        }
        this.tagName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//        super.characters(ch, start, length);
        if(tagName!=null){
            String data = new String(ch,start,length);
            if(tagName.equals("name")){
                this.currentPerson.setName(data);
            }else if(tagName.equals("age")){
                this.currentPerson.setAge(Short.parseShort(data));
            }

        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        super.endElement(uri, localName, qName);
        if(localName.equals("person")){
            persons.add(currentPerson);
            currentPerson = null;
        }
        this.tagName = null;
    }
}
