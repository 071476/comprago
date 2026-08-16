import { useEffect, useState } from 'react';
import { shippingApi } from '../services/api';

export default function Shipping() {
  const [shipments, setShipments] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    shippingApi.getBySeller(1)
      .then(res => setShipments(res.data))
      .catch(() => setShipments([]))
      .finally(() => setLoading(false));
  }, []);

  const handleStatus = async (id: number, status: string) => {
    try {
      const res = await shippingApi.updateStatus(id, status);
      setShipments(shipments.map(s => s.id === id ? res.data : s));
    } catch (e) {
      console.error(e);
    }
  };

  const statusColor = (s: string) => {
    switch (s) {
      case 'PENDING': return 'badge-yellow';
      case 'SHIPPED': return 'badge-blue';
      case 'IN_TRANSIT': return 'badge-purple';
      case 'DELIVERED': return 'badge-green';
      default: return 'badge-gray';
    }
  };

  if (loading) return <div className="card"><p>Cargando envios...</p></div>;

  return (
    <div className="card">
      <div className="card-header">
        <h3>Envios ({shipments.length})</h3>
      </div>
      {shipments.length === 0 ? (
        <div className="empty-state">
          <h4>Sin envios aun</h4>
          <p>Cuando confirmes un pedido, se creara un envio aqui.</p>
        </div>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Tracking</th>
              <th>Pedido</th>
              <th>Carrier</th>
              <th>Destino</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {shipments.map((s: any) => (
              <tr key={s.id}>
                <td>{s.trackingNumber}</td>
                <td>#{s.orderId}</td>
                <td>{s.carrier}</td>
                <td>{s.destinationAddress}</td>
                <td><span className={`badge ${statusColor(s.status)}`}>{s.status}</span></td>
                <td>
                  {s.status === 'PENDING' && (
                    <button className="btn-sm" onClick={() => handleStatus(s.id, 'SHIPPED')}>Enviar</button>
                  )}
                  {s.status === 'SHIPPED' && (
                    <button className="btn-sm" onClick={() => handleStatus(s.id, 'IN_TRANSIT')}>En Transito</button>
                  )}
                  {s.status === 'IN_TRANSIT' && (
                    <button className="btn-sm btn-green" onClick={() => handleStatus(s.id, 'DELIVERED')}>Entregado</button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
