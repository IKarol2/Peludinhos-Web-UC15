# PeludinhosMours-Web-UC15 — Etapa 9 (formatado)
- Código padronizado com cabeçalhos (autor, data, versão) e mensagens PT-BR.
- Handlers de erro + respostas de sucesso em JSON.

## Como rodar
1. Ajuste credenciais do MySQL em `src/main/resources/application.properties`.
2. `mvn spring-boot:run` (ou F6 no NetBeans).
3. Testes: `mvn test` (inclui cenários de sucesso/erro).

## Endpoints
- `GET /api/servicos`
- `GET /api/agendamentos`
- `POST /api/agendamentos` → 201 com `{ "mensagem": "Agendamento criado com sucesso!", "id": X }`
- `POST /api/contato` → 202 com `{ "mensagem": "Mensagem enviada! Em breve entraremos em contato." }`

## Mensagens de erro (PT-BR)
- Validações: 400 com `mensagem` + `erros` (mapa de campo → mensagem).
- JSON inválido: 400 `"Formato de requisição inválido. Envie um JSON válido."`
- Regras de negócio (ex.: IDs inexistentes): 400 com a mensagem específica.
