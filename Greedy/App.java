package com.example.hitting.greedy;

import java.util.*;

public class App {
    private List<Integer> universo;
    private List<Set<Integer>> subconjuntos;
    private List<Integer> hitting_set;

    public App(List<Integer> universo, List<Set<Integer>> subconjuntos) {
        this.universo = universo;
        this.subconjuntos = validarSubconjuntos(universo, subconjuntos);
        this.hitting_set = new ArrayList<>();
    }

    
    public List<Integer> getUniverso() { return universo; }
    public void setUniverso(List<Integer> universo) { 
        this.universo = universo; 
    }

    public List<Set<Integer>> getSubconjuntos() { return subconjuntos; }
    public void setSubconjuntos(List<Set<Integer>> subconjuntos) {
        this.subconjuntos = validarSubconjuntos(this.universo, subconjuntos);
    }

    public List<Integer> getHittingSet() {
        return hitting_set; 
    }
    
    public void setHittingSet(List<Integer> hitting_set) {
        this.hitting_set = hitting_set; 
    }

    
    private List<Set<Integer>> validarSubconjuntos(List<Integer> universo, List<Set<Integer>> subconjuntos) {
        Set<Integer> universoSet = new HashSet<>(universo);
        List<Set<Integer>> subconjuntosValidados = new ArrayList<>();

        for (Set<Integer> sub : subconjuntos) {
            Set<Integer> subValidado = new HashSet<>();
            for (int elem : sub) {
                if (universoSet.contains(elem)) {
                    subValidado.add(elem);
                } else {
                    System.out.println("Elemento " + elem + " no está en el universo y será ignorado.");
                }
            }
            if (!subValidado.isEmpty()) {
                subconjuntosValidados.add(subValidado);
            }
        }
        return subconjuntosValidados;
    }

    
    public void resolver() {
        hitting_set = new ArrayList<>();
        Set<Set<Integer>> subconjuntosPendientes = new HashSet<>(subconjuntos);

        while (!subconjuntosPendientes.isEmpty()) {
            int mejorElemento = -1;
            int maxCobertura = -1;

            for (int elem : universo) {
                int cobertura = 0;
                for (Set<Integer> sub : subconjuntosPendientes) {
                    if (sub.contains(elem)) {
                        cobertura++;
                    }
                }
                if (cobertura > maxCobertura) {
                    maxCobertura = cobertura;
                    mejorElemento = elem;
                }
            }

            if (mejorElemento == -1) {
                System.out.println("No se puede cubrir todos los subconjuntos con el universo dado.");
                return;
            }

            hitting_set.add(mejorElemento);

            
            Iterator<Set<Integer>> it = subconjuntosPendientes.iterator();
            while (it.hasNext()) {
                Set<Integer> sub = it.next();
                if (sub.contains(mejorElemento)) {
                    it.remove();
                }
            }
        }
    }

    
    public boolean resolverConK(int k) {
        resolver();  
        return hitting_set.size() <= k;
    }

    
    public static void main(String[] args) {
        // Caso 1
        List<Integer> universo1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Set<Integer>> subconjuntos1 = new ArrayList<>();
        subconjuntos1.add(new HashSet<>(Arrays.asList(1, 2)));
        subconjuntos1.add(new HashSet<>(Arrays.asList(2, 3)));
        subconjuntos1.add(new HashSet<>(Arrays.asList(3, 4)));
        subconjuntos1.add(new HashSet<>(Arrays.asList(4, 5)));
        ejecutarCaso(universo1, subconjuntos1, "Caso 1", 2);

        // Caso 2
        List<Integer> universo2 = Arrays.asList(1,2,3,4,5,6,7,8);
        List<Set<Integer>> subconjuntos2 = new ArrayList<>();
        subconjuntos2.add(new HashSet<>(Arrays.asList(1,2,3)));
        subconjuntos2.add(new HashSet<>(Arrays.asList(2,4,5)));
        subconjuntos2.add(new HashSet<>(Arrays.asList(3,6)));
        subconjuntos2.add(new HashSet<>(Arrays.asList(5,7)));
        subconjuntos2.add(new HashSet<>(Arrays.asList(6,8)));
        subconjuntos2.add(new HashSet<>(Arrays.asList(1,7)));
        subconjuntos2.add(new HashSet<>(Arrays.asList(2,8)));
        ejecutarCaso(universo2, subconjuntos2, "Caso 2", 3);

        // Caso 3
        List<Integer> universo3 = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
        List<Set<Integer>> subconjuntos3 = new ArrayList<>();
        subconjuntos3.add(new HashSet<>(Arrays.asList(1,2,3,4)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(2,5,6)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(3,7,8)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(4,9)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(5,10)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(6,11)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(7,9)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(8,10)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(1,11)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(2,7)));
        subconjuntos3.add(new HashSet<>(Arrays.asList(3,5)));
        ejecutarCaso(universo3, subconjuntos3, "Caso 3", 4);
    }

    
    private static void ejecutarCaso(List<Integer> universo, List<Set<Integer>> subconjuntos, String nombreCaso, int k) {
        App s = new App(universo, subconjuntos);

        long inicio = System.nanoTime();
        s.resolver();
        long fin = System.nanoTime();
        double milisegundos = (fin - inicio) / 1_000_000.0;

        System.out.println("=== " + nombreCaso + " ===");
        System.out.println("Universo: " + s.getUniverso());
        System.out.println("Subconjuntos validados: " + s.getSubconjuntos());
        System.out.println("Hitting set (greedy): " + s.getHittingSet());
        System.out.println("Tiempo de ejecución (greedy): " + milisegundos + " ms");

        
        inicio = System.nanoTime();
        boolean existe = s.resolverConK(k);
        fin = System.nanoTime();
        milisegundos = (fin - inicio) / 1_000_000.0;

        System.out.println("¿Existe hitting set de tamaño <= " + k + "? " + existe);
        if (existe) {
            System.out.println("Ejemplo de hitting set con tamaño <= " + k + ": " + s.getHittingSet());
        }
        System.out.println("Tiempo de ejecución (con k): " + milisegundos + " ms\n");
    }
}
