import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import { stringify } from 'query-string';
import isEmpty from 'lodash/isEmpty';
import env from 'config/env';

import { NotificationType, showNotification } from 'components/notification.component';

export enum Methods {
  GET = 'GET',
  PUT = 'PUT',
  POST = 'POST',
  DELETE = 'DELETE',
  PATCH = 'PATCH',
}

export enum Status {
  OK = 200,
  BAD_REQUEST = 400,
  UNAUTHORIZED = 401,
  FORBIDDEN = 403,
  NOT_FOUND = 404,
  UNPROCESSABLE_ENTITY = 422,
  INTERNAL_SERVER_ERROR = 500,
  BAD_GATEWAY = 502,
  SERVICE_UNAVAILABLE = 503,
  GATEWAY_TIMEOUT = 504,
}

export interface RequestConfig extends AxiosRequestConfig {
  resource: string;
  method?: Methods;
}

axios.interceptors.request.use((requestConfig) => {
  const token = localStorage.getItem('token');

  if (token) {
    requestConfig.headers = {
      ...requestConfig.headers,
      Authorization: `bearer ${token}`,
    };
  }

  return requestConfig;
});

axios.interceptors.response.use(
  async (response) => response,
  async (error: AxiosError) => {
    if (error.response && error.config) {
      const { status, statusText } = error.response;

      if (status !== Status.UNAUTHORIZED) {
        showNotification({ type: NotificationType.ERROR, content: `${status} - ${statusText}` });
      }

      if (status === Status.UNAUTHORIZED && localStorage.getItem('token')) {
        localStorage.removeItem('token');
        window.history.go(0);
      }
    }

    return Promise.reject(error);
  }
);

interface GenerateUrlSettings {
  baseURL?: string;
  resource?: string;
  params?: Record<string, any>;
}

export function appendParamsToUrl(url: string, params?: Record<string, any>) {
  const query = params && !isEmpty(params) ? `?${stringify(params, { arrayFormat: 'bracket' })}` : '';

  return `${url}${query}`;
}

export function generateUrl({ baseURL = env.REACT_APP_API_URL, resource = '', params }: GenerateUrlSettings = {}) {
  const url = `${baseURL || ''}/${resource}`;

  return appendParamsToUrl(url, params);
}

async function request<T = void>({
  resource,
  method = Methods.GET,
  transformResponse,
  headers,
  data,
  ...requestConfig
}: RequestConfig) {
  const url = generateUrl({ baseURL: requestConfig.baseURL, resource });

  const { data: response } = await axios.request<T>({
    method,
    headers,
    url,
    data,
    ...requestConfig,
  });

  return response;
}

export default request;
