package org.ftd.educational.categorizer;

/**
 *
 * @author Fabio Tavares Dippold
 *
 */
public class Term {

    private String term;
    private String objectOfKnowledge;

    public Term(String term, String objectOfKnowledge) {
        this.term = term;
        this.objectOfKnowledge = objectOfKnowledge;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getObjectOfKnowledge() {
        return objectOfKnowledge;
    }

    public void setObjectOfKnowledge(String objectOfKnowledge) {
        this.objectOfKnowledge = objectOfKnowledge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| %-20s |", term));
        sb.append(String.format(" %-20s |", objectOfKnowledge));
        
        return sb.toString();
    }

}
