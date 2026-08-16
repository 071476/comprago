import { useEffect, useState } from 'react';
import { productsApi, ordersApi, shippingApi } from '../services/api';

export default function Dashboard() {
  const [productCount, setProductCount] = useState(0);
  const [orderCount, setOrderCount] = useState(0);
  const [revenue, setRevenue] = useState(0);
  const [shippingCount, setShippingCount] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    productsApi.list()
      .then(res => setProductCount(res.data.length))
      .catch(() => setProductCount(0));

    ordersApi.getBySeller(1)
      .then(res => {
        setOrderCount(res.data.length);
        const total = res.data.reduce((sum: number, o: any) => sum + parseFloat(o.totalAmount), 0);
        setRevenue(total);
      })
      .catch(() => { setOrderCount(0); setRevenue(0); });

    shippingApi.getBySeller(1)
      .then(res => setShippingCount(res.data.length))
      .catch(() => setShippingCount(0))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      <div className="stats-grid">
        <div className="stat-card">
          <span className="stat-label">Productos</span>
          <span className="stat-value">{loading ? '...' : productCount}</span>
          <span className="stat-sub">activos en tu tienda</span>
        </div>
        <div className="stat-card">
          <span className="stat-label">Pedidos</span>
          <span className="stat-value">{loading ? '...' : orderCount}</span>
          <span className="stat-sub">{orderCount === 0 ? 'sin pedidos aun' : 'pedidos totales'}</span>
        </div>
        <div className="stat-card">
          <span className="stat-label">Ingresos</span>
          <span className="stat-value">{loading ? '...' : `$ ${revenue.toFixed(2)}`}</span>
          <span className="stat-sub">{revenue === 0 ? 'sin ventas aun' : 'ingresos totales'}</span>
        </div>
        <div className="stat-card">
          <span className="stat-label">Envios</span>
          <span className="stat-value">{loading ? '...' : shippingCount}</span>
          <span className="stat-sub">{shippingCount === 0 ? 'sin envios aun' : 'envios activos'}</span>
        </div>
      </div>
      <div className="card" style={{ marginTop: 24 }}>
        <h3>Bienvenido a CompraGo Seller Hub</h3>
        <p style={{ color: 'var(--text-muted)', marginTop: 8 }}>
          Tu plataforma para gestionar tu negocio. Desde aqui puedes administrar productos, pedidos, inventario y envios.
        </p>
        <div className="roadmap-status" style={{ marginTop: 24 }}>
          <h4>Estado del Roadmap</h4>
          <div style={{ marginTop: 12 }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 8 }}>
              <span className="badge badge-green">Activo</span>
              <span>Products Service</span>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 8 }}>
              <span className="badge badge-green">Activo</span>
              <span>Orders Service</span>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 8 }}>
              <span className="badge badge-green">Activo</span>
              <span>Inventory Service</span>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 8 }}>
              <span className="badge badge-green">Activo</span>
              <span>Shipping Service</span>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 8 }}>
              <span className="badge badge-yellow">Proximamente</span>
              <span>Sellers + Stores</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
