package org.example.tp10.bean;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import org.example.tp10.model.Produit;
import org.example.tp10.service.ProduitService;

@Named
@ViewScoped
public class ProduitBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProduitService service;

    private Produit produit = new Produit();
    private List<Produit> produits;
    private Produit selected;

    public List<Produit> getProduits() {
        if (produits == null) {
            produits = service.getProduits();
        }
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Produit getSelected() {
        return selected;
    }

    public void setSelected(Produit selected) {
        this.selected = selected;
    }

    public void delete(Long id) {
        service.deleteProduit(id);
        
        // Remove from local list to avoid full reload
        if (produits != null) {
            produits.removeIf(p -> p.getId().equals(id));
        }

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Supprimé", "Article supprimé !"));
    }

    public void save() {
        boolean isNew = (produit.getId() == null);
        service.createProduit(produit);
        
        // Refresh list to ensure we have the new ID or reflected changes
        produits = service.getProduits();
        
        String msg = isNew ? "Article ajouté !" : "Article modifié !";
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", msg));
            
        produit = new Produit(); // Reset form
    }

    public void prepareAdd() {
        this.produit = new Produit();
    }

    public void prepareEdit(Produit p) {
        this.produit = p;
    }
}

