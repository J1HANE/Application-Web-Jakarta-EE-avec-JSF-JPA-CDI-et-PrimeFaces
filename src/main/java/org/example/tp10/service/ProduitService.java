package org.example.tp10.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

import org.example.tp10.dao.ProduitDAO;
import org.example.tp10.model.Produit;

@ApplicationScoped
public class ProduitService {

    @Inject
    private ProduitDAO produitDAO;

    public List<Produit> getProduits() {
        return produitDAO.findAll();
    }

    public void createProduit(Produit produit) {
        produitDAO.save(produit);
    }

    public void deleteProduit(Long id) {
        produitDAO.delete(id);
    }
}
