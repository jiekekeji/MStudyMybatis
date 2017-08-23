package com.jk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	public void testGenerator()
			throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {

		String config_file_name = "generatorConfig.xml";
		ClassLoader classLoader = AppTest.class.getClassLoader();
		URL url = classLoader.getResource(config_file_name);

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(url.getPath());

		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("end");

	}

}
