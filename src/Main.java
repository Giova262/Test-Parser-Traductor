

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

public class Main {

	public static void main(String[] args) throws IOException {
		
		 File archivo = new File("textoTraducido.txt");	 
		 FileWriter fileWriter = null;
		 ArrayList<String> archivoTemp = new ArrayList<String>(); 
		 
		 boolean modoSacarTexto = false;
		 boolean modoModificarXML = true;
		 
		 String atributo = "string";
		 
         try {
        	 
			fileWriter = new FileWriter(archivo, true);
		
		//Parseo el XML	
		     try {
			        
		    	 File inputFile = new File("StrSheet_Achievement_1.xml"); 
		    	 DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	 dbFactory.setExpandEntityReferences(false);
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         dBuilder.setEntityResolver(null);
		         Document doc = dBuilder.parse(inputFile);
		         doc.normalize();
		         System.out.println("Nombre del archivo XML :" + doc.getDocumentElement().getNodeName());
		         NodeList nList = doc.getElementsByTagName("String");
		         
		         System.out.println("----------------------------");
		         
		         if(modoModificarXML) {
		        	 
			         FileReader fr = new FileReader(archivo);
			         BufferedReader br = new BufferedReader(fr);
			         String line;
			         
			         while((line = br.readLine()) != null){             
			             archivoTemp.add(line);
			         }
			         
			         System.out.println("Archivo Traducido Cargado.");
		         }
		      	         
		         System.out.println("Cantidad de nodos: " + nList.getLength());
		         System.out.println("Cantidad de lineas : " + archivoTemp.size());
		         
		         System.out.println("----------------------------");
		         
		         for (int temp = 0; temp < nList.getLength(); temp++) {
		        	 
		        	
		            Node nNode = nList.item(temp);
		            
		            //Para Modificar            
		            if(modoModificarXML){
		            	
			            NamedNodeMap attribute = nNode.getAttributes();
			            Node nodeAttr = attribute.getNamedItem(atributo);
			                         	       
			            if(nodeAttr != null) {
			            //	System.out.println(archivoTemp.get(temp));
			            	  nodeAttr.setTextContent( archivoTemp.get(temp)  );
			            }
		            	
		            }

		            
		            					//Para Escribir en el Txt           
		            if(modoSacarTexto) {
		            	
		            	 if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			            		
				               Element eElement = (Element) nNode;	
				               
				              // System.out.println( eElement.getAttribute(atributo).replace("\n", "").replace("\r", "") );
				              // System.out.println( temp );
				               
				              fileWriter.write(eElement.getAttribute(atributo).replace("\n", "").replace("\r", "")    );
				               //fileWriter.write("aaaaaaaaaaaaaaaaaa");
				               fileWriter.write("\n");
				               
				   			   
				            }
		            	
		            }
		            
           
		         }
		         
		         // Para Modificar
		         if(modoModificarXML){
		        	 
		        	 TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    		 
			         Transformer transformer = transformerFactory.newTransformer();
			         DOMSource domSource = new DOMSource(doc);
			 
			         StreamResult streamResult = new StreamResult(new File("StrSheet_Achievement_1.xml"));
			         transformer.transform(domSource, streamResult);
		        	 
		         }
		           
		            
		         
		      } catch (Exception e) {	e.printStackTrace();   }
					
		     fileWriter.close();
		     		
		} catch (IOException e2) {	e2.printStackTrace();	}
   
      if(modoSacarTexto)  System.out.println("Texto extraido con Exito.");	
      if(modoModificarXML)  System.out.println("Traduccion Completada.");	
	}

}
