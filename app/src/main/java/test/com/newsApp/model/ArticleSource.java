package test.com.newsApp.model;

import java.io.Serializable;

/**
 * Created by Doha on 14/06/19.
 */
public class ArticleSource implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
