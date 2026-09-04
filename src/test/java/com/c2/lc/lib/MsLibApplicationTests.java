package com.c2.lc.lib;

import com.c2.lc.lib.bo.KeyValue;
import com.c2.lc.lib.utils.SystemHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class MsLibApplicationTests {

	@Test
	void json() {
		SystemHelper helper = new SystemHelper();
		KeyValue key = new KeyValue();
		key.setKey("KEY");
		final String json = helper.toJson(key);
		System.out.println(json);
		KeyValue test = helper.fromJson(json, KeyValue.class);
		System.out.println(test.getKey());

	}
}
