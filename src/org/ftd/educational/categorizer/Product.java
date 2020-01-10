package org.ftd.educational.categorizer;

/**
 *
 * @author Fabio Tavares Dippold
 *
 */
public class Product {

    private String description;
    private String category;

    public Product(String description, String category) {
        this.description = description;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| %-20s |", description));
        sb.append(String.format(" %-20s |", category));

        return sb.toString();
    }

}
