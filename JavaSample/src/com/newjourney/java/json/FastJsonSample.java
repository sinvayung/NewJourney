package com.newjourney.java.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastJsonSample {

	@Test
	public void testObject2Json() {
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < 2; i++) {
			Person p = new Person(i, "name" + i);
			list.add(p);
		}
		String json = JSON.toJSONString(list);
		System.out.println(json);
	}
	
	@Test
	public void testJson2Object() {
		String json = "[{\"id\":0,\"name\":\"name0\"},{\"id\":1,\"name\":\"name1\"}]";
		List<Map> list = (List<Map>)JSON.parse(json);
		for(Map m : list) {
			System.out.println(m.get("id") + ", " + m.get("name"));
		}
		int a = 0;
	}

	public static class Person {
		private int id;
		private String name;

		public Person() {
			super();
		}

		public Person(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
