import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpEventType } from '@angular/common/http';
import { environment } from '../environments/environment'; // Import environment configuration
import { OrderDto } from './models/order-dto.model';
import { Stock } from './models/stock.model';
import { Product } from './models/product.model';
import { OrderedProductDto } from './models/ordered-product-dto.model';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShoppingService {
  private cart: { name: string; price: number; quantity: number }[] = [];
  private backendUri = environment.backendUri; // Use environment variable
  private defaultStockId = 1; // Replace with an appropriate default value

  private stockSubject = new BehaviorSubject<Stock>(null!); // Observable for stock
  private productsSubject = new BehaviorSubject<Product[]>([]); // Observable for products
  private ordersSubject = new BehaviorSubject<OrderDto[]>([]); // Observable for orders

  stock$ = this.stockSubject.asObservable(); // Expose stock as observable
  products$ = this.productsSubject.asObservable(); // Expose products as observable
  orders$ = this.ordersSubject.asObservable(); // Expose orders as observable

  constructor(private http: HttpClient) {
    this.http.get<Stock>(`${this.backendUri}/stock/${this.defaultStockId}`).subscribe(stockData => {
      this.stockSubject.next(stockData);
    });

    this.http.get<Product[]>(`${this.backendUri}/products`).subscribe(productData => {
      this.productsSubject.next(productData);
    });

    this.http.get<OrderDto[]>(`${this.backendUri}/orders`).subscribe(orderData => {
      this.ordersSubject.next(orderData);
    });
  }

  getCart() {
    return this.cart;
  }

  addToCart(product: { name: string; price: number; quantity: number }) {
    if (product.quantity > 0) {
      const cartItem = this.cart.find(item => item.name === product.name);
      if (cartItem) {
        cartItem.quantity++;
      } else {
        this.cart.push({ ...product, quantity: 1 });
      }
      product.quantity--;
    }
  }

  removeFromCart(product: { name: string; price: number; quantity: number }) {
    const cartItem = this.cart.find(item => item.name === product.name);
    if (cartItem) {
      cartItem.quantity--;
      if (cartItem.quantity === 0) {
        this.cart = this.cart.filter(item => item !== cartItem);
      }
      const stockItem = this.stockSubject.value?.productList.find(item => item.product.productName === product.name);
      if (stockItem) {
        stockItem.quantity++;
      }
    }
  }

  getCartTotal(): number {
    return this.cart.reduce((total, item) => total + item.price * item.quantity, 0);
  }

  validateOrder(order: OrderDto) {
    return this.http.post(`${this.backendUri}/validate`, order);
  }

  getProducts(forceRefresh: boolean = false) {
    if (forceRefresh) {
      this.http.get<Product[]>(`${this.backendUri}/products`).subscribe(productData => {
        this.productsSubject.next(productData);
      });
    }
    return this.products$;
  }

  getStock(forceRefresh: boolean = false) {
    if (forceRefresh) {
      this.http.get<Stock>(`${this.backendUri}/stock/${this.defaultStockId}`).subscribe(stockData => {
        this.stockSubject.next(stockData);
      });
    }
    return this.stock$;
  }

  getOrders() {
    return this.orders$;
  }

  getOrderedProducts() {
    return this.http.get<OrderedProductDto[]>(`${this.backendUri}/orderedProducts`);
  }

  uploadProductsFile(file: File) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.backendUri}/upload/products`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }
}
