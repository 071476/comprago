import { useEffect, useState } from 'react';
import { ordersApi } from '../services/api';

interface Order {
  id: number;
  buyerId: number;
  totalAmount: number;
  status: string;
  createdAt: string;
}

const statusBadge: Record<string, string> = {
  PENDING: 'badge-warning',
  PAID: 'badge-info',
  SHIPPED: 'badge-info',
  DELIVERED: 'badge-success',
  CANCELLED: 'badge-danger',
};

const statusLabels: Record<string, string> = {
  PENDING: 'Pendiente',
  PAID: 'Pagado',
  SHIPPED: 'Enviado',
  DELIVERED: 'Entregado',
  CANCELLED: 'Cancelado',
};

export default function Orders() {
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => { loadOrders(); }, []);

  const loadOrders = async () => {
    try {
      const res = await ordersApi.list();
      setOrders(res.data);
    } catch {
      setOrders([]);
    }
  };

  const updateStatus = async (id: number, status: string) => {
    try {
      await ordersApi.updateStatus(id, status);
      loadOrders();
    } catch {
      alert('Error al actualizar');
    }
  };

  return (
    <div>
      <div className="card">
        <div className="card-header">
          <h3>Pedidos ({orders.length})</h3>
        </div>
        {orders.length === 0 ? (
          <div className="empty-state">
            <h4>Sin pedidos aún</h4>
            <p>Los pedidos de tus clientes aparecerán aquí.</p>
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Pedido</th>
                <th>Comprador</th>
                <th>Total</th>
                <th>Estado</th>
                <th>Fecha</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {orders.map((o) => (
                <tr key={o.id}>
                  <td><strong>#{o.id}</strong></td>
                  <td>Comprador #{o.buyerId}</td>
                  <td>${o.totalAmount.toLocaleString('es-MX')}</td>
                  <td>
                    <span className={`badge ${statusBadge[o.status] || 'badge-info'}`}>
                      {statusLabels[o.status] || o.status}
                    </span>
                  </td>
                  <td>{new Date(o.createdAt).toLocaleDateString('es-MX')}</td>
                  <td>
                    {o.status === 'PAID' && (
                      <button className="btn-sm" onClick={() => updateStatus(o.id, 'SHIPPED')}>
                        Marcar Enviado
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
