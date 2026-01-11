import { ProductInStock } from './product-in-stock.model';

export interface Stock {
  stockName: string;
  productList: ProductInStock[];
}
