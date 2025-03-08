import axios, { AxiosResponse, InternalAxiosRequestConfig } from "axios";
import AuthService from "./AuthService";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8083:",
  timeout: 5000,
});

axiosInstance.interceptors.request.use(
(config: InternalAxiosRequestConfig): InternalAxiosRequestConfig | Promise<InternalAxiosRequestConfig> => {
    const accessToken = AuthService.getAccessToken();
    console.log('Request Interceptor Triggered');
    console.log('Request Configuration:', config);
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  (error) => {
    console.error("HTTP Error:", error.response || error.message);
    return Promise.reject(error);
  }
);

export default axiosInstance;
