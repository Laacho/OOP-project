package bg.tu_varna.sit.a4.f22621679.namespaces;

import java.util.HashMap;
import java.util.Map;

public class XMLNamespaces {
    /***
     *  namespace ->map with key the name of the namespace and value-url(link)
     */
    private Map<String,String> namespaces;


    public XMLNamespaces() {
        this.namespaces = new HashMap<>();
    }

    public Map<String, String> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Map<String, String> namespaces) {
        this.namespaces = namespaces;
    }

    /***
     *  adds a namespace in the map with the given name and its url
     * @param name the name of the namespace
     * @param url its url linked to its name
     */
    public void addNamespace(String name, String url) {
        this.namespaces.put(name, url);
    }
}
