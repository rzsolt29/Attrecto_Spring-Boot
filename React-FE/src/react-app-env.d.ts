/// <reference types="react-scripts" />

interface Environment {
  REACT_APP_API_URL: string;
}

interface RuntimeEnvironment extends Environment {}

interface Window {
  __RUNTIME_ENV__?: RuntimeEnvironment;
}
