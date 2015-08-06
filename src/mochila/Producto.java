
package mochila;


public class Producto {
    private Integer id;
    private Integer costo;
    private Integer beneficio;

    public Producto() {
    }

    public Producto(Integer id, Integer costo, Integer beneficio) {
        this.id = id;
        this.costo = costo;
        this.beneficio = beneficio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(Integer beneficio) {
        this.beneficio = beneficio;
    }

    @Override
    public String toString() {
        return "Prod{" + "id=" + id + ", costo=" + costo + ", beneficio=" + beneficio + "}\n";
    }
    
    
}
