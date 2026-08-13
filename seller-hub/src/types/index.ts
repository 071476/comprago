export interface User {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  role: 'BUYER' | 'SELLER' | 'ADMIN';
}

export interface Seller {
  id: number;
  userId: number;
  storeName: string;
  storeDescription: string;
  createdAt: string;
}

export interface Product {
  id: number;
  sellerId: number;
  name: string;
  description: string;
  price: number;
  categoryId: number;
  images: string[];
  createdAt: string;
}

export interface Order {
  id: number;
  buyerId: number;
  sellerId: number;
  totalAmount: number;
  status: 'PENDING' | 'PAID' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED';
  createdAt: string;
}

export interface InventoryItem {
  id: number;
  productId: number;
  quantity: number;
  reserved: number;
  available: number;
}

export interface Shipping {
  id: number;
  orderId: number;
  trackingNumber: string;
  carrier: string;
  status: 'PENDING' | 'SHIPPED' | 'IN_TRANSIT' | 'DELIVERED';
  estimatedDelivery: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: User;
}

export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}
