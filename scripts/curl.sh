curl http://localhost:8080/status | jq

curl http://localhost:8080/payments \
  -H "Content-Type: application/json" \
  -d '{"payerId": "123", "payeeId": "456", "amount": 100}'

curl http://localhost:8080/payments/1
