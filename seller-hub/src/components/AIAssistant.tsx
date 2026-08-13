import { useState } from 'react';

interface Message {
  role: 'user' | 'assistant';
  content: string;
}

export default function AIAssistant() {
  const [messages, setMessages] = useState<Message[]>([
    {
      role: 'assistant',
      content: '¡Hola! Soy tu asistente IA de CompraGo. Puedo ayudarte a:\n\n• Analizar tus ventas\n• Sugerir precios competitivos\n• Mejorar descripciones de productos\n• Recomendar estrategias de marketing\n• Optimizar tu inventario\n\n¿En qué puedo ayudarte hoy?',
    },
  ]);
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);

  const suggestions = [
    '¿Cómo puedo mejorar mis ventas?',
    'Analiza mi inventario',
    'Sugiéreme un precio para mi producto',
    '¿Qué productos son más populares?',
  ];

  const sendMessage = async (text?: string) => {
    const msg = text || input;
    if (!msg.trim()) return;

    const userMsg: Message = { role: 'user', content: msg };
    setMessages(prev => [...prev, userMsg]);
    setInput('');
    setLoading(true);

    setTimeout(() => {
      const responses: Record<string, string> = {
        'ventas': 'Basado en tus datos, te recomiendo:\n\n1. **Ofrecer envío gratis** en pedidos mayores a $500 MXN\n2. **Crear bundles** de productos relacionados\n3. **Mejorar fotos** con fondos blancos y buena iluminación\n4. **Responder rápido** a preguntas de compradores\n\nLos vendedores que aplican estas estrategias ven un incremento del 25-40% en ventas.',
        'inventario': 'Análisis de tu inventario:\n\n• Tienes productos con stock bajo\n• Recomiendo mantener mínimo 10 unidades por producto\n• Los productos más vendidos necesitan reposición semanal\n• Considera eliminar productos sin movimiento en 30 días',
        'precio': 'Para fijar precios competitivos:\n\n1. Investiga precios en la categoría\n2. Calcula tus costos + envío + 3.5% comisión CompraGo\n3. Margen recomendado: 30-50%\n4. Usa precios psicológicos ($499 en vez de $500)\n\n¿De qué producto quieres que te sugiera un precio?',
        'populares': 'Los productos más populares en CompraGo son:\n\n1. Electrónicos y accesorios\n2. Ropa y calzado\n3. Hogar y decoración\n4. Belleza y cuidado personal\n5. Deportes y fitness\n\n¿Quieres que analice tu categoría específica?',
      };

      const lowerMsg = msg.toLowerCase();
      let response = 'Entiendo tu consulta. Como asistente de CompraGo, te sugiero revisar tu dashboard para métricas actualizadas. ¿Hay algo específico en lo que pueda ayudarte?';

      for (const [key, value] of Object.entries(responses)) {
        if (lowerMsg.includes(key)) {
          response = value;
          break;
        }
      }

      setMessages(prev => [...prev, { role: 'assistant', content: response }]);
      setLoading(false);
    }, 1500);
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: 'calc(100vh - 140px)' }}>
      <div className="card" style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
        <div className="card-header">
          <h3>🤖 IA Asistente de CompraGo</h3>
          <span className="badge badge-success">En línea</span>
        </div>

        <div style={{ flex: 1, padding: '1.5rem', overflowY: 'auto' }}>
          {messages.map((msg, i) => (
            <div key={i} style={{
              display: 'flex',
              justifyContent: msg.role === 'user' ? 'flex-end' : 'flex-start',
              marginBottom: '1rem',
            }}>
              <div style={{
                maxWidth: '75%',
                padding: '1rem 1.25rem',
                borderRadius: msg.role === 'user' ? '16px 16px 4px 16px' : '16px 16px 16px 4px',
                background: msg.role === 'user' ? 'var(--accent)' : 'var(--surface-2)',
                color: msg.role === 'user' ? 'var(--bg)' : 'var(--text)',
                whiteSpace: 'pre-line',
                lineHeight: 1.6,
                fontSize: '0.9rem',
              }}>
                {msg.content}
              </div>
            </div>
          ))}
          {loading && (
            <div style={{ display: 'flex', justifyContent: 'flex-start', marginBottom: '1rem' }}>
              <div style={{
                padding: '1rem 1.25rem',
                borderRadius: '16px 16px 16px 4px',
                background: 'var(--surface-2)',
                color: 'var(--text-muted)',
              }}>
                Pensando...
              </div>
            </div>
          )}
        </div>

        {messages.length <= 1 && (
          <div style={{ padding: '0 1.5rem 1rem', display: 'flex', flexWrap: 'wrap', gap: '0.5rem' }}>
            {suggestions.map((s, i) => (
              <button key={i} onClick={() => sendMessage(s)} style={{
                padding: '0.5rem 1rem',
                background: 'var(--surface-3)',
                border: '1px solid var(--border)',
                borderRadius: '100px',
                color: 'var(--text-muted)',
                fontFamily: 'inherit',
                fontSize: '0.8rem',
                cursor: 'pointer',
                transition: 'all 0.2s ease',
              }}>
                {s}
              </button>
            ))}
          </div>
        )}

        <div style={{
          padding: '1rem 1.5rem',
          borderTop: '1px solid var(--border)',
          display: 'flex',
          gap: '0.75rem',
        }}>
          <input
            type="text"
            value={input}
            onChange={e => setInput(e.target.value)}
            onKeyDown={e => e.key === 'Enter' && sendMessage()}
            placeholder="Escribe tu pregunta..."
            style={{
              flex: 1,
              padding: '0.875rem 1rem',
              background: 'var(--surface-2)',
              border: '1px solid var(--border)',
              borderRadius: 'var(--radius-sm)',
              color: 'var(--text)',
              fontFamily: 'inherit',
              fontSize: '0.9rem',
              outline: 'none',
            }}
          />
          <button className="btn-sm" onClick={() => sendMessage()} disabled={loading}>
            Enviar
          </button>
        </div>
      </div>
    </div>
  );
}
