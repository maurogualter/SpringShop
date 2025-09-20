package com.mauro.shop.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartItem> items = new HashSet<CartItem>();
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user; 
	
	public void addItem(CartItem item) {
		this.items.add(item);
		item.setCart(this);
		uptadeTotalAmount();
	}
	
	public void removeItem(CartItem item) {
		this.items.remove(item);
		item.setCart(null);
		uptadeTotalAmount();
	}
	
	public void uptadeTotalAmount() {
		this.totalAmount = this.items.stream().map(item ->{
			BigDecimal unitPrice = item.getUnitPrice();
			if(unitPrice == null) {
				return BigDecimal.ZERO;
			}
			return unitPrice.multiply(BigDecimal.valueOf(item.getQuantaty()));
		}).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
