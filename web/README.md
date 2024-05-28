# dse-webapp

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) to make the TypeScript language service aware of `.vue` types.

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

## Deploy

1. `docker build -t dse/web-app:latest .`
2. Set environment variables for API Endpoints

### Environment Variables

See `.env` file for reference:

- VITE_URL_API_BEACHCOMB=http://127.0.0.1:4442/beachcomb/
- VITE_URL_API_CONTROL=http://127.0.0.1:4443/control/
- VITE_URL_API_EVENT=http://127.0.0.1:4449/
- VITE_URL_API_INVENTORY=http://127.0.0.1:4444/inventory
