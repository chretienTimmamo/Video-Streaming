/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.Exception.InternalErrorException;

/**
 * @author WILLY KENGNE
 */
public class MapJson {

	protected ObjectMapper mapper; // for converting JSON to Java objects

	/**
	 * Convert String to Object
	 * 
	 * @param <T>
	 * @param user
	 * @param jsonString
	 * @return Objects
	 * @throws InternalErrorException
	 */
	public <T> T mapJSONStringToObject(Class<T> user, String jsonString)
			throws InternalErrorException {

		T myobj = null;
		try {
			myobj = (T) mapper.readValue(jsonString, user);

		} catch (IOException ex) {
			Logger.getLogger(MapJson.class.getName()).log(Level.SEVERE, null,
					ex);
			String err = "An error happened in " + this.getClass().getName()
					+ " in method " + thisMethodName();
			throw new InternalErrorException(err);
		}
		return myobj;
	}

	/**
	 * Convert Object to String
	 * 
	 * @param obj
	 * @return
	 */
	public String mapObjectToJsonString(Object obj) {
		try {
			mapper = new ObjectMapper();

			StringWriter sw = new StringWriter(); // serialize

			MappingJsonFactory jsonFactory = new MappingJsonFactory();
			JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(sw);
			mapper.writeValue(jsonGenerator, obj);
			sw.close();

			System.out.println("\n->>Resulting string: "
					+ sw.getBuffer().toString() + "\n");

			return sw.getBuffer().toString();
		} catch (IOException ex) {
			Logger.getLogger(MapJson.class.getName()).log(Level.SEVERE, null,
					ex);
			String err = "An error happened in " + this.getClass().getName()
					+ " in method " + thisMethodName();
			return "{\"response\":\"msg\":\"Impossible to map Objet in JSON\",\"sessionID\":\"0\"}";
		}

	}

	/**
	 * @return the name of the method calling this method
	 */
	private String thisMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName() + "()";
	}


}
