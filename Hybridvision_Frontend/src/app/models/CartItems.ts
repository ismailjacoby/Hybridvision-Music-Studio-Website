export interface CartItems{
  id: number;
  title: string;
  coverImage: string;
  price: number;
  quantity:number;
  itemType: string;
  licenseType?: string;
  stripePriceId: string;
}
