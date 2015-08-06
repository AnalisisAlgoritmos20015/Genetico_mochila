
package mochila;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Solucion {
    private Integer id;
    private Integer[] productos;
    private double pond;

    public Solucion(List<Producto> productos) {
        this.productos = new Integer[productos.size()];
        Arrays.fill(this.productos, 0);
        pond = 0.0;
    }

    public Solucion(Integer[] productos){
        this.productos = productos.clone();
        pond = 0.0;
    }

    public Integer[] getProductos() {
        return productos;
    }
    
    public void FillRnd(List<Producto> productos, Random rnd, int tope) {
        int dado = rnd.nextInt(productos.size());
      
        for (int i=0; i <= dado; i++){
            if (rnd.nextInt(100) < 60)
                this.productos[i] = 1;
        }
        
        this.correctora( rnd,  tope,  productos);
    }
    
    public Integer getCosto (List<Producto> productos){
        Integer sum = 0;
        for (int i=0; i < this.productos.length; i++)
            sum += this.productos[i] * productos.get(i).getCosto();
        return sum;
    }
    
    public Integer getFitness (List<Producto> productos){
       Integer sum = 0;
        for (int i=0; i < this.productos.length; i++)
            sum += this.productos[i] * productos.get(i).getBeneficio();
        return sum;
    }

    
   public void mutar(Random rnd, int tope, List<Producto> productos){
       int dado = rnd.nextInt(this.productos.length);
       this.productos[dado] = (this.productos[dado] + 1) % 2; 
       
       this.correctora( rnd,  tope,  productos);
   }
    
  public static int sumaFitnes(List<Solucion> poblacion, List<Producto> productos){
     Iterator<Solucion> iter = poblacion.iterator();
     int suma = 0;
     while (iter.hasNext()){
         suma += iter.next().getFitness(productos);
     }
     return suma;
  } 

    public void setPond(double pond) {
        this.pond = pond;
    }
    public double getPond() {
        return this.pond ;
    }
   
    public static Solucion ruleta(List<Solucion> poblacion, Random rnd){
        double dado = rnd.nextDouble();
        double acu = 0.0;
        Iterator<Solucion> iter = poblacion.iterator();
     Solucion temp = null;
     while (iter.hasNext() && acu <= dado){
         temp = iter.next();
         acu += temp.getPond();
     }
     return temp;
    }
  
  public static void setPond(List<Solucion> poblacion, List<Producto> productos, int sumaF){
     Iterator<Solucion> iter = poblacion.iterator();
     
     while (iter.hasNext()){
         Solucion temp = iter.next();
         Integer fit = temp.getFitness(productos);
         if (fit == 0)
             temp.setPond(0);
         else
            temp.setPond(sumaF / temp.getFitness(productos));
     }
     
  }
   
  public Solucion vecina (Random rnd, int tope, List<Producto> productos){
      Solucion sTemp = new  Solucion(this.productos);
      sTemp.mutar(rnd, tope, productos);
      
      return sTemp;
  }
    
   @Override
   public Solucion clone(){
        Solucion sTemp = new  Solucion(this.productos);
         return sTemp;
   }
  
    public void correctora (Random rnd, int tope, List<Producto> productos){
        while (this.getCosto(productos) > tope){
            sacaProducto(rnd);
        }
    }
   
    private void sacaProducto(Random rnd){
       boolean sw = true;
       while (sw){
           int dado = rnd.nextInt(productos.length);
           if (productos[dado] == 1){
               productos[dado] = 0;
               sw = false;
           }
       }
       
    }

    @Override
    public String toString() {
        return "Solucion{pond=" + pond + "productos=" + Arrays.toString(productos) +  "}\n";
    }

   
    
    public List<Solucion> cruzamiento (Solucion papa, Random rnd){
        List<Solucion> salida = new ArrayList<Solucion>();
        
        int dado = rnd.nextInt(this.productos.length);
        
        Integer[] adn1=new Integer[this.productos.length];
        Integer[] adn2=new Integer[this.productos.length];
        
        for (int i=0; i < this.productos.length; i++){
            if (i < dado){
                adn1[i] = this.productos[i];
                adn2[i] = papa.getProductos()[i];
            }else{
                adn1[i] = papa.getProductos()[i];
                adn2[i] = this.productos[i];
            }
        }
       
        salida.add(new Solucion(adn1));
        salida.add(new Solucion(adn2));
        return salida;
    }
    
}
