package com.rsvier.workshop2.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/product")
@SessionAttributes({ "product", "model" })
public class ProductController {

	private ProductRepository productRepository;

	@Autowired
	public ProductController(ProductRepository productRepository) {

		this.productRepository = productRepository;
	}

	@ModelAttribute("product")
	public Product product() {
		return new Product();
	}

	@GetMapping("/createNewProduct")
	public String showCreateProductPage(Model model) {
		List<Product> productList = (List<Product>) productRepository.findAll();
		model.addAttribute(productList);
		return "createNewProductForm";
	}

	@GetMapping("/searchProduct")
	public String searchProductForm(Model model) {
		Iterable<Product> productList = productRepository.findAll();
		model.addAttribute("productList", productList);
		return "searchProduct";
	}

	@PostMapping("/createNewProduct")
	public String addProduct(@Valid Product product, Errors errors, Model model, SessionStatus sessionStatus, 
			RedirectAttributes redirectAttributes) {
		
		
		
		if (errors.hasErrors()) {

			return "createNewProductForm";
		}
		
		

		productRepository.save(product);

		sessionStatus.setComplete();
		String editProductMessage = "Product is aangemaakt.";
		model.addAttribute("editProductMessage", editProductMessage);
		redirectAttributes.addFlashAttribute("productMessage", editProductMessage);

		return "redirect:/employee";
	}

	
	@PostMapping("/foundProduct")
	public String FoundProduct(Product product, Model model) {

		if (productRepository.findByName(product.getName()) == null) {
			
			return "searchProduct";
		}
		
		Product productDB = productRepository.findByName(product.getName());

		model.addAttribute("product", productDB);

		return "foundProduct";
	}

	
	@PostMapping("/deleteProduct")
    public String deleteProduct(Product product, Model model, SessionStatus sessionstatus, RedirectAttributes redirectAttributes) {


        productRepository.delete(product);
        
        String deletedProductMesage = "Product is verwijderd";
       
        redirectAttributes.addFlashAttribute("productMesage", deletedProductMesage);
        sessionstatus.setComplete();
        
        return "redirect:/employee";
    }

	
    @PostMapping("/editProduct")
    public String editProduct(Product product, RedirectAttributes redirectAttributes, Model model) {

        productRepository.save(product);
        
        String editProductMesage = "Product is aangepast.";
      
        redirectAttributes.addFlashAttribute("productMesage", editProductMesage);
        
        return "redirect:/employee";
    }


}
