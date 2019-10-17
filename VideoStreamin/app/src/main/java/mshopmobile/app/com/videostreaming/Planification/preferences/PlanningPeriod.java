/* Copyright 2014 Sheldon Neilson www.neilson.co.za
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package mshopmobile.app.com.videostreaming.Planification.preferences;

public class PlanningPeriod {
	
	public enum Key{
		ALARM_NAME,
		ALARM_TIME,
		ALARM_REPEAT,
		ALARM_CHECKER
	}
	
	public enum Type{		
		BOOLEAN,
		INTEGER,
		STRING,
		LIST,
		MULTIPLE_LIST,
		TIME,
		CHECKER
	}
	
	private Key key;
	private String title;
	private String summary;
	private Object value;
	private String[] options;
	private Type type;
	public String recordingTo;
	public boolean status;
	
	public PlanningPeriod(Key key, Object value, Type type,boolean status) {
		this(key,null,null,null,value,type,status);
	}

	public PlanningPeriod(Key key, String title, String summary, String[] options, Object value, Type type,boolean status) {
		setTitle(title);
		setSummary(summary);
		setOptions(options);
		setKey(key);
		setValue(value);
		//setRecordingTo((String)value);
		setType(type);
		setStatus(status);
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRecordingTo() {
		return recordingTo;
	}

	public void setRecordingTo(String recordingTo) {
		this.recordingTo = recordingTo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
