package api;

import org.springframework.http.HttpHeaders;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Api {

    protected String baseURI = "localhost:5000";
    protected int port = -1;
    protected String basePath = "";
    protected HttpHeaders httpRequestHeaders= new HttpHeaders();
    protected Map<String, String> cookies = new LinkedHashMap<String, String>();
    protected Map<String, String> headers = new LinkedHashMap<String, String>();
    protected Map<String, String> params = new LinkedHashMap<String, String>();

    public Api() {
    }

    public Api setBaseURI(String baseURI) {
        this.baseURI = baseURI;
        return this;
    }

    public Api setPort(int port) {
        this.port = port;
        return this;
    }

    public Api setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }
    public Api addHeaders(HttpHeaders httpRequestHeaders) {
        this.httpRequestHeaders = httpRequestHeaders;
        return this;
    }

}
