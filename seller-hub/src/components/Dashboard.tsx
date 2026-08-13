import { useEffect, useState } from 'react';
import { ordersApi, productsApi } from '../services/api';

interface Stats {
  totalOrders: number;
  totalProducts: number;
  totalRevenue: number;
  pendingOrders: number;
}

export default function Dashboard() {
  const [stats, setStats] = useState<Stats>({
    totalOrders: 0,
    totalProducts: 0,
    totalRevenue: 0,
    pendingOrders: 0,
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [ordersRes, productsRes] = await Promise.allSettled([
        ordersApi.list(),
        productsApi.list(),
      ]);

      const orders = ordersRes.status === 'fulfilled' ? ordersRes.value.data : [];
      const products = productsRes.status === 'fulfilled' ? productsRes.value.data : [];

      setStats({
        totalOrders: orders.length,
        totalProducts: products.length,
        totalRevenue: orders.reduce((sum: number, o: any) => sum + (o.totalAmount || 0), 0),
        pendingOrders: orders.filter((o: any) => o.status === 'PENDING').length,
      });
    } catch {
      // API not connected yet
    }
  };

  return (
    <div>
      <div className="stats-grid">
        <div className="stat-card">
          <div className="stat-label">Ventas Totales</div>
          <div className="stat-value">${stats.totalRevenue.toLocaleString('es-MX')}</div>
          <div className="stat-change up">+12% este mes</div>
        </div>
        <div className="stat-card">
          <div className="stat-label">Pedidos</div>
          <div className="stat-value">{stats.totalOrders}</div>
          <div className="stat-change up">+8% este mes</div>
        </div>
        <div className="stat-card">
          <div className="stat-label">Productos</div>
          <div className="stat-value">{stats.totalProducts}</div>
          <div className="stat-change up">Activos en tienda</div>
        </div>
        <div className="stat-card">
          <div className="stat-label">Pedidos Pendientes</div>
          <div className="stat-value">{stats.pendingOrders}</div>
          <div className="stat-change down">Requieren atención</div>
        </div>
      </div>

      <div className="card">
        <div className="card-header">
          <h3>Actividad Reciente</h3>
        </div>
        <div className="empty-state">
          <h4>Bienvenido a Seller Hub</h4>
          <p>Tu panel de vendedor está listo. Agrega productos y empieza a vender en CompraGo.</p>
        </div>
      </div>
    </div>
  );
}
