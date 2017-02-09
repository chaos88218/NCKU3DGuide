package com.example.miles.ncku3dguide;
import android.content.Context;
import android.content.res.AssetManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Scanner;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilderFactory;
//import javax.print.DocFlavor.INPUT_STREAM;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SearchClassroom {
	
	
	public static String RoomInfo[]=new String[3]; //教室資訊
	public static ArrayList<String> realNameList; //真正包含搜尋字串的教室名稱
	
	private ArrayList<String> allNameList;	//所有東西的名稱，所有建築物加教室名稱加教室代號(名稱跟代號不重複)
	private InputStream inputFile;
	private DocumentBuilderFactory dbFactory;	
	private DocumentBuilder dBuilder;
	private Document doc ;
	
	SearchClassroom(Context context){
		Initialize(context);
	}
	
	public void Initialize(Context context){
		try {
			allNameList = new ArrayList<String>();
			realNameList = new ArrayList<String>();
			AssetManager am = context.getAssets();
			inputFile = am.open("NCKUBuildingDatabase.xml");

			dbFactory = DocumentBuilderFactory.newInstance();

			dBuilder = dbFactory.newDocumentBuilder();

			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			String expression = "/NCKUClassroomDatabase/Building";
			
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			
			//搜尋所有建築物名稱
			expression = "/NCKUClassroomDatabase/Building/BuildingName"; 
			nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			
			//將找到的所有建築物名稱存進allNameList
			if (nodeList.getLength() > 0) {								
				for (int i = 0;i < nodeList.getLength();i++) {
					allNameList.add(nodeList.item(i).getTextContent());					
				}				
			}
			//搜尋所有"教室"，不是名稱也不是代號
			expression = "/NCKUClassroomDatabase/Building/Floor/Classroom";
			nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			Element eElement ; 
			if (nodeList.getLength() > 0) {							
				for (int i = 0;i < nodeList.getLength();i++) {
					eElement= (Element)nodeList.item(i);
					allNameList.add(eElement.getElementsByTagName("ClassroomName").item(0).getTextContent());//存進所有教室名稱				
					if(!eElement.getElementsByTagName("ClassroomName").item(0).getTextContent() //教室名稱跟代號不一樣的話就再加進allNameList
					.equals(eElement.getElementsByTagName("ClassroomCode").item(0).getTextContent())){
						allNameList.add(eElement.getElementsByTagName("ClassroomCode").item(0).getTextContent());
					}					
				}				
			}
			
			
			
			
			RoomInfo[0]="找不到該地點"; //預設回傳格式
			RoomInfo[1]="";
			RoomInfo[2]="";
			
			
			
			
			

		} catch (XPathExpressionException e) {
			    e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public String[] getRoomInfo(String code){//所有代碼，包括系館名稱、教室中文名稱、教室代號都可以
		
		
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/NCKUClassroomDatabase/Building[@CHName='" + code + "']";//先尋找系館名稱
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			if (nodeList.getLength() != 0) {//如果找到就直接回傳							
				RoomInfo[0]=code;
				RoomInfo[1]="";
				RoomInfo[2]="";				
				return RoomInfo;
			}

			
			expression = "//Classroom[@code='" + code + "']";//先尋找教室代碼 
			nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
			

			if (nodeList.getLength() == 0) {//沒找到教室代碼就進來再找教室名稱，有找到就進不去				
				expression = "//Classroom[@Name='" + code + "']";
				nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
				if (nodeList.getLength() == 0) {
					RoomInfo[0]="找不到該地點";
					RoomInfo[1]="";
					RoomInfo[2]="";
					return RoomInfo;
				}
			}
			
			Element eElement = (Element)nodeList.item(0); 
			RoomInfo[2]=eElement.getElementsByTagName("ClassroomName").item(0).getTextContent();//先取得教室名稱
			
		    Node nNode = nodeList.item(0).getParentNode();//到上一層node，為了取得樓層
		    
		    eElement = (Element) nNode;
		    RoomInfo[1]=eElement.getElementsByTagName("FloorName").item(0).getTextContent(); //取得教室樓層
		    nNode=nNode.getParentNode();
		    eElement = (Element) nNode;
		    RoomInfo[0]=eElement.getElementsByTagName("BuildingName").item(0).getTextContent();//取得建築名稱

		    return RoomInfo;


		 }  catch (XPathExpressionException e) {
		    e.printStackTrace();
		    
		    return RoomInfo;
		 }			
	}

	public ArrayList<String> getNameList(String code){//所有代碼，包括系館、中文名、代碼都可以
		
		
		realNameList.clear();//清除舊的list
		
		if(code==""){ //沒輸入東西直接回傳
			return realNameList;
		}
		
		Map<Integer, Integer> table = new HashMap<Integer, Integer>(); //建一個Map，前Int為allNameList的索引值，後Int為欲搜尋字串的起始值

		int index=0;
			
		for (int i = 0;i < allNameList.size();i++) {

			index=allNameList.get(i).indexOf(code);//在每個名稱當中尋找欲搜尋的字串
			if(index==-1){ //找不到跳過
				continue;							
			}
			else{ //找到就存進table
				table.put(i, index);
			}
		}
		
		if(table.isEmpty()){ //table為空 ，沒有資訊
			realNameList.add("找不到資料");
			return realNameList;
		}
		//將Map轉成list格式
		List<Map.Entry<Integer, Integer>> list_Data =new ArrayList<Map.Entry<Integer, Integer>>(table.entrySet());

       //自訂Comparator中的compare，並sort
        Collections.sort(list_Data, new Comparator<Map.Entry<Integer, Integer>>(){
        	@Override
            public int compare(Map.Entry<Integer, Integer> entry1,
                               Map.Entry<Integer, Integer> entry2){
                return (entry1.getValue() - entry2.getValue());
            }
        });
        
        
        //將包含搜尋字串的名稱表回傳
        for(Map.Entry<Integer, Integer> entry:list_Data){
        	realNameList.add(allNameList.get(entry.getKey()));      	
        }
                       
	    return realNameList;
	}
}

