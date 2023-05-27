package com.shopme.wishlist;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopme.Utility;
import com.shopme.common.entity.Customer;
import com.shopme.common.exception.CustomerNotFoundException;
import com.shopme.customer.CustomerService;

@RestController
public class WishlistRestController {
	@Autowired private WishlistService wishlistService;
	
	@Autowired private CustomerService customerService;
	
	@PostMapping("/wishlist/add/{productId}/{quantity}")
	public String addProductToWishlist(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		
		try {
			Customer customer = getAuthenticatedCustomer(request);
			Integer updatedQuantity = wishlistService.addProduct(productId, quantity, customer);
			
			return updatedQuantity + " item(s) of this product were added to your shopping wishlist.";
		} catch (CustomerNotFoundException ex) {
			return "You must login to add this product to wishlist.";
		} catch (WishlistException ex) {
			return ex.getMessage();
		}
		
	}
	
	private Customer getAuthenticatedCustomer(HttpServletRequest request) 
			throws CustomerNotFoundException {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		if (email == null) {
			throw new CustomerNotFoundException("No authenticated customer");
		}
				
		return customerService.getCustomerByEmail(email);
	}
	
	@PostMapping("/wishlist/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		try {
			Customer customer = getAuthenticatedCustomer(request);
			float subtotal = wishlistService.updateQuantity(productId, quantity, customer);
			
			return String.valueOf(subtotal);
		} catch (CustomerNotFoundException ex) {
			return "You must login to change quantity of product.";
		}	
	}
	
	@DeleteMapping("/wishlist/remove/{productId}")
	public String removeProduct(@PathVariable("productId") Integer productId,
			HttpServletRequest request) {
		try {
			Customer customer = getAuthenticatedCustomer(request);
			wishlistService.removeProduct(productId, customer);
			
			return "The product has been removed from your shopping wishlist.";
			
		} catch (CustomerNotFoundException e) {
			return "You must login to remove product.";
		}
	}
}
