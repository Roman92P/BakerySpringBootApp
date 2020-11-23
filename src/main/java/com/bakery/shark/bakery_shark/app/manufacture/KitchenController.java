package com.bakery.shark.bakery_shark.app.manufacture;


import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.*;
import com.bakery.shark.bakery_shark.app.produced.JpaProducedService;
import com.bakery.shark.bakery_shark.app.product.JpaProductService;
import com.bakery.shark.bakery_shark.app.stock.JpaStockService;
import com.bakery.shark.bakery_shark.app.user.UserService;
import com.bakery.shark.bakery_shark.app.workIngredientQuantity.JpaWorkIngredientQuantityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/kitchen")
//@Scope("session")
public class KitchenController {
    private final Logger logger = LoggerFactory.getLogger(KitchenController.class);

    private final JpaManufactureItemService jpaManufactureItemService;
    private final JpaManufacturedService jpaManufacturedService;
    private final JpaProductService jpaProductService;
    private final JpaIngredientService jpaIngredientService;
    private final JpaProducedService jpaProducedService;
    private final JpaStockService jpaStockService;
    private final JpaWorkIngredientQuantityService jpaWorkIngredientQuantityService;
    private final UserService userService;

//    private final ManufacturedRepository manufacturedRepository;

    @RequestMapping("")
    public String getKitchenView(Model model, HttpServletRequest request, Principal principal) {
        Set<ManufactureItem> allByNullManufactureId = jpaManufactureItemService.getAllItemsWithNullManufatured();
        model.addAttribute("allManufacturedItems", allByNullManufactureId);
        // add work Ingredients Quantity to find out if we can manufacture selected product, if we have enough ingredient quantity
        if (allByNullManufactureId.size() == 0) {
            copyIngredientstoWorkIngredients();
        }
        if (allByNullManufactureId.size() != 0 && request.getSession().getAttribute("manufactured") == null) {
            allByNullManufactureId.stream()
                    .forEach(manufactureItem -> jpaManufactureItemService.deleteManufactureItem(manufactureItem));
            return "redirect:/kitchen";
        }
        //show only products that can be manufactured
        List<WorkIngredientQuantity> allWorkIngredients = jpaWorkIngredientQuantityService.getAllWorkIngredients();
        List<Product> allProducts = jpaProductService.getAllProducts();
        List<Product> productAbleToManufacture = new ArrayList<>();
        for ( Product p : allProducts ) {
            boolean checkIfAbleToAddProduct = true;
            Set<RecipeItem> recipeItemList = p.getRecipe().getRecipeItemList();
            for ( RecipeItem recipeItem : recipeItemList ) {
                String ingredientName = recipeItem.getIngredients().getName();
                Double ingredientQuantity = recipeItem.getIngredientQuantity();
                WorkIngredientQuantity workIngredientByIngredientName = jpaWorkIngredientQuantityService.getWorkIngredientByIngredientName(ingredientName);
                if (ingredientQuantity > workIngredientByIngredientName.getWorkIngredientQuantity()) {
                    checkIfAbleToAddProduct = false;
                }
            }
            if (checkIfAbleToAddProduct) {
                productAbleToManufacture.add(p);
            }
            if (jpaManufacturedService.getManufacturedNotFinalized() == null) {
                String name = principal.getName();
                User byUserName = userService.findByUserName(name);
                Manufactured manufactured = new Manufactured();
                manufactured.setFinalizedWorkOrder(false);
                manufactured.setUser(byUserName);
                jpaManufacturedService.addManufactured(manufactured);
            }
        }
        //show message if choosen quantity in calculator is to much
        HttpSession session = request.getSession();
        if (session.getAttribute("manufactureProductQuantityErrorIngredients") != null) {

            Object manufactureProductQuantityErrorIngredients = session.getAttribute("manufactureProductQuantityErrorIngredients");
            String productNameQuantityError = (String) session.getAttribute("productNameQuantityError");
            Integer productQuantityError = (Integer) session.getAttribute("productQuantityError");

            model.addAttribute("alertIngredients", manufactureProductQuantityErrorIngredients);
            model.addAttribute("alertQuantity", productQuantityError);
            model.addAttribute("alertProductName", productNameQuantityError);

            session.removeAttribute("manufactureProductQuantityErrorIngredients");
            session.removeAttribute("productNameQuantityError");
            session.removeAttribute("productQuantityError");

        }
        model.addAttribute("products", productAbleToManufacture);
        logger.debug("Number of products that can be manufactured: " + productAbleToManufacture.size());
        return "kitchenView";
    }

