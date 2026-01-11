import { Product } from './product.model';

export interface OrderedProductDto {
  product: Product;
  quantity: number;
}
