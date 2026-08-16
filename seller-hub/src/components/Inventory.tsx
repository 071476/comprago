import { useEffect, useState } from 'react';
import { inventoryApi } from '../services/api';

export default function Inventory() {
  const [items, setItems] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    inventoryApi.getBySeller(1)
      .then(res => setItems(res.data))
      .catch(() => setItems([]))
      .finally(() => setLoading(false));
  }, []);

  const handleStock = async (id: number, stock: number) => {
    try {
      const res = await inventoryApi.updateStock(id, stock);
      setItems(items.map(i => i.id === id ? res.data : i));
    } catch (e) {
      console.error(e);
    }
  };

  if (loading) return <div className="card"><p>Cargando inventario...</p></div>;

  return (
    <div className="card">
      <div className="card-header">
        <h3>Inventario ({items.length})</h3>
      </div>
      {items.length === 0 ? (
        <div className="empty-state">
          <h4>Sin productos en inventario</h4>
          <p>Cuando agregues productos, el inventario se creara automaticamente.</p>
        </div>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Producto</th>
              <th>Stock</th>
              <th>Minimo</th>
              <th>Estado</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {items.map((i: any) => (
              <tr key={i.id}>
                <td>{i.productName}</td>
                <td>{i.stock}</td>
                <td>{i.minStock}</td>
                <td>
                  <span className={`badge ${i.lowStock ? 'badge-red' : 'badge-green'}`}>
                    {i.lowStock ? 'Bajo' : 'OK'}
                  </span>
                </td>
                <td>
                  <button className="btn-sm" onClick={() => handleStock(i.id, i.stock + 10)}>+10</button>
                  <button className="btn-sm" onClick={() => handleStock(i.id, i.stock + 50)}>+50</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
