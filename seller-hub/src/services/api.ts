import axios from 'axios';

const BASE = 'https://comprago-gateway.onrender.com';

const api = axios.create({ baseURL: BASE });

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authApi = {
  login: (email: string, password: string) =>
    api.post('/api/auth/login', { email, password }),
  register: (data: { email: string; password: string; firstName: string; lastName: string }) =>
    api.post('/api/auth/register', { ...data, role: 'SELLER' }),
};

export const sellersApi = {
  getProfile: () => api.get('/api/sellers/me'),
  updateProfile: (data: { storeName: string; storeDescription: string }) =>
    api.put('/api/sellers/me', data),
};

export const productsApi = {
  list: () => api.get('/api/products'),
  create: (data: { name: string; description: string; price: number }) =>
    api.post('/api/products', data),
  update: (id: number, data: { name: string; description: string; price: number }) =>
    api.put(`/api/products/${id}`, data),
  delete: (id: number) => api.delete(`/api/products/${id}`),
};

export const inventoryApi = {
  list: () => api.get('/api/inventory'),
  update: (id: number, quantity: number) =>
    api.put(`/api/inventory/${id}`, { quantity }),
};

export const ordersApi = {
  list: () => api.get('/api/orders'),
  getOne: (id: number) => api.get(`/api/orders/${id}`),
  updateStatus: (id: number, status: string) =>
    api.put(`/api/orders/${id}/status`, { status }),
};

export const shippingApi = {
  list: () => api.get('/api/shipping'),
  create: (orderId: number) => api.post('/api/shipping', { orderId }),
  track: (id: number) => api.get(`/api/shipping/${id}/track`),
};

export default api;
