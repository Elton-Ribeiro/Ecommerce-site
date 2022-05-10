package com.carrinho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrinho.model.Cart;
import com.carrinho.repository.CartRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v2/")
public class CarrinhoController {

	@Autowired
	private CartRepository cartRepository;

	@GetMapping("/produtos")
	public List<Cart> getAllCarts() {		
		return cartRepository.findAll();
		System.out.println("GET TODOS OS PRODUTOS");
	}

	@PostMapping("/carrinho")
	public Cart createCart(@RequestBody Cart artist) {		
		return cartRepository.save(carrinho);
		System.out.println("ADD PRODUTO NO CARRINHO");
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<Cart> getCarrinhoById(@PathVariable Long id) {
		System.out.println("FIND CART BY ID");
		Cart artist = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Não existe produto com id: " + id));
		return ResponseEntity.ok(carrinho);
	}

	
	
}