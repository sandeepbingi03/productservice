package com.shoppingcart.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.org.dto.Coupon;
import com.shoppingcart.org.entity.Product;
import com.shoppingcart.org.repository.ProductJpaRepository;

@RestController
@RequestMapping(value = "/productapi")
public class ProductRestController {
	
	@Autowired
	private ProductJpaRepository ProductJpaRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${couponservice.url}")
	private String couponServiceURL;
	
	@RequestMapping(value="/products", method=RequestMethod.POST)
	public Product createProduct(@RequestBody Product product) {
		Coupon coupon = restTemplate.getForObject(couponServiceURL + product.getCouponCode(), Coupon.class); // Cuopon.class is a dto for deserializing the the objct coming from url
		if(coupon == null) {
			throw new RuntimeException("Coupon code not found");
		}
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return ProductJpaRepository.save(product);
	}

}
