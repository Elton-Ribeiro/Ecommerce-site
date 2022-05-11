package com.carrinho.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrinho.exception.NotFoundException;
import com.carrinho.model.Carrinho;
import com.carrinho.repository.CarrinhoRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class CarrinhoController {

	@Autowired
	private CarrinhoRepository carrinhoRepository;

	@GetMapping("/carrinho")
	public List<Carrinho> getAllCarrinho() {		
		return carrinhoRepository.findAll();		
	}

	@PostMapping("/carrinho/{id}")
	public Carrinho createCarrinho(@RequestBody Carrinho carrinho) {		
		return carrinhoRepository.save(carrinho);		
	}

	@GetMapping("/carrinho/{id}")
	public ResponseEntity<Carrinho> getCarrinhoById(@PathVariable Long id) {		
		Carrinho carrinho = carrinhoRepository.findById(id).orElseThrow(() -> new NotFoundException("Não existe produto com id :" + id));
		return ResponseEntity.ok(carrinho);
	}

	@PutMapping("/carrinho/{id}")
	public ResponseEntity<Carrinho> updateCarrinho(@PathVariable Long id, @RequestBody Carrinho carrinhoDetails) {		
		Carrinho carrinho = carrinhoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não existe produto com id :" + id));

		carrinho.setProduto(carrinhoDetails.getProduto());
		carrinho.setQtd(carrinhoDetails.getQtd());		

		Carrinho updateCarrinho = carrinhoRepository.save(carrinho);
		return ResponseEntity.ok(updateCarrinho);		
	}

	@DeleteMapping("/carrinho/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCarrinho(@PathVariable Long id) {		
		Carrinho carrinho = carrinhoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não existe produto com id :" + id));

		carrinhoRepository.delete(carrinho );
		Map<String, Boolean> response = new HashMap<>();
		response.put("DELETED", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}	
	
}