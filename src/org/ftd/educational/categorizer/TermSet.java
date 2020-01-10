package org.ftd.educational.categorizer;

import java.text.DecimalFormat;

/**
 *
 * @author Fabio Tavares Dippold
 *
 */
public class TermSet {

    private String term;
    private String objectOfKnowledge;
    private int occurrence = 0;
    private double specificity = 0;
    private double weight = 0.0;

    public TermSet(String term, String objectOfKnowledge) {
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

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public double getSpecificity() {
        return specificity;
    }

    public void setSpecificity(double specificity) {
        this.specificity = specificity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.######");
        StringBuilder sb = new StringBuilder();
        sb.append("| O=");
        sb.append(occurrence);
        sb.append(" | S=");
        sb.append(specificity);
        sb.append(" | W=");
        sb.append(df.format(weight));
        sb.append(String.format(" | %-20s |", term));
        sb.append(String.format(" %-20s |", objectOfKnowledge));

        return sb.toString();
    }

}
