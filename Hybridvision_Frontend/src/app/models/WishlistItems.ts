import {LicenseType} from "./LicenseType";

export interface WishlistItems{
  id: number,
  title: string,
  coverImage: string,
  price: number,
  itemType: string,
  licenseType: LicenseType,
  stripePriceId: string
}
