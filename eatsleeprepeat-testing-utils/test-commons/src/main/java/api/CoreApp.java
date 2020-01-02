package api;

public class CoreApp extends Api{
    public <T extends Api> T initialise(Class<T> clazz) {
        return getApiInstance(clazz);
    }

    private CoreApp addHeader(Api api) {
    api.addHeaders(this.httpRequestHeaders);
    return this;
    }

    private CoreApp addConfig(Api api) {
        api.setBasePath(this.basePath);
        api.setBaseURI(this.baseURI);
        api.setPort(this.port);
        return this;
    }

    private <T extends Api> T getApiInstance(Class<T> apiClass) {
        try {
            Api api = apiClass.newInstance();
            addConfig(api);
            addHeader(api);
            return apiClass.cast(api);
        } catch (Exception e) {
            throw new RuntimeException("Can't create api class");
        }
    }

}
