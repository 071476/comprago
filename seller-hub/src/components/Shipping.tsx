import { useEffect, useState } from 'react';
import { shippingApi } from '../services/api';

interface Shipment {
  id: number;
  orderId: number;
  trackingNumber: string;
  carrier: string;
  status: string;
  estimatedDelivery: string;
}

const statusBadge: Record<string, string> = {
  PENDING: 'badge-warning',
  SHIPPED: 'badge-info',
  IN_TRANSIT: 'badge-info',
  DELIVERED: 'badge-success',
};

const statusLabels: Record<string, string> = {
  PENDING: 'Pendiente',
  SHIPPED: 'Enviado',
  IN_TRANSIT: 'En Tránsito',
  DELIVERED: 'Entregado',
};

export default function Shipping() {
  const [shipments, setShipments] = useState<Shipment[]>([]);

  useEffect(() => { loadShipments(); }, []);

  const loadShipments = async () => {
    try {
      const res = await shippingApi.list();
      setShipments(res.data);
    } catch {
      setShipments([]);
    }
  };

  return (
    <div>
      <div className="card">
        <div className="card-header">
          <h3>Envíos ({shipments.length})</h3>
        </div>
        {shipments.length === 0 ? (
          <div className="empty-state">
            <h4>Sin envíos</h4>
            <p>Los envíos de tus pedidos aparecerán aquí.</p>
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Envío</th>
                <th>Pedido</th>
                <th>Transportista</th>
                <th>Guía</th>
                <th>Estado</th>
                <th>Entrega Est.</th>
              </tr>
            </thead>
            <tbody>
              {shipments.map((s) => (
                <tr key={s.id}>
                  <td><strong>#{s.id}</strong></td>
                  <td>Pedido #{s.orderId}</td>
                  <td>{s.carrier}</td>
                  <td style={{ fontFamily: 'DM Mono, monospace' }}>{s.trackingNumber}</td>
                  <td>
                    <span className={`badge ${statusBadge[s.status] || 'badge-info'}`}>
                      {statusLabels[s.status] || s.status}
                    </span>
                  </td>
                  <td>{s.estimatedDelivery ? new Date(s.estimatedDelivery).toLocaleDateString('es-MX') : '—'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
