package com.shopme.wishlist;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.ControllerHelper;
import com.shopme.address.AddressService;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShippingRate;
import com.shopme.common.entity.Wishlist;
import com.shopme.common.exception.CustomerNotFoundException;
import com.shopme.shipping.ShippingRateService;

@Controller
public class WishlistController {
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private WishlistService wishlistService;
	@Autowired private AddressService addressService;
	@Autowired private ShippingRateService shipService;
	
	@GetMapping("/wishlist")
	public String viewwishlist(Model model, HttpServletRequest request) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(request);
		List<Wishlist> wishlist = wishlistService.listWishlistItems(customer);
		
		float estimatedTotal = 0.0F;
		
		for (Wishlist item : wishlist) {
			estimatedTotal += item.getSubtotal();
		}
		
		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		boolean usePrimaryAddressAsDefault = false;
		
		if (defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			usePrimaryAddressAsDefault = true;
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}
		
		model.addAttribute("usePrimaryAddressAsDefault", usePrimaryAddressAsDefault);
    	model.addAttribute("shippingSupported", shippingRate != null);
		model.addAttribute("wishlist", wishlist);
		model.addAttribute("estimatedTotal", estimatedTotal);
		
		return "wishlist/wishlist";
	}
	
//	@PostMapping("wishlist/add/{productId}")
//	public String addProductToWishlist(@PathVariable("productId") Integer productId,
//			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
//		
//		try {
//			Customer customer = getAuthenticatedCustomer(request);
//			Integer updatedQuantity = wishlistService.addProduct(productId, quantity, customer);
//			return updatedQuantity + " item(s) of this product were added to your shopping wishlist.";
//		} catch (WishlistException ex) {
//			return ex.getMessage();
//		}
//		
//	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
