import axios from 'axios';

const api = axios.create();

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const AUTH = 'https://comprago.onrender.com';
const SELLERS = 'https://name-comprago-sellers.onrender.com';
const PRODUCTS = 'https://name-comprago-products.onrender.com';
const INVENTORY = 'https://comprago-inventory.onrender.com';
const ORDERS = 'https://comprago-orders.onrender.com';
const SHIPPING = 'https://comprago-shipping.onrender.com';

export const authApi = {
  login: (email: string, password: string) =>
    axios.post(`${AUTH}/api/auth/login`, { email, password }),
  register: (data: { email: string; password: string; firstName: string; lastName: string }) =>
    axios.post(`${AUTH}/api/auth/register`, { ...data, role: 'SELLER' }),
};

export const sellersApi = {
  getProfile: () => api.get(`${SELLERS}/api/sellers/me`),
  updateProfile: (data: { storeName: string; storeDescription: string }) =>
    api.put(`${SELLERS}/api/sellers/me`, data),
};

export const productsApi = {
  list: () => api.get(`${PRODUCTS}/api/products`),
  create: (data: { name: string; description: string; price: number }) =>
    api.post(`${PRODUCTS}/api/products`, data),
  update: (id: number, data: { name: string; description: string; price: number }) =>
    api.put(`${PRODUCTS}/api/products/${id}`, data),
  delete: (id: number) => api.delete(`${PRODUCTS}/api/products/${id}`),
};

export const inventoryApi = {
  list: () => api.get(`${INVENTORY}/api/inventory`),
  update: (id: number, quantity: number) =>
    api.put(`${INVENTORY}/api/inventory/${id}`, { quantity }),
};

export const ordersApi = {
  list: () => api.get(`${ORDERS}/api/orders`),
  getOne: (id: number) => api.get(`${ORDERS}/api/orders/${id}`),
  updateStatus: (id: number, status: string) =>
    api.put(`${ORDERS}/api/orders/${id}/status`, { status }),
};

export const shippingApi = {
  list: () => api.get(`${SHIPPING}/api/shipping`),
  create: (orderId: number) => api.post(`${SHIPPING}/api/shipping`, { orderId }),
  track: (id: number) => api.get(`${SHIPPING}/api/shipping/${id}/track`),
};

export default api;
