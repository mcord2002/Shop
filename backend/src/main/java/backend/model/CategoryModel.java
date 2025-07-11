package backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CategoryModel {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public CategoryModel(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