    private void copyIngredientstoWorkIngredients() {
        List<Ingredient> allIngredients = jpaIngredientService.getAllIngredients();
        List<WorkIngredientQuantity> workIngredientQuantities = new ArrayList<>();
        for ( Ingredient i : allIngredients ) {
            WorkIngredientQuantity workIngredientQuantity = new WorkIngredientQuantity();
            workIngredientQuantity.setId(i.getId());
            workIngredientQuantity.setWorkIngredientName(i.getName());
            if (i.getLitr() != 0) {
                workIngredientQuantity.setWorkIngredientQuantity(i.getLitr());
            }
            if (i.getWeight() != 0) {
                workIngredientQuantity.setWorkIngredientQuantity(i.getWeight());
            }
            if (i.getQuantity() != 0) {
                workIngredientQuantity.setWorkIngredientQuantity(i.getQuantity());
            }
            workIngredientQuantities.add(workIngredientQuantity);
        }
        workIngredientQuantities.forEach(jpaWorkIngredientQuantityService::addWorkIngredient);
    }

    @RequestMapping("/createManufactured/{id}")
    public String createManufacturedProduct(@PathVariable long id, Model model, HttpServletRequest request) {
        logger.error("Product id witch was choosen in kitchen view: " + id);
        ManufactureItem manufactureItem = new ManufactureItem();
        manufactureItem.setProduct(jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("productId", id);
        model.addAttribute("manufactureItem", manufactureItem);

        if (request.getSession().getAttribute("manufactured") == null) {
            request.getSession().setAttribute("manufactured", jpaManufacturedService.getManufacturedNotFinalized().getId());
        }

        return "kitchenAddProductQuantityView";
    }

    @PostMapping("/addProducedItemProduct")
    public String addProducedItem(@Valid ManufactureItem manufactureItem, BindingResult result, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        // check if manufacture item has errors
        if (result.hasErrors()) {
            return "kitchenAddProductQuantityView";
        }
        // get product of manufacture item and quantity of product
        long id = manufactureItem.getProduct().getId();
        Product product = jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new);
        int productQuantity = manufactureItem.getQuantity();
        double doubleProductQuantity = productQuantity + 0.0;

        Set<RecipeItem> recipeItemList = product.getRecipe().getRecipeItemList();

        List<String> alerts = new ArrayList<>();
        for ( RecipeItem recipeItem : recipeItemList ) {
            String ingredientName = recipeItem.getIngredients().getName();
            Double ingredientQuantity = recipeItem.getIngredientQuantity();
            double manufactureItemIngQuantityToCheck = doubleProductQuantity * ingredientQuantity;
            WorkIngredientQuantity workIngredientByIngredientName =
                    jpaWorkIngredientQuantityService.getWorkIngredientByIngredientName(ingredientName);
            if (manufactureItemIngQuantityToCheck > workIngredientByIngredientName.getWorkIngredientQuantity()) {
                alerts.add(ingredientName);
            }
        }
        if (alerts.size() > 0) {

            if (session.getAttribute("manufactureProductQuantityErrorIngredients") == null) {
                session.setAttribute("manufactureProductQuantityErrorIngredients", alerts);
                session.setAttribute("productNameQuantityError", product.getName());
                session.setAttribute("productQuantityError", productQuantity);
            }
            return "redirect:/kitchen";
        }

        if (jpaManufactureItemService.findByProduct_NameAndManufacturedNull(product.getName()) != null) {
            ManufactureItem manufactureItemToUpdate =
                    jpaManufactureItemService.findByProduct_NameAndManufacturedNull(product.getName());
            manufactureItemToUpdate.setQuantity(manufactureItemToUpdate.getQuantity() + manufactureItem.getQuantity());
            jpaManufactureItemService.updateManufactureItem(manufactureItemToUpdate);
            jpaWorkIngredientQuantityService.updateWorkQuantity(manufactureItem.getProduct().getId(), manufactureItem.getQuantity());
            return "redirect:/kitchen";
        } else
            jpaManufactureItemService.addManufactureItem(manufactureItem);
        jpaWorkIngredientQuantityService.updateWorkQuantity(manufactureItem.getProduct().getId(), manufactureItem.getQuantity());
        return "redirect:/kitchen";
    }

