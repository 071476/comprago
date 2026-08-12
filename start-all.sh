#!/bin/bash
echo "=== Encendiendo CompraGo Backend ==="

services=(
    "auth-service:8080"
    "api-gateway:8081"
    "sellers-service:8082"
    "products-service:8083"
    "inventory-service:8084"
    "search-service:8085"
    "cart-service:8086"
    "orders-service:8087"
    "payments-service:8088"
    "shipping-service:8089"
    "customers-service:8090"
)

for entry in "${services[@]}"; do
    name="${entry%%:*}"
    port="${entry##*:}"
    echo "Encendiendo $name en puerto $port..."
    cd ~/comprago/backend/$name
    nohup ./gradlew bootRun > ~/comprago/logs/$name.log 2>&1 &
    cd ~/comprago
done

echo ""
echo "=== Todos los servicios iniciados ==="
echo "Esperando 60 segundos para que arranquen..."
sleep 60
echo ""
echo "=== Verificando servicios ==="

for entry in "${services[@]}"; do
    name="${entry%%:*}"
    port="${entry##*:}"
    result=$(curl -s --max-time 2 http://localhost:$port/api/health 2>/dev/null || echo '{"status":"DOWN"}')
    if echo "$result" | grep -q "UP"; then
        echo "✅ $name (puerto $port): UP"
    else
        echo "❌ $name (puerto $port): DOWN"
    fi
done
