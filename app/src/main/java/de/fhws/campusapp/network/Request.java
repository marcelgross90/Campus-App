package de.fhws.campusapp.network;

class Request
{
    private String url;
    private String method;
    private String[] headers;
    private String data;

    public Request( String url, String method, String[] headers, String data ) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl( String url ) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod( String method ) {
        this.method = method;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders( String[] headers ) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData( String data ) {
        this.data = data;
    }
}
