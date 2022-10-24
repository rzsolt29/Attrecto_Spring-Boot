export default {
  ...process.env,
  ...(window.__RUNTIME_ENV__ || {}),
};
