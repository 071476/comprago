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

export const authApi = {
  login: (email: string, password: string) =>
    axios.post(`${PRODUCTS}/api/auth/login`, { email, password }),
  register: (data: { email: string; password: string; firstName: string; lastName: string }) =>
    axios.post(`${PRODUCTS}/api/auth/register`, { ...data, role: 'SELLER' }),
};

export const productsApi = {
  list: () => api.get(`${PRODUCTS}/api/products`),
  getOne: (id: number) => api.get(`${PRODUCTS}/api/products/${id}`),
  create: (data: { name: string; description: string; price: number; category?: string; stock?: number }) =>
    api.post(`${PRODUCTS}/api/products`, data, { headers: { 'X-Seller-Id': '1' } }),
  update: (id: number, data: { name: string; description: string; price: number }) =>
    api.put(`${PRODUCTS}/api/products/${id}`, data),
  delete: (id: number) => api.delete(`${PRODUCTS}/api/products/${id}`),
};

export default api;
