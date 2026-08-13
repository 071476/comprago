import { useEffect, useState } from 'react';
import { inventoryApi } from '../services/api';

interface InventoryItem {
  id: number;
  productId: number;
  quantity: number;
  reserved: number;
  available: number;
}

export default function Inventory() {
  const [items, setItems] = useState<InventoryItem[]>([]);
  const [editId, setEditId] = useState<number | null>(null);
  const [newQty, setNewQty] = useState('');

  useEffect(() => { loadInventory(); }, []);

  const loadInventory = async () => {
    try {
      const res = await inventoryApi.list();
      setItems(res.data);
    } catch {
      setItems([]);
    }
  };

  const updateQty = async (id: number) => {
    try {
      await inventoryApi.update(id, Number(newQty));
      setEditId(null);
      setNewQty('');
      loadInventory();
    } catch {
      alert('Error al actualizar');
    }
  };

  return (
    <div>
      <div className="card">
        <div className="card-header">
          <h3>Inventario ({items.length})</h3>
        </div>
        {items.length === 0 ? (
          <div className="empty-state">
            <h4>Sin inventario</h4>
            <p>El inventario de tus productos aparecerá aquí.</p>
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Producto</th>
                <th>Stock</th>
                <th>Reservado</th>
                <th>Disponible</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {items.map((item) => (
                <tr key={item.id}>
                  <td><strong>Producto #{item.productId}</strong></td>
                  <td>
                    {editId === item.id ? (
                      <input type="number" value={newQty} onChange={e => setNewQty(e.target.value)}
                        style={{ width: 80, padding: '0.4rem', background: 'var(--surface-2)',
                          border: '1px solid var(--border)', borderRadius: 6, color: 'var(--text)' }} />
                    ) : item.quantity}
                  </td>
                  <td>{item.reserved}</td>
                  <td>
                    <span className={`badge ${item.available > 0 ? 'badge-success' : 'badge-danger'}`}>
                      {item.available}
                    </span>
                  </td>
                  <td>
                    {editId === item.id ? (
                      <>
                        <button className="btn-sm" style={{ marginRight: 8 }} onClick={() => updateQty(item.id)}>Guardar</button>
                        <button className="btn-secondary" onClick={() => setEditId(null)}>Cancelar</button>
                      </>
                    ) : (
                      <button className="btn-sm" onClick={() => { setEditId(item.id); setNewQty(String(item.quantity)); }}>
                        Editar Stock
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
