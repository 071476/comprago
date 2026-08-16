import { useEffect, useState } from 'react';
import { ordersApi } from '../services/api';

export default function Orders() {
  const [orders, setOrders] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    ordersApi.getBySeller(1)
      .then(res => setOrders(res.data))
      .catch(() => setOrders([]))
      .finally(() => setLoading(false));
  }, []);

  const handleStatus = async (id: number, status: string) => {
    try {
      const res = await ordersApi.updateStatus(id, status);
      setOrders(orders.map(o => o.id === id ? res.data : o));
    } catch (e) {
      console.error(e);
    }
  };

  const statusColor = (s: string) => {
    switch (s) {
      case 'PENDING': return 'badge-yellow';
      case 'CONFIRMED': return 'badge-blue';
      case 'SHIPPED': return 'badge-purple';
      case 'DELIVERED': return 'badge-green';
      case 'CANCELLED': return 'badge-red';
      default: return 'badge-gray';
    }
  };

  if (loading) return <div className="card"><p>Cargando pedidos...</p></div>;

  return (
    <div className="card">
      <div className="card-header">
        <h3>Pedidos ({orders.length})</h3>
      </div>
      {orders.length === 0 ? (
        <div className="empty-state">
          <h4>Sin pedidos aun</h4>
          <p>Cuando un cliente haga un pedido, aparecera aqui.</p>
        </div>
      ) : (
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Producto</th>
              <th>Cantidad</th>
              <th>Total</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((o: any) => (
              <tr key={o.id}>
                <td>#{o.id}</td>
                <td>{o.productName}</td>
                <td>{o.quantity}</td>
                <td>$ {o.totalAmount}</td>
                <td><span className={`badge ${statusColor(o.status)}`}>{o.status}</span></td>
                <td>
                  {o.status === 'PENDING' && (
                    <>
                      <button className="btn-sm" onClick={() => handleStatus(o.id, 'CONFIRMED')}>Confirmar</button>
                      <button className="btn-sm btn-red" onClick={() => handleStatus(o.id, 'CANCELLED')}>Cancelar</button>
                    </>
                  )}
                  {o.status === 'CONFIRMED' && (
                    <button className="btn-sm" onClick={() => handleStatus(o.id, 'SHIPPED')}>Enviar</button>
                  )}
                  {o.status === 'SHIPPED' && (
                    <button className="btn-sm btn-green" onClick={() => handleStatus(o.id, 'DELIVERED')}>Entregado</button>
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
