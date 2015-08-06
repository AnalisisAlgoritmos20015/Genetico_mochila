
package mochila;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Mochila {


    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>();
        Random rnd = new Random(111);
        int tope =  300000;
        
        for (int i = 0; i < 300; i++){
           productos.add(new Producto(i, rnd.nextInt(5000), rnd.nextInt(10000)));
        } 
        
        System.out.println(productos);
        
        Solucion best = null;
        
        for (int i=0; i< 1000; i++){
        
            Solucion S1 = new Solucion(productos);
           
            S1.FillRnd(productos, rnd, tope);
            if (best == null){
                best = S1;
                System.out.println(S1);
                System.out.println("Costo = " + S1.getCosto(productos));
                System.out.println("Beneficio = " + S1.getFitness(productos));
            }
          
            else if (best.getFitness(productos) < S1.getFitness(productos)){
                best = S1;
            }
                
            
        
        }
        System.out.println("=============GEN ALEATORIA=================");
        System.out.println(best);
        System.out.println("Costo = " + best.getCosto(productos));
        System.out.println("Beneficio = " + best.getFitness(productos));
        
        Solucion mga = best.clone();
        int cantMejoras = 0;
        
        while (cantMejoras < 10){
            
        
            Solucion mv = null;
            for (int i = 0; i< 1000; i++){
                Solucion v = mga.vecina(rnd, tope, productos);

                if (mv == null)
                    mv = v;
                else if (mv.getFitness(productos) < v.getFitness(productos)){
                    mv = v;
                }
            }
            
            if (mga.getFitness(productos) < mv.getFitness(productos)){
                mga = mv;
                cantMejoras = 0;
                System.out.println("\t\t--> " + mga.getCosto(productos));
                System.out.println("\t\t--> " + mga.getFitness(productos));
            } else {
                cantMejoras ++;
                System.out.println("\t\t<-- " + mga.getCosto(productos));
                System.out.println("\t\t<-- " + mga.getFitness(productos));
            }
     
        }
        
        System.out.println("=============G A=================");
        System.out.println(mga);
        System.out.println("Costo = " + mga.getCosto(productos));
        System.out.println("Beneficio = " + mga.getFitness(productos));
        
        
        
        List<Solucion> poblacion = new ArrayList<Solucion>();
        List<Solucion> newPoblacion = new ArrayList<Solucion>();
        poblacion.add(mga);
        poblacion.add(best);
        for (int i=2; i < 10; i++){
            Solucion S1 = new Solucion(productos);
           
            S1.FillRnd(productos, rnd, tope);
            poblacion.add(S1);
        }
            
        
        int probCruzamiento = 80;
        int probMutacion = 2;
        
        //Seleccion Natural
        int sumaF = Solucion.sumaFitnes(poblacion, productos);
        
        Solucion.setPond(poblacion, productos, sumaF);
        
        System.out.println("Poblacion = " + poblacion);
        
        Solucion Ind1 = Solucion.ruleta(poblacion, rnd);
        Solucion Ind2 = Solucion.ruleta(poblacion, rnd);
        
        //Cruzamiento
        if (rnd.nextInt(100) < probCruzamiento){
            List<Solucion> hijos = Ind1.cruzamiento(Ind2, rnd);
            //Mutacion
            if (rnd.nextInt(100) < probMutacion){
                hijos.set(0,hijos.get(0).vecina(rnd, tope, productos));
            } 
            if (rnd.nextInt(100) < probMutacion){
                hijos.set(0,hijos.get(0).vecina(rnd, tope, productos));
            } 
            newPoblacion.addAll(hijos);
        }
        
       
        
    }
}
