package com.pimco.questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given input String
 * key1=value1;key2=value2;key3=value3;key1=value4;key2=value5;key1=value6
 * parse it and be able to later
 * - regenerate the same information string keeping the same ordering of
 * key=value
 * - given a key, return all its values
 * 
 * Extra questions
 * When parsing multiple identical Strings key and value,
 * What would be the impact on memory? More? Less?
 * 
 */

public class KeyValueParsing {

	class KeyValue {
		public String key;
		public String value;

		public KeyValue(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	private final List<KeyValue> keyValueList = new ArrayList<>();
	private final Map<String, List<String>> keyToValuesMap = new HashMap<>();

	public void parse(String input) {
		String[] kvTokens = input.split(";");
		for (String kv : kvTokens) {
			String[] token = kv.split("=");
			String k = token[0];
			String v = token[1];
			KeyValue keyValue = new KeyValue(k, v);
			keyValueList.add(keyValue);

			keyToValuesMap.putIfAbsent(k, new ArrayList<String>());
			keyToValuesMap.get(k).add(v);
		}
	}

	public String generateRecords() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyValueList.size(); i++) {
			KeyValue kv = keyValueList.get(i);
			sb.append(String.format("%s=%s", kv.key, kv.value));
			if (i < keyValueList.size() - 1) {
				sb.append(";");
			}
		}
		return sb.toString();

	}

	public Object getValues(String key) {

		return keyToValuesMap.getOrDefault(key, List.of());
	}

	public static void main(String[] args) {
		String input = "key1=value1;key2=value2;key3=value3;key1=value4;key2=value5;key1=value6";
		KeyValueParsing kvParsing = new KeyValueParsing();
		kvParsing.parse(input);

		String output = kvParsing.generateRecords();
		System.out.println(kvParsing.getValues("key1"));
		System.out.println(kvParsing.getValues("key2"));
		System.out.println(kvParsing.getValues("key3"));
		System.out.println(output);
	}

}
