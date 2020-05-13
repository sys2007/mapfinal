/**
 * Copyright (c) 2015-2017, Henry Yang 杨勇 (gismail@foxmail.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapfinal.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;

	private final long timestamp;
	private String action;
	private Map<String, Object> data;
	private Callback callback;
	
	public Event(String action) {
		this.action = action;
		this.data = new HashMap<>();
		this.timestamp = System.currentTimeMillis();
	}

	public Event(String action, String name, Object data) {
		this.action = action;
		this.data = new HashMap<>();
		this.data.put(name, data);
		this.timestamp = System.currentTimeMillis();
	}
	
	public Event set(String name, Object data) {
		this.data.put(name, data);
		return this;
	}
	
	public void put(String name, Object data) {
		this.data.put(name, data);
	}
	
	public boolean hasData(String name) {
		return data.containsKey(name);
	}

	@SuppressWarnings("unchecked")
	public <M> M get(String name) {
		return (M) data.get(name);
	}
	
	public <M> M get(String name, M defaultValue) {
		Object res = data.get(name);
		return res==null ? defaultValue : (M) res;
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public String getAction() {
		return action;
	}

	public long getTimestamp() {
		return this.timestamp;
	}
	
	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public boolean isRender() {
		return "render".equals(action) ? true : false;
	}
	
	public boolean hasCallback() {
		return this.callback!=null ? true : false;
	}

	@Override
	public String toString() {
		return "Message [timestamp=" + timestamp + ", action=" + action + ", data=" + data + "]";
	}
}
