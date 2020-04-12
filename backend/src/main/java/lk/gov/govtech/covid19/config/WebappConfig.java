package lk.gov.govtech.covid19.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@Configuration
public class WebappConfig {

//    @Bean
//    @ConditionalOnProperty(name = "webapp.war.file")
//    public TomcatServletWebServerFactory servletContainerFactory(@Value("${webapp.war.file}") String path,
//                                                                 @Value("${webapp.war.context}") String contextPath) {
//        return new TomcatServletWebServerFactory() {
//            @Override
//            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
//                new File(tomcat.getServer().getCatalinaBase(), "webapps").mkdirs();
//                try {
//                    tomcat.addWebapp(contextPath, path);
//                } catch (ServletException e) {
//                    log.error("Unable to find admin-webapp.war file. Starting backend without webapp");
//                }
//                return super.getTomcatWebServer(tomcat);
//            }
//        };
//    }
    @Bean
    @ConditionalOnProperty(name = "webapp.war.filename")
    public ServletWebServerFactory servletContainer(@Value("${webapp.war.filename}") String filename,
                                                                 @Value("${webapp.war.context}") String contextPath) {
        return new TomcatServletWebServerFactory() {
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                new File(tomcat.getServer().getCatalinaBase(), "webapps").mkdirs();

                Path source = Paths.get(filename);
                Path destDir = Paths.get(tomcat.getServer().getCatalinaBase()
                        + File.separator + "webapps" +File.separator + filename);
                try {
                    Files.copy(source,
                            destDir,
                            StandardCopyOption.REPLACE_EXISTING);
                    tomcat.addWebapp(contextPath, filename);
                } catch (Exception ex) {
                    log.error("Unable to find admin-webapp.war file. Starting backend without webapp");
                }
                return super.getTomcatWebServer(tomcat);
            }
        };
    }
}
