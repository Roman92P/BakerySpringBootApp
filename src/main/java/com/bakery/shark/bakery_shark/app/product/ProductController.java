package com.bakery.shark.bakery_shark.app.product;


import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.Ingredient;
import com.bakery.shark.bakery_shark.app.model.Product;
import com.bakery.shark.bakery_shark.app.model.Recipe;
import com.bakery.shark.bakery_shark.app.recipe.JpaRecipeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {


    @Autowired
    Validator validator;

    @Autowired
    private final JpaProductService jpaProductService;
    private final JpaIngredientService ingredientService;
    private final JpaRecipeService recipeService;

    @GetMapping("/addImg/{id}")
    public String uploadImgToProduct(@PathVariable Long id, Model model){
        Product product = jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("productForPhoto", product);

        return "addProductImg";
    }

    @PostMapping("/upload/{id}")
    public String postUpload(@RequestParam("file") MultipartFile file, @PathVariable long id, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "addProductImg";
        }

        Optional<Product> product = jpaProductService.getProduct(id);
        if(product.isPresent()){
            Product product1 = product.get();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            product1.setPhoto(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        jpaProductService.updateProduct(product1);
    }
        return "redirect:/product/details/"+product.get().getId();
    }

    @RequestMapping("/add")
    public String addProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "addProductView";
    }

    @PostMapping("/add/")
    public String addNewProductFromForm(@Valid Product product, BindingResult result) {
        if(result.hasErrors()){
            return "addProductView";
        }
        jpaProductService.addProduct(product);
        return "redirect:/main";
    }

    @RequestMapping("/edit/{id}")
    public String editProductForm(@PathVariable long id, Model model) {
        model.addAttribute("product", jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new));
        return "addProductView";
    }

    @PostMapping("/edit")
    public String editProduct(Product product) {
        jpaProductService.updateProduct(product);
        return "redirect:/main";
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        Optional<Product> product = jpaProductService.getProduct(id);
        if (product.isPresent()) {
            Product product1 = product.get();
            jpaProductService.deleteProduct(product1);
        } else {
            throw new EntityNotFoundException();
        }
        return "redirect:/main";
    }

    @ModelAttribute("ingredients")
    public List<Ingredient> allIngredients() {
        return ingredientService.getAllIngredients();
    }

    @ModelAttribute("recipes")
    public List<Recipe> allRecipesForNewProduct() {
        return recipeService.getAllRecipes();
    }

    @RequestMapping("/details/{id}")
    public String productDeatils(@PathVariable long id, Model model){
        Product product = jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("product", product);

        byte[] img = product.getPhoto();
        String image = "";
        if (img != null && img.length > 0) {
            image = Base64.getEncoder().encodeToString(img);
            model.addAttribute("photo", image);
        }

        return "productDetailsView";
    }

    @RequestMapping("/deleteImg/{id}")
    public String deleteProductImg(@PathVariable Long id, Model model){
        Product product = jpaProductService.getProduct(id).orElseThrow(EntityNotFoundException::new);
        product.setPhoto(null);
        jpaProductService.updateProduct(product);
        return "redirect:/product/details/"+id;
    }
}
