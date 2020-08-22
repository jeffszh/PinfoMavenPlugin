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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Mojo(name = "p-info", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class MyMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.outputDirectory}", required = true)
	private File outputDir;

	@Parameter(defaultValue = "")
	private Properties pInfoProperties = new Properties();

	@Parameter(defaultValue = "${project.basedir}/src/main/resources/p-info")
	private File resourcesDir;

	@Parameter(defaultValue = "")
	private Properties properties = new Properties();

	public void execute() throws MojoExecutionException {
		File f = outputDir;

		if (!f.exists()) {
			f.mkdirs();
		}

		File pInfo = new File(f, "p-info.properties");

		try (FileOutputStream outputStream = new FileOutputStream(pInfo)) {
			pInfoProperties.store(outputStream, "p-info 屬性");
		} catch (IOException e) {
			throw new MojoExecutionException("寫文件出錯：" + pInfo, e);
		}

		if (properties != null && !properties.isEmpty()) {
			File resDir = resourcesDir;
			resDir.mkdirs();
			File resFile = new File(resDir, "p-info-resource.properties");
//			try (Writer resWriter = new FileWriter(resFile)) {
			try (Writer resWriter = new OutputStreamWriter(new FileOutputStream(resFile), StandardCharsets.UTF_8)) {
				properties.store(resWriter, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
