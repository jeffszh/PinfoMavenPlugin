package cn.jeff.ex;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

@Mojo(name = "p-info", defaultPhase = LifecyclePhase.COMPILE)
public class MyMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.outputDirectory}", required = true)
	private File outputDir;

	@Parameter(defaultValue = "nothing")
	private ArrayList<KvPair> items;

	public void execute() throws MojoExecutionException {
		File f = outputDir;

		if (!f.exists()) {
			f.mkdirs();
		}

		File pInfo = new File(f, "p-info.json");
		JSONObject jo = new JSONObject();
		for (KvPair item : items) {
			jo.put(item.key, item.value);
		}

		try (FileOutputStream outputStream = new FileOutputStream(pInfo)) {
			JSON.writeJSONString(outputStream, jo, SerializerFeature.PrettyFormat);
		} catch (IOException e) {
			throw new MojoExecutionException("寫文件出錯：" + pInfo, e);
		}
	}

	public static class KvPair {
		public String key;
		public String value;
	}

}
