# Endur Backend

## Configuracao de ambiente local (Spring Profiles)

O backend usa profile `local` por padrao.

- Arquivo base: `src/main/resources/application.yml`
- Configuracao local: `src/main/resources/application-local.yml`

As credenciais do banco sao lidas por variaveis de ambiente:

- `ENDUR_DB_URL`
- `ENDUR_DB_USERNAME`
- `ENDUR_DB_PASSWORD`

Se voce nao definir essas variaveis, o profile `local` usa defaults:

- URL: `jdbc:postgresql://localhost:5432/endur_db`
- usuario: `endur`
- senha: `endur`

## Como rodar local

### 1) Exportar variaveis (macOS/Linux)

```bash
export ENDUR_DB_URL=jdbc:postgresql://localhost:5432/endur_db
export ENDUR_DB_USERNAME=seu_usuario
export ENDUR_DB_PASSWORD=sua_senha
```

### 2) Subir a aplicacao

```bash
./mvnw spring-boot:run
```

Opcional (explicito): 

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

## Fluxo recomendado para desenvolvimento em dupla

Cada dev configura suas proprias variaveis localmente (shell ou IntelliJ), sem commitar segredo.

- Nao versionar arquivos com senha (`.env`, `.env.local`, etc.)
- Versionar apenas exemplos/documentacao
- Manter o mesmo nome de variaveis (`ENDUR_DB_*`) para padronizacao entre a dupla
- Em PR, evitar alterar `application-local.yml` com dados pessoais

## IntelliJ (alternativa ao export)

Em **Run/Debug Configurations** da aplicacao Spring Boot:

- `ENDUR_DB_URL=jdbc:postgresql://localhost:5432/endur_db`
- `ENDUR_DB_USERNAME=seu_usuario`
- `ENDUR_DB_PASSWORD=sua_senha`

Assim cada pessoa roda com seu proprio ambiente sem afetar o repositório.
