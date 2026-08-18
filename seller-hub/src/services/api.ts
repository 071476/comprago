import axios from 'axios';

const api = axios.create();

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const PRODUCTS = 'https://comprago-products.onrender.com';
const ORDERS = 'https://comprago-orders-service.onrender.com';
const INVENTORY = 'https://comprago-inventory.onrender.com';
const SHIPPING = 'https://comprago-shipping.onrender.com';

export const authApi = {
  login: (email: string, _password: string) => {
    return Promise.resolve({
      data: {
        email,
        firstName: 'Vendedor',
        lastName: 'CompraGo',
        role: 'SELLER',
        token: 'seller-hub-token-' + Date.now()
      }
    });
  },
  register: (data: { email: string; password: string; firstName: string; lastName: string }) => {
    return Promise.resolve({
      data: {
        email: data.email,
        firstName: data.firstName,
        lastName: data.lastName,
        role: 'SELLER',
        token: 'seller-hub-token-' + Date.now()
      }
    });
  },
};

export const mediaApi = {
  upload: async (file: File) => {
    const formData = new FormData();
    formData.append('file', file);
    const res = await api.post(`${PRODUCTS}/api/media/upload`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return res.data.url as string;
  },
};

export const productsApi = {
  list: () => api.get(`${PRODUCTS}/api/products`),
  getOne: (id: number) => api.get(`${PRODUCTS}/api/products/${id}`),
  create: (data: { name: string; description: string; price: number; category?: string; stock?: number; imageUrls?: string[] }) =>
    api.post(`${PRODUCTS}/api/products`, data, { headers: { 'X-Seller-Id': '1' } }),
  update: (id: number, data: { name: string; description: string; price: number; imageUrls?: string[] }) =>
    api.put(`${PRODUCTS}/api/products/${id}`, data),
  delete: (id: number) => api.delete(`${PRODUCTS}/api/products/${id}`),
};

export const ordersApi = {
  list: () => api.get(`${ORDERS}/api/orders`),
  getBySeller: (sellerId: number) => api.get(`${ORDERS}/api/orders/seller/${sellerId}`),
  getOne: (id: number) => api.get(`${ORDERS}/api/orders/${id}`),
  updateStatus: (id: number, status: string) =>
    api.put(`${ORDERS}/api/orders/${id}/status`, { status }),
};

export const inventoryApi = {
  list: () => api.get(`${INVENTORY}/api/inventory`),
  getBySeller: (sellerId: number) => api.get(`${INVENTORY}/api/inventory/seller/${sellerId}`),
  getLowStock: () => api.get(`${INVENTORY}/api/inventory/low-stock`),
  create: (data: { productId: number; sellerId: number; productName: string; stock: number; minStock?: number }) =>
    api.post(`${INVENTORY}/api/inventory`, data),
  updateStock: (id: number, stock: number) =>
    api.put(`${INVENTORY}/api/inventory/${id}/stock`, { stock }),
};

export const shippingApi = {
  list: () => api.get(`${SHIPPING}/api/shipping`),
  getBySeller: (sellerId: number) => api.get(`${SHIPPING}/api/shipping/seller/${sellerId}`),
  getOne: (id: number) => api.get(`${SHIPPING}/api/shipping/${id}`),
  create: (data: { orderId: number; sellerId: number; buyerId: number; carrier: string; originAddress: string; destinationAddress: string; estimatedDelivery: string }) =>
    api.post(`${SHIPPING}/api/shipping`, data),
  updateStatus: (id: number, status: string) =>
    api.put(`${SHIPPING}/api/shipping/${id}/status`, { status }),
};

export default api;
