/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joel
 */
public class Config {
    
     protected ObjectMapper mapper; // for converting JSON to Java objects
    
    /**
     * Loads Configuration from a JSON file. To use this static member, 
 1. create your class that will hold the Configuration
 2. use this code example:
      MyConfigClass cfg;
      cfg=getConfigFromJSON(MyConfigClass.class,Configuration.json);        
     * @param user the class for the Configuration. The class members should match the fields in the JSON. 
 As well, the class should have getters and setters with Jackson annotations.
     * @param jsonFile the path to the JSON file containing the Configuration
     * @return an object of the class user, filled with Configuration information
     * @throws BadJSONRequestException
     * @throws IOException
     */
    public static <T> T getConfigFromJSON(Class<T> type, String jsonFile) throws IOException {
         
    	T myobj;
        
    	try {  
        	
    		ObjectMapper mapper;
            mapper  = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            
            //System.out.println("-> Loading "+ jsonFile + "....");
            
            myobj = (T) mapper.readValue(new File(jsonFile), type);
            
            return myobj;       
            
        }catch(JsonParseException e){
        	
            e.printStackTrace(System.out);
            return null;
            //throw new BadJSONRequestException("Parser failed. Couldn't load configuration from "+jsonFile);
        }catch (JsonMappingException e) {
        	
        	e.printStackTrace(System.out);
            //throw new BadJSONRequestException("Mapping failed");
            // throw an IO Exception because mapping error might be caused
            // by IO errors, like blocked connections. 
            throw new IOException();
        }catch (IOException ioe) {
            System.err.println(ioe);
            throw ioe;                
        }                      
    }
    
    
     /**
      * Convert String to Object
      * @param <T>
      * @param user
      * @param jsonString
      * @return Objects
      * @throws InternalErrorException 
      */
     public <T> T mapJSONStringToObject(Class<T> user,String jsonString)
     {

            T myobj=null;
           try {  
            myobj = (T) mapper.readValue(jsonString, user);

        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            String err = "An error happened in " + this.getClass().getName() + " in method " + thisMethodName();
                     
        } 
        return myobj;
     }
    
    
    /**
     * Convert Object to String
     * @param obj
     * @return 
     */
    public String mapObjectToJsonString(Object obj)
    {
        try {
            mapper = new ObjectMapper(); 
            
            StringWriter sw = new StringWriter(); // serialize
           
            MappingJsonFactory jsonFactory = new MappingJsonFactory();
            JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);
            mapper.writeValue(jsonGenerator, obj);
            sw.close();  
            
            System.out.println("\n->>Resulting string: "+sw.getBuffer().toString()+"\n");
            
            return sw.getBuffer().toString();
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            String err = "An error happened in " + this.getClass().getName() + " in method " + thisMethodName();
	    return "";
        } 
    
    } 
    
    
    /**
     * @return the name of the method calling this method
     */
    private String thisMethodName(){
        return Thread.currentThread().getStackTrace()[2].getMethodName() + "()";
    }
    
    
}
