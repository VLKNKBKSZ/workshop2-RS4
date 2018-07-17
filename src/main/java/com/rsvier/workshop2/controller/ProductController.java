package com.rsvier.workshop2.controller;

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

import com.rsvier.workshop2.domain.Account;
import com.rsvier.workshop2.domain.Product;
import com.rsvier.workshop2.repository.ProductRepository;

@Controller
@RequestMapping("/product")
@SessionAttributes("product")
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
	public String showCreateProductPage() {
		return "createNewProductForm";
	}
	
	@GetMapping("/searchProduct")
	public String searchProductForm() {
		return "searchProduct";
	}

	@PostMapping("/createNewProduct")
	public String addProduct(@Valid Product product, Errors errors, Model model, SessionStatus sessionStatus) {
		if(errors.hasErrors()) {
			return "createNewProductForm";
		}
		
		productRepository.save(product);
		String productAdded = "Het product is aangemaakt";
		model.addAttribute("editMessage", productAdded);
		sessionStatus.setComplete();
		return "redirect:/employee";
	}
	
	@PostMapping("/foundProduct")
	public String FoundProduct(Product product,Model model) {
		
		Product productDB = productRepository.findByName(product.getName());
		
		model.addAttribute("product", productDB);
		
		return "foundProduct";
	}

}
