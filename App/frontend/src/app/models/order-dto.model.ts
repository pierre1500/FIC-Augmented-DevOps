import { OrderedProductDto } from './ordered-product-dto.model';

export interface OrderDto {
  id: number;
  orderedProductDtoList: OrderedProductDto[];
}
