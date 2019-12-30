package api.util;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;


import javax.naming.ConfigurationException;

public class UriCreator {

    private String propertiesFileName;

    public UriCreator(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    public String createUri() {
        return createUri(null);
    }

    private String createUri(String relativePath) {
        String apiUrl = "";
        try {
            CompositeConfiguration configuration = new CompositeConfiguration();
            configuration.addConfiguration(new SystemConfiguration());
            configuration.addConfiguration(new PropertiesConfiguration(propertiesFileName));

            String scheme = configuration.getString(CommonConstants.APP_SERVER_PROTOCOL_PROPERTY_NAME);
            String host = configuration.getString(CommonConstants.APP_SERVER_HOST_PROPERTY_NAME);
            URI uri = new URIBuilder().setScheme(scheme).setHost(host).build();
            apiUrl = uri.toString();
        } catch (Exception e) {
            System.out.println("Unhandled exception!");
        }
        apiUrl = relativePath != null ? apiUrl + relativePath : apiUrl;
        return apiUrl;
    }
}
