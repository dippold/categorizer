package org.ftd.educational.categorizer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fabio Tavares Dippold
 * @version 2018-07-18
 *
 */
public class Setup {

    public static void main(String[] args) {

        List<Product> products = construirBaseAprendizado();
        List<Term> terms = construirTermos(products);
        List<TermSet> termSets = construirGrupoDeTermos(terms);

        System.out.println(mostrarBaseAprendizado(products));
        System.out.println(mostrarTermosGerados(terms));
        System.out.println(mostrarGruposDeTermosGerados(termSets));
        System.out.println(categorizar("Casa Gastro",termSets));

    }

    private static String categorizar(String descriptionTarget, List<TermSet> termSets) {
        StringBuilder result = new StringBuilder();
        List<String> termsFinal = decomporDescricaoEmTermos(descriptionTarget);

        result.append("\nCATEGORIZANDO: ");
        result.append(descriptionTarget);
        result.append("\n\nDECOMPONDO EM TERMOS: ");

        for (String s : termsFinal) {
            result.append("\n- ").append(s);
        }

        Map<String, Double> categories = new HashMap<>();
        for (TermSet o : termSets) {
            for (String term : termsFinal) {
                if (o.getTerm().equals(term)) {
                    if (categories.containsKey(o.getObjectOfKnowledge())) { // A CATEGORIA JÁ EXISTE...
                        double weight = categories.get(o.getObjectOfKnowledge());
                        categories.remove(o.getObjectOfKnowledge());
                        weight += o.getWeight();
                        categories.put(o.getObjectOfKnowledge(), weight);
                    } else { // NÃO EXISTE A CATEGORIA...
                        categories.put(o.getObjectOfKnowledge(), o.getWeight());
                    }
                }
            }
        }

        
        result.append("\n\nPESOS POR CATEGORIA DOS TERMOS DE: ");
        result.append(descriptionTarget);
        
        double total = 0.0;
        for (String category : categories.keySet()) {
            total += categories.get(category);
        }        
        
        DecimalFormat df = new DecimalFormat("#.#########");
        
        for (String category : categories.keySet()) {
            double weight = categories.get(category);
            result.append(String.format("\n| %-20s | ", category));
            result.append(df.format(weight));
            result.append(" | ");
            double percentual = (weight/total)*100;
            result.append((df.format(percentual)));
            //result.append((weight/total)*100);
            
        }        
        
        return result.toString();
    }

    private static List<Product> construirBaseAprendizado() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("Casa Benjamin Gastro", "Informática"));
        //products.add(new Product("Amaciante de Roupa Confort Natural 500 ml", "Lavagem de roupas"));
        products.add(new Product("Adamo Gastro", "Alimentação"));
        products.add(new Product("Slice Gastro", "Alimentação"));
        products.add(new Product("Adamo Gastro", "Alimentação"));
        products.add(new Product("Adamo Casa", "Moradia"));

        return products;
    }

    private static List<String> decomporDescricaoEmTermos(String productDescription) {
        List<String> termos = new ArrayList<>();
        String[] sArray = productDescription.split(" ");

        // ADICIONA CADA PALAVRA INDIVIDUAL A LISTA DE TERMOS ...
        for (String s : sArray) {
            termos.add(s);
        }

        // ADICIONA A COMBINAÇÃO DE DUAS PALAVRAS COMO UM TERMO...
        for (int i = 0; i < sArray.length; i++) {
            if (i != (sArray.length - 1)) {
                for (int j = i + 1; j < sArray.length; j++) {
                    termos.add(sArray[i] + " " + sArray[j]);
                }
            }
        }

        return termos;

    }

    private static List<Term> construirTermos(List<Product> products) {
        List<Term> terms = new ArrayList<>();
        products.forEach((o) -> {
            String[] sArray = o.getDescription().split(" ");
            // ADICIONA CADA PALAVRA INDIVIDUAL A LISTA DE TERMOS ...
            for (String s : sArray) {
                terms.add(new Term(s, o.getCategory()));
            }
            // ADICIONA A COMBINAÇÃO DE DUAS PALAVRAS COMO UM TERMO...
            for (int i = 0; i < sArray.length; i++) {
                if (i != (sArray.length - 1)) {
                    for (int j = i + 1; j < sArray.length; j++) {
                        terms.add(new Term(sArray[i] + " " + sArray[j], o.getCategory()));
                    }
                }
            }
        });

        return terms;
    }

    private static List<TermSet> construirGrupoDeTermos(List<Term> terms) {
        List<TermSet> termSets = new ArrayList<>();

        terms.forEach((term) -> {
            // JÁ EXISTE O TERM-SET?
            TermSet termSet = encontrarTermSet(termSets, term);
            if (termSet == null) {
                termSet = new TermSet(term.getTerm(), term.getObjectOfKnowledge());
                termSet.setOccurrence(1);
                termSets.add(termSet);
            } else {
                termSet.setOccurrence(termSet.getOccurrence() + 1);
            }

        });

        // CALCULAR OS PESOS...
        calcularPesos(termSets);

//        for (TermSet termSet : termSets) {
//            termSet.setSpecificity(contarTermSet(termSets, termSet.getTerm()));
//            double peso = (1 + log(termSet.getOccurrence(), 2)) * log((3 / termSet.getSpecificity()), 2);
//            termSet.setWeight(peso);
//        }
        return termSets;
    }

    private static void calcularPesos(List<TermSet> termSets) {
        for (TermSet termSet : termSets) {
            termSet.setSpecificity(contarTermSet(termSets, termSet.getTerm()));
            double peso = (1 + log(termSet.getOccurrence(), 2)) * log((3 / termSet.getSpecificity()), 2);
            termSet.setWeight(peso);
        }
    }

    private static TermSet encontrarTermSet(List<TermSet> termSets, Term term) {
        TermSet termSet = null;
        for (TermSet o : termSets) {
            if ((o.getTerm().equals(term.getTerm())) && (o.getObjectOfKnowledge().equals(term.getObjectOfKnowledge()))) {
                termSet = o;
                break;
            }
        }

        return termSet;
    }

    private static int contarTermSet(List<TermSet> termSets, String termSetName) {
        int count = 0;
        for (TermSet o : termSets) {
            if (o.getTerm().equals(termSetName)) {
                count++;
            }
        }

        return count;
    }

    private static String mostrarBaseAprendizado(List<Product> products) {
        StringBuilder result = new StringBuilder("\nPRODUTOS: ");
        result.append(products.size());
        products.forEach((o) -> {
            result.append("\n");
            result.append(o);
        });

        return result.toString();
    }

    private static String mostrarTermosGerados(List<Term> terms) {
        StringBuilder result = new StringBuilder("\nTERMOS: ");
        result.append(terms.size());
        terms.forEach((o) -> {
            result.append("\n");
            result.append(o);
        });

        return result.toString();
    }

    private static String mostrarGruposDeTermosGerados(List<TermSet> termSets) {
        StringBuilder result = new StringBuilder("\nGRUPO DE TERMOS: ");
        result.append(termSets.size());
        termSets.forEach((o) -> {
            result.append("\n");
            result.append(o);
        });

        return result.toString();
    }

    public static double log(double valor, double base) {
        return Math.log(valor) / Math.log(base);
    }

}
