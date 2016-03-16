package wxw.com.androiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wxw.com.androiddemo.XML.XMLContentHandler;
import wxw.com.androiddemo.domain.Person;

/**
 * Created by Eleven on 16/3/1.
 */
public class SettingFragment extends Fragment {
    @Bind(R.id.sax)
    Button sax;
    @Bind(R.id.pull)
    Button pull;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_layout, null);
        ButterKnife.bind(this,view);

        return view;
    }
    @OnClick(R.id.sax)
    public void click(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            XMLContentHandler contentHandler = new XMLContentHandler();
            reader.setContentHandler(contentHandler);
            InputStream inputStream = getResources().getAssets().open("itcast.xml");
            reader.parse(new InputSource(inputStream));
            List<Person> list = contentHandler.getPersons();
            Log.i("TAG",list.size()+"");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void readFile(){

//        InputStream is =  getContext().getClass().getClassLoader().getResourceAsStream("assets/itcast.xml");
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(getResources().getAssets().open("itcast.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(is);

        String line;
        String result = null;
        try {
            while ((line=reader.readLine())!=null){
                result = result+line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @OnClick(R.id.pull)
    public void pullXML(){

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(getResources().getAssets().open("itcast.xml"),"UTF-8");
            int envenType = parser.getEventType();
            Person currentPerson = null;
            List<Person> list = null;
            while (envenType != XmlPullParser.END_DOCUMENT){

                switch (envenType){
                    case XmlPullParser.START_DOCUMENT://文件开始
                        list = new ArrayList<Person>();
                        break;
                    case XmlPullParser.START_TAG://开始元素事件
                        String name = parser.getName();
                        if("person".equals(name)){
                            currentPerson = new Person();
                            currentPerson.setId(Integer.parseInt(parser.getAttributeValue(null,"id")));
                        }else if(currentPerson!=null){
                            if(name.equals("name")){
                                currentPerson.setName(parser.nextText());
                            }else if(name.equals("age")){
                                currentPerson.setAge(new Short(parser.nextText()));
                            }
                        }

                        break;
                    case XmlPullParser.END_TAG://结束元素
                        if(parser.getName().equalsIgnoreCase("person")&&currentPerson!=null){
                            list.add(currentPerson);
                            currentPerson = null;
                        }
                        break;

                }
                envenType = parser.next();
            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
