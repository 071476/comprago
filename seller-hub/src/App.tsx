import { useState, useEffect } from 'react';
import { authApi } from './services/api';
import Dashboard from './components/Dashboard';
import Products from './components/Products';
import Orders from './components/Orders';
import Inventory from './components/Inventory';
import Shipping from './components/Shipping';
import Analytics from './components/Analytics';
import AIAssistant from './components/AIAssistant';
import './index.css';

interface User {
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

type Page = 'dashboard' | 'products' | 'orders' | 'inventory' | 'shipping' | 'analytics' | 'ai';

const pageTitles: Record<Page, string> = {
  dashboard: 'Dashboard',
  products: 'Productos',
  orders: 'Pedidos',
  inventory: 'Inventario',
  shipping: 'Envíos',
  analytics: 'Analytics',
  ai: 'IA Asistente',
};

const pages: Page[] = ['dashboard', 'products', 'orders', 'inventory', 'shipping', 'analytics', 'ai'];

export default function App() {
  const [isAuth, setIsAuth] = useState(false);
  const [activeTab, setActiveTab] = useState<'login' | 'register'>('login');
  const [activePage, setActivePage] = useState<Page>('dashboard');
  const [user, setUser] = useState<User | null>(null);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [loginError, setLoginError] = useState('');

  const [regFirst, setRegFirst] = useState('');
  const [regLast, setRegLast] = useState('');
  const [regEmail, setRegEmail] = useState('');
  const [regPassword, setRegPassword] = useState('');
  const [regError, setRegError] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    const saved = localStorage.getItem('user');
    if (token && saved) {
      setUser(JSON.parse(saved));
      setIsAuth(true);
    }
  }, []);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoginError('');
    try {
      const res = await authApi.login(loginEmail, loginPassword);
      const data = res.data;
      const userData: User = {
        email: data.email,
        firstName: data.firstName || loginEmail.split('@')[0],
        lastName: data.lastName || '',
        role: data.role,
      };
      localStorage.setItem('token', data.token);
      localStorage.setItem('user', JSON.stringify(userData));
      setUser(userData);
      setIsAuth(true);
    } catch {
      setLoginError('Credenciales incorrectas');
    }
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setRegError('');
    try {
      const res = await authApi.register({
        email: regEmail,
        password: regPassword,
        firstName: regFirst,
        lastName: regLast,
      });
      const data = res.data;
      const userData: User = {
        email: data.email || regEmail,
        firstName: data.firstName || regFirst,
        lastName: data.lastName || regLast,
        role: data.role || 'SELLER',
      };
      localStorage.setItem('token', data.token);
      localStorage.setItem('user', JSON.stringify(userData));
      setUser(userData);
      setIsAuth(true);
    } catch (err: any) {
      if (err.response?.data?.message) {
        setRegError(err.response.data.message);
      } else {
        setRegError('Error al crear la cuenta');
      }
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    setIsAuth(false);
    setActivePage('dashboard');
  };

  const today = new Date().toLocaleDateString('es-MX', {
    weekday: 'long', year: 'numeric', month: 'long', day: 'numeric',
  });

  if (!isAuth) {
    return (
      <div id="auth-screen">
        <div className="auth-left">
          <div className="auth-brand">
            <div className="brand-icon">CG</div>
            <h1>CompraGo</h1>
            <h2>Seller Hub</h2>
            <p>Gestiona tu tienda, productos, pedidos y envíos desde un solo lugar.</p>
          </div>
        </div>
        <div className="auth-right">
          <div className="auth-form-wrapper">
            <div className="auth-tabs">
              <button className={`auth-tab ${activeTab === 'login' ? 'active' : ''}`} onClick={() => setActiveTab('login')}>
                Iniciar Sesión
              </button>
              <button className={`auth-tab ${activeTab === 'register' ? 'active' : ''}`} onClick={() => setActiveTab('register')}>
                Registrarse
              </button>
            </div>

            <form className={`auth-form ${activeTab === 'login' ? 'active' : ''}`} onSubmit={handleLogin}>
              <div className="form-group">
                <label>Email</label>
                <input type="email" placeholder="tu@email.com" value={loginEmail} onChange={e => setLoginEmail(e.target.value)} required />
              </div>
              <div className="form-group">
                <label>Contraseña</label>
                <input type="password" placeholder="••••••••" value={loginPassword} onChange={e => setLoginPassword(e.target.value)} required />
              </div>
              <button type="submit" className="btn-primary">Iniciar Sesión</button>
              <p className="auth-error">{loginError}</p>
            </form>

            <form className={`auth-form ${activeTab === 'register' ? 'active' : ''}`} onSubmit={handleRegister}>
              <div className="form-row">
                <div className="form-group">
                  <label>Nombre</label>
                  <input type="text" placeholder="Juan" value={regFirst} onChange={e => setRegFirst(e.target.value)} required />
                </div>
                <div className="form-group">
                  <label>Apellido</label>
                  <input type="text" placeholder="Pérez" value={regLast} onChange={e => setRegLast(e.target.value)} required />
                </div>
              </div>
              <div className="form-group">
                <label>Email</label>
                <input type="email" placeholder="tu@email.com" value={regEmail} onChange={e => setRegEmail(e.target.value)} required />
              </div>
              <div className="form-group">
                <label>Contraseña</label>
                <input type="password" placeholder="••••••••" value={regPassword} onChange={e => setRegPassword(e.target.value)} required />
              </div>
              <button type="submit" className="btn-primary">Crear Cuenta</button>
              <p className="auth-error">{regError}</p>
            </form>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div id="app-screen">
      <aside id="sidebar" className={sidebarOpen ? 'open' : ''}>
        <div className="sidebar-brand">
          <div className="brand-icon-sm">CG</div>
          <span>CompraGo</span>
        </div>
        <nav className="sidebar-nav">
          {pages.map((page) => (
            <a key={page} className={`nav-item ${activePage === page ? 'active' : ''}`} onClick={() => { setActivePage(page); setSidebarOpen(false); }}>
              <span>{pageTitles[page]}</span>
            </a>
          ))}
        </nav>
        <div className="sidebar-footer">
          <div className="sidebar-user">
            <div className="user-avatar">{user?.firstName?.[0] || 'S'}</div>
            <div className="user-info">
              <span className="user-name">{user?.firstName} {user?.lastName}</span>
              <span className="user-role">Vendedor</span>
            </div>
          </div>
          <button className="btn-logout" onClick={logout}>
            Salir
          </button>
        </div>
      </aside>

      <div id="main">
        <header id="topbar">
          <button className="menu-toggle" onClick={() => setSidebarOpen(!sidebarOpen)}>
            ☰
          </button>
          <h2 id="page-title">{pageTitles[activePage]}</h2>
          <div className="topbar-right">
            <span className="topbar-date">{today}</span>
          </div>
        </header>
        <div id="content">
          {activePage === 'dashboard' && <Dashboard />}
          {activePage === 'products' && <Products />}
          {activePage === 'orders' && <Orders />}
          {activePage === 'inventory' && <Inventory />}
          {activePage === 'shipping' && <Shipping />}
          {activePage === 'analytics' && <Analytics />}
          {activePage === 'ai' && <AIAssistant />}
        </div>
      </div>
    </div>
  );
}
