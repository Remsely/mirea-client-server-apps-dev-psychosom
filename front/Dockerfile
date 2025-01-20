FROM oven/bun:latest as base

WORKDIR /app

COPY package.json bun.lockb ./

RUN bun install

COPY . .

RUN bun next build

FROM oven/bun:slim as runner

WORKDIR /app

COPY --from=base /app ./

EXPOSE 3000

CMD ["bun", "next", "start"]
