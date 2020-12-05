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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/kitchen")
//@SessionAttributes({"manufactured"})
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

    @RequestMapping("")
    public String getKitchenView(@ModelAttribute("editQuantityError") String errorModel, Model model, HttpSession session, HttpServletRequest request, Principal principal,
                                 SessionStatus status) {

        if (session.getAttribute("manufactured") == null) {
            jpaManufactureItemService.getAllItemsWithNullManufatured().forEach(jpaManufactureItemService::deleteManufactureItem);
            session.removeAttribute("manufactured");
        }
        //summary of working order
        Set<ManufactureItem> allByNullManufactureId = jpaManufactureItemService.getAllItemsWithNullManufatured();
        model.addAttribute("allManufacturedItems", allByNullManufactureId);
        // add work Ingredients Quantity to find out if we can manufacture selected product, if we have enough ingredient quantity
        if (allByNullManufactureId.size() == 0) {
            jpaWorkIngredientQuantityService.getAllWorkIngredients().forEach(jpaWorkIngredientQuantityService::deleteWorkIngredient);
            copyIngredientstoWorkIngredients();
            if (session.getAttribute("manufactured") != null) {
                session.removeAttribute("manufactured");
            }
        }
        //show only products that can be manufactured
        List<WorkIngredientQuantity> allWorkIngredients = jpaWorkIngredientQuantityService.getAllWorkIngredients();
        List<Product> allProducts = jpaProductService.getAllProducts();
        List<Product> productAbleToManufacture = new ArrayList<>();
        List<Double> possibleNumberToProduce = null;
        for ( Product p : allProducts ) {
            boolean checkIfAbleToAddProduct = true;
            Set<RecipeItem> recipeItemList = p.getRecipe().getRecipeItemList();
            //possible number to produce
            possibleNumberToProduce = new ArrayList<>();
            double workIngredientQuantity = 0;
            double ingredientQuantity = 0;
            // end possible numer to produce
            for ( RecipeItem recipeItem : recipeItemList ) {
                String ingredientName = recipeItem.getIngredients().getName();
                ingredientQuantity = recipeItem.getIngredientQuantity();
                WorkIngredientQuantity workIngredientByIngredientName = jpaWorkIngredientQuantityService.getWorkIngredientByIngredientName(ingredientName);
                workIngredientQuantity = workIngredientByIngredientName.getWorkIngredientQuantity();
                if (ingredientQuantity > workIngredientByIngredientName.getWorkIngredientQuantity()) {
                    checkIfAbleToAddProduct = false;
                }
                double v = workIngredientQuantity / ingredientQuantity;
                possibleNumberToProduce.add(v);
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
        try {
            OptionalDouble any = possibleNumberToProduce.stream().mapToDouble(value -> value).min();
            if (any.isPresent()) {
                model.addAttribute("kitchenBadges", any.getAsDouble());
            }
        }catch (NullPointerException e){
            model.addAttribute("nullMessage", "There is nothing to show");
        }
        //show message if choosen quantity in calculator is to much
        HttpSession session1 = request.getSession();
        if (session1.getAttribute("manufactureProductQuantityErrorIngredients") != null) {

            Object manufactureProductQuantityErrorIngredients = session1.getAttribute("manufactureProductQuantityErrorIngredients");
            String productNameQuantityError = (String) session1.getAttribute("productNameQuantityError");
            Integer productQuantityError = (Integer) session1.getAttribute("productQuantityError");

            model.addAttribute("alertIngredients", manufactureProductQuantityErrorIngredients);
            model.addAttribute("alertQuantity", productQuantityError);
            model.addAttribute("alertProductName", productNameQuantityError);

            session1.removeAttribute("manufactureProductQuantityErrorIngredients");
            session1.removeAttribute("productNameQuantityError");
            session1.removeAttribute("productQuantityError");

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
//            workIngredientQuantity.setId(i.getId());
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
    public String createManufacturedProduct(@PathVariable long id, Model model) {
        logger.error("Product id witch was choosen in kitchen view: " + id);
        ManufactureItem manufactureItem = new ManufactureItem();
        manufactureItem.setProduct(jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("productId", id);
        model.addAttribute("manufactureItem", manufactureItem);
        return "kitchenAddProductQuantityView";
    }

    @PostMapping("/addProducedItemProduct")
    public String addProducedItem(@Valid ManufactureItem manufactureItem, BindingResult result, Model model, HttpServletRequest request, HttpSession session) {
        if (session.getAttribute("manufactured") == null) {
            session.setAttribute("manufactured", jpaManufacturedService.getManufacturedNotFinalized().getId());
        }
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

        HttpSession session1 = request.getSession();
        if (alerts.size() > 0) {
            if (session1.getAttribute("manufactureProductQuantityErrorIngredients") == null) {
                session1.setAttribute("manufactureProductQuantityErrorIngredients", alerts);
                session1.setAttribute("productNameQuantityError", product.getName());
                session1.setAttribute("productQuantityError", productQuantity);
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
    public String deleteWorkOrder(HttpServletRequest request, HttpSession session, SessionStatus status) {
        Set<ManufactureItem> allItemsWithNullManufatured = jpaManufactureItemService.getAllItemsWithNullManufatured();
        allItemsWithNullManufatured.stream()
                .forEach(jpaManufactureItemService::deleteManufactureItem);
        if (session.getAttribute("manufactured") != null) {
            session.removeAttribute("manufactured");
        }
        return "redirect:/kitchen";
    }

    @RequestMapping("/finalizeOrder")
    public String finalizeWorkOrder(HttpServletRequest request, Model model, HttpSession session) {
        Long manufactured1 = (Long) session.getAttribute("manufactured");
        logger.error("Manufactured w finalize: "+ manufactured1);

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
                    double v = BigDecimal.valueOf(manufactureItemIngredientQuantity * quantity).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    ingredient.setLitr(ingredient.getLitr() - (v));
                } else if (ingredient.getWeight() != 0) {
                    double v = BigDecimal.valueOf(manufactureItemIngredientQuantity * quantity).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    ingredient.setWeight(ingredient.getWeight() - (v));
                } else {
                    double v = BigDecimal.valueOf(manufactureItemIngredientQuantity * quantity).setScale(3, RoundingMode.HALF_UP).doubleValue();
                    ingredient.setQuantity(ingredient.getQuantity() - (v));
                }
                jpaIngredientService.updateIngredient(ingredient);
            }
            // create manufactured, finalize work order with manufactureItems and update stock
            Manufactured finalManufactured = jpaManufacturedService.getManufactured(manufactured1).orElseThrow(EntityNotFoundException::new);
            finalManufactured.setFinalizedWorkOrder(true);
            jpaManufacturedService.updateManufactured(finalManufactured);
            manufactureItem.setManufactured(finalManufactured);
            jpaManufactureItemService.updateManufactureItem(manufactureItem);
        }
        //add to Stock
        Manufactured manufactured = jpaManufacturedService.getManufactured(manufactured1).orElseThrow(EntityNotFoundException::new);
        jpaStockService.addStock(manufactured);

        List<ManufactureItem> allByManufactureId = jpaManufactureItemService.getAllByManufactureId(manufactured1);
        jpaProducedService.addManufacturedToProduced(allByManufactureId);

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

    @RequestMapping("/deleteWorkItem/{id}")
    public String deleteWorkItem(@PathVariable long id) {
        ManufactureItem manufactureItem = jpaManufactureItemService.getManufactureItem(id).orElseThrow(EntityNotFoundException::new);

        int quantity = manufactureItem.getQuantity();
        Set<RecipeItem> recipeItemList = manufactureItem.getProduct().getRecipe().getRecipeItemList();
        for ( RecipeItem recipeItem : recipeItemList ) {
            String ingredientName = recipeItem.getIngredients().getName();
            Double ingredientQuantity = recipeItem.getIngredientQuantity();
            double manufactureItemIngQuantityToCheck = BigDecimal.valueOf(quantity * ingredientQuantity).setScale(3, RoundingMode.HALF_UP).doubleValue();
            WorkIngredientQuantity workIngredientByIngredientName =
                    jpaWorkIngredientQuantityService.getWorkIngredientByIngredientName(ingredientName);
            workIngredientByIngredientName.setWorkIngredientQuantity(workIngredientByIngredientName.getWorkIngredientQuantity() + manufactureItemIngQuantityToCheck);
            jpaWorkIngredientQuantityService.updateWorkIngredient(workIngredientByIngredientName);
        }
        jpaManufactureItemService.deleteManufactureItem(manufactureItem);
        return "redirect:/kitchen";
    }

    @PostMapping("/editWorkItem/{cquantity}")
    public String editWorkItem(@PathVariable int cquantity, @Valid ManufactureItem manufactureItem, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editQuantityError", "Quantity of item can't be less then 1");
            return "redirect:/kitchen";
        }
        int previousItemQuantity = cquantity;
        logger.error("previous quantity: " + previousItemQuantity);
        int newQuantity = manufactureItem.getQuantity();
        logger.error("new quantity: " + newQuantity);
        Set<RecipeItem> recipeItemList = manufactureItem.getProduct().getRecipe().getRecipeItemList();
        for ( RecipeItem recipeItem : recipeItemList ) {
            String ingredientName = recipeItem.getIngredients().getName();
            Double ingredientQuantity = recipeItem.getIngredientQuantity();
            double newIngredientQuantity = BigDecimal.valueOf(newQuantity * ingredientQuantity).setScale(3, RoundingMode.HALF_UP).doubleValue();
            double previousIngredientQuantity = BigDecimal.valueOf(previousItemQuantity * ingredientQuantity).setScale(3, RoundingMode.HALF_UP).doubleValue();
            WorkIngredientQuantity workIngredientByIngredientName =
                    jpaWorkIngredientQuantityService.getWorkIngredientByIngredientName(ingredientName);
            double newWorkIngredientQuantity = workIngredientByIngredientName.getWorkIngredientQuantity()
                    + previousIngredientQuantity - newIngredientQuantity;
            if (newWorkIngredientQuantity < 0.0) {
                return "redirect:/kitchen";
            } else {
                workIngredientByIngredientName.setWorkIngredientQuantity(newWorkIngredientQuantity);
                jpaWorkIngredientQuantityService.updateWorkIngredient(workIngredientByIngredientName);
            }
        }
        jpaManufactureItemService.updateManufactureItem(manufactureItem);
        return "redirect:/kitchen";
    }

    @ModelAttribute("manufactureItemToEdit")
    public ManufactureItem getManufactureItemToEdit() {
        return new ManufactureItem();
    }
}
