package com.shopme.wishlist;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Wishlist;
import com.shopme.common.entity.product.Product;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
	
	public List<Wishlist> findByCustomer(Customer customer);
	
	public Wishlist findByCustomerAndProduct(Customer customer, Product product);
	
	@Modifying
	@Query("UPDATE Wishlist c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.product.id = ?3")
	public void updateQuantity(Integer quantity, Integer customerId, Integer productId);
	
	@Modifying
	@Query("DELETE FROM Wishlist c WHERE c.customer.id = ?1 AND c.product.id = ?2")
	public void deleteByCustomerAndProduct(Integer customerId, Integer productId);
	
	@Modifying
	@Query("DELETE Wishlist c WHERE c.customer.id = ?1")
	public void deleteByCustomer(Integer customerId);
}
