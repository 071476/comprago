import { useEffect, useState } from 'react';
import { productsApi } from '../services/api';

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  category: string;
  active: boolean;
}

export default function Products() {
  const [products, setProducts] = useState<Product[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState<Product | null>(null);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [category, setCategory] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => { loadProducts(); }, []);

  const loadProducts = async () => {
    try {
      const res = await productsApi.list();
      setProducts(res.data);
    } catch {
      setProducts([]);
    } finally {
      setLoading(false);
    }
  };

  const openCreate = () => {
    setEditing(null);
    setName('');
    setDescription('');
    setPrice('');
    setCategory('');
    setShowModal(true);
  };

  const openEdit = (p: Product) => {
    setEditing(p);
    setName(p.name);
    setDescription(p.description);
    setPrice(String(p.price));
    setCategory(p.category || '');
    setShowModal(true);
  };

  const save = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const data = { name, description, price: Number(price), category };
      if (editing) {
        await productsApi.update(editing.id, data as any);
      } else {
        await productsApi.create(data as any);
      }
      setShowModal(false);
      loadProducts();
    } catch {
      alert('Error al guardar');
    }
  };

  const deleteProduct = async (id: number) => {
    if (!confirm('Eliminar este producto?')) return;
    try {
      await productsApi.delete(id);
      loadProducts();
    } catch {
      alert('Error al eliminar');
    }
  };

  if (loading) {
    return <div className="card"><p>Cargando productos...</p></div>;
  }

  return (
    <div>
      <div className="card">
        <div className="card-header">
          <h3>Mis Productos ({products.length})</h3>
          <button className="btn-sm" onClick={openCreate}>+ Nuevo Producto</button>
        </div>
        {products.length === 0 ? (
          <div className="empty-state">
            <h4>Sin productos aun</h4>
            <p>Crea tu primer producto para empezar a vender en CompraGo.</p>
            <button className="btn-sm" onClick={openCreate}>+ Crear Producto</button>
          </div>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Producto</th>
                <th>Categoria</th>
                <th>Precio</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {products.map((p) => (
                <tr key={p.id}>
                  <td>
                    <strong>{p.name}</strong>
                    <br />
                    <small style={{ color: 'var(--text-muted)' }}>{p.description}</small>
                  </td>
                  <td>{p.category || '-'}</td>
                  <td>${p.price.toLocaleString('es-MX')}</td>
                  <td>
                    <span className={`badge ${p.active ? 'badge-green' : 'badge-red'}`}>
                      {p.active ? 'Activo' : 'Inactivo'}
                    </span>
                  </td>
                  <td>
                    <button className="btn-sm" style={{ marginRight: 8 }} onClick={() => openEdit(p)}>Editar</button>
                    <button className="btn-sm" style={{ background: 'var(--danger)' }} onClick={() => deleteProduct(p.id)}>Eliminar</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {showModal && (
        <div id="modal-overlay" style={{ display: 'flex' }} onClick={() => setShowModal(false)}>
          <div id="modal-box" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h3>{editing ? 'Editar Producto' : 'Nuevo Producto'}</h3>
              <button className="btn-close" onClick={() => setShowModal(false)}>x</button>
            </div>
            <form onSubmit={save}>
              <div className="form-group">
                <label>Nombre</label>
                <input type="text" value={name} onChange={e => setName(e.target.value)} required />
              </div>
              <div className="form-group">
                <label>Descripcion</label>
                <input type="text" value={description} onChange={e => setDescription(e.target.value)} required />
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label>Precio (MXN)</label>
                  <input type="number" step="0.01" value={price} onChange={e => setPrice(e.target.value)} required />
                </div>
                <div className="form-group">
                  <label>Categoria</label>
                  <select value={category} onChange={e => setCategory(e.target.value)}>
                    <option value="">Seleccionar</option>
                    <option value="CARNES">Carnes</option>
                    <option value="VERDURAS">Verduras</option>
                    <option value="FRUTAS">Frutas</option>
                    <option value="LACTEOS">Lacteos</option>
                    <option value="BEBIDAS">Bebidas</option>
                    <option value="ABARROTES">Abarrotes</option>
                    <option value="LIMPIEZA">Limpieza</option>
                    <option value="OTROS">Otros</option>
                  </select>
                </div>
              </div>
              <div className="modal-actions">
                <button type="button" className="btn-secondary" onClick={() => setShowModal(false)}>Cancelar</button>
                <button type="submit" className="btn-sm">{editing ? 'Guardar' : 'Crear'}</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
