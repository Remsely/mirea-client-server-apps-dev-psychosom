FROM oven/bun:latest AS base

WORKDIR /app

COPY package.json bun.lockb ./

RUN bun install

COPY . .

ARG NEXT_PUBLIC_WS_URL
ARG NEXT_PUBLIC_REST_URL
ENV NEXT_PUBLIC_WS_URL=$NEXT_PUBLIC_WS_URL
ENV NEXT_PUBLIC_REST_URL=$NEXT_PUBLIC_REST_URL

RUN bun next build

FROM oven/bun:slim AS runner

WORKDIR /app

COPY --from=base /app ./

EXPOSE 3000

CMD ["bun", "next", "start"]