    @RequestMapping("/deleteWorkOrder")
    public String deleteWorkOrder(HttpServletRequest request) {
        Set<ManufactureItem> allItemsWithNullManufatured = jpaManufactureItemService.getAllItemsWithNullManufatured();
        allItemsWithNullManufatured.stream()
                .forEach(jpaManufactureItemService::deleteManufactureItem);
        HttpSession session = request.getSession();
        if (session.getAttribute("manufactured") != null) {
            session.removeAttribute("manufactured");
        }
        return "redirect:/kitchen";
    }

    @RequestMapping("/finalizeOrder")
    public String finalizeWorkOrder(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long manufactured1 = (Long) session.getAttribute("manufactured");

        logger.error("Id manufactured in finalize: " + manufactured1);

        Set<ManufactureItem> allByNullManufactureId = jpaManufactureItemService.getAllItemsWithNullManufatured();
        for ( ManufactureItem manufactureItem : allByNullManufactureId ) {
            int quantity = manufactureItem.getQuantity();
            Set<RecipeItem> recipeItemList = manufactureItem.getProduct().getRecipe().getRecipeItemList();
            for ( RecipeItem recipeItem : recipeItemList ) {
                long id = recipeItem.getIngredients().getId();
                Double manufactureItemIngredientQuantity = recipeItem.getIngredientQuantity();
//     get ingredient and change it stock quantity
                Ingredient ingredient = jpaIngredientService.getIngredient(id).get();
                if (ingredient.getLitr() != 0) {
                    ingredient.setLitr(ingredient.getLitr() - (manufactureItemIngredientQuantity * quantity));
                } else if (ingredient.getWeight() != 0) {
                    ingredient.setWeight(ingredient.getWeight() - (manufactureItemIngredientQuantity * quantity));
                } else {
                    ingredient.setQuantity(ingredient.getQuantity() - (manufactureItemIngredientQuantity * quantity));
                }
                jpaIngredientService.updateIngredient(ingredient);

            }
            // create manufactured, finalize work order with manufactureItems and update stock
            Manufactured finalManufactured = jpaManufacturedService.getManufactured(manufactured1).orElseThrow(EntityNotFoundException::new);
            finalManufactured.setFinalizedWorkOrder(true);
            jpaManufacturedService.updateManufactured(finalManufactured);
            manufactureItem.setManufactured(finalManufactured);
            jpaManufactureItemService.updateManufactureItem(manufactureItem);
            addProduced(jpaManufacturedService.getManufactured(manufactured1).orElseThrow(EntityNotFoundException::new));
        }
        //add to Stock
        jpaStockService.addStock(jpaManufacturedService.getManufactured(manufactured1)
                .orElseThrow(EntityNotFoundException::new));

        model.addAttribute("finalManufactureId", manufactured1);
        session.removeAttribute("manufactured");
        return "redirect:/kitchen/summarizing";
    }

    @RequestMapping("/summarizing")
    public String showBookedOrderDetails(HttpServletRequest request, Model model) {
        String finalManufactureId = request.getParameter("finalManufactureId");
        model.addAttribute("workOrder", jpaManufactureItemService.getAllByManufactureId(Long.parseLong(finalManufactureId)));
        return "kitchenWorkOrderDetails";
    }

    private void addProduced(Manufactured manufactured) {
        Set<ManufactureItem> manufactureItems = manufactured.getManufactureItems();
        Produced produced = new Produced();
        for ( ManufactureItem item : manufactureItems ) {
            produced.setProductName(item.getProduct().getName());
            produced.setProductPrice(item.getProduct().getPrice());
            produced.setProductStockQuantity(item.getQuantity());
        }
        jpaProducedService.addProduced(produced);
    }
}
