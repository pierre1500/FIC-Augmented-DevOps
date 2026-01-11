import { Product } from './product.model';

export interface ProductInStock {
  product: Product;
  quantity: number;
}
