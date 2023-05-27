package com.shopme.wishlist;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Wishlist;
import com.shopme.common.entity.product.Product;
import com.shopme.product.ProductRepository;

@Service
@Transactional
public class WishlistService {

	@Autowired private WishlistRepository wishlistRepo;
	
	@Autowired private ProductRepository productRepo;
	
	public Integer addProduct(Integer productId, Integer quantity, Customer customer) 
			throws WishlistException {
		Integer updatedQuantity = quantity;
		Product product = new Product(productId);
		
		Wishlist wishlistItem = wishlistRepo.findByCustomerAndProduct(customer, product);
		
		if (wishlistItem != null) {
			updatedQuantity = wishlistItem.getQuantity() + quantity;
			
			if (updatedQuantity > 5) {
				throw new WishlistException("Could not add more " + quantity + " item(s)"
						+ " because there's already " + wishlistItem.getQuantity() + " item(s) "
						+ "in your shopping wishlist. Maximum allowed quantity is 5.");
			}
		} else {
			wishlistItem = new Wishlist();
			wishlistItem.setCustomer(customer);
			wishlistItem.setProduct(product);
		}
		
		wishlistItem.setQuantity(updatedQuantity);
		
		wishlistRepo.save(wishlistItem);
		
		return updatedQuantity;
	}
	
	public List<Wishlist> listWishlistItems(Customer customer) {
		return wishlistRepo.findByCustomer(customer);
	}
	
	public float updateQuantity(Integer productId, Integer quantity, Customer customer) {
		wishlistRepo.updateQuantity(quantity, customer.getId(), productId);
		Product product = productRepo.findById(productId).get();
		float subtotal = product.getDiscountPrice() * quantity;
		return subtotal;
	}
	
	public void removeProduct(Integer productId, Customer customer) {
		wishlistRepo.deleteByCustomerAndProduct(customer.getId(), productId);
	}
	
	public void deleteByCustomer(Customer customer) {
		wishlistRepo.deleteByCustomer(customer.getId());
	}
}
