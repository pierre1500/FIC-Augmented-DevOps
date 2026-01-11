import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ShoppingService } from './shopping.service';
import { Stock } from './models/stock.model';
import { BehaviorSubject, Observable } from 'rxjs';
import { OrderDto } from './models/order-dto.model';
import { Product } from './models/product.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass'],
  imports: [CommonModule, FormsModule, HttpClientModule]
})
export class AppComponent implements OnInit {
  title: string = 'My shopping list';
  stock!: Observable<Stock>;
  products!: Observable<Product[]>;
  orders!: Observable<OrderDto[]>;
  uploadProgress: number | null = null;
  selectedFile: File | null = null;
  uploadMessage: string = '';

  constructor(private shoppingService: ShoppingService) {}

  ngOnInit(): void {
    this.products = this.shoppingService.getProducts();
    this.stock = this.shoppingService.getStock();
    this.orders = this.shoppingService.getOrders();
  }

  refreshData(): void {
    // re-fetch products and stock from the backend
    this.products = this.shoppingService.getProducts(true);
    this.stock = this.shoppingService.getStock(true);
  }

  get cart() {
    return this.shoppingService.getCart();
  }

  addToCart(product: Product | { name: string; price: number; quantity: number }) {
    let cartProduct;

    if ('productName' in product && 'productPrice' in product) {
      // If the product is of type Product
      cartProduct = {
        name: product.productName,
        price: product.productPrice,
        quantity: 1,
      };
    } else {
      // If the product is already in the transformed structure
      cartProduct = { ...product };
    }

    this.stock.subscribe(stock => {
      const stockItem = stock.productList.find(item => item.product.productName === cartProduct.name); // Adjusted property
      if (stockItem && stockItem.quantity > 0) {
        const existingProduct = this.cart.find(p => p.name === cartProduct.name);
        if (existingProduct) {
          existingProduct.quantity += 1;
        } else {
          this.cart.push(cartProduct);
        }
        stockItem.quantity -= 1; // Decrement stock
      } else {
        console.error('Cannot add to cart. Stock is empty for:', cartProduct.name);
        alert(`Cannot add to cart. Stock is empty for: ${cartProduct.name}`);
      }
    });
  }

  removeFromCart(product: { name: string; price: number; quantity: number }): void {
    this.shoppingService.removeFromCart(product);
  }

  getCartTotal(): number {
    return this.shoppingService.getCartTotal();
  }

  validateOrder(): void {
    const order = {
      id: 0, // Assuming ID is auto-generated on the backend
      orderedProductDtoList: this.cart.map(item => ({
        product: { productName: item.name, productPrice: item.price },
        quantity: item.quantity
      }))
    };
    this.shoppingService.validateOrder(order).subscribe(response => {
      console.log('Order validated:', response);
      this.orders = this.shoppingService.getOrders(); // Refresh the orders observable
      this.cart.length = 0; // Clear the cart after successful validation
    });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.uploadMessage = '';
    }
  }

  uploadProducts() {
    if (!this.selectedFile) {
      this.uploadMessage = 'Please select a file.';
      return;
    }
    this.uploadProgress = 0;
    this.shoppingService.uploadProductsFile(this.selectedFile).subscribe({
      next: event => {
        if (event.type === 1 && event.total) {
          // Progress event
          this.uploadProgress = Math.round(100 * (event.loaded / event.total));
        } else if (event.type === 4) {
          // Response event
          this.uploadMessage = 'Upload successful!';
          this.uploadProgress = null;
          // Optionally refresh products/stock
          this.products = this.shoppingService.getProducts();
          this.stock = this.shoppingService.getStock();
        }
      },
      error: () => {
        this.uploadMessage = 'Upload failed.';
        this.uploadProgress = null;
      }
    });
  }
}

