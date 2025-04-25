import axios from "axios";

const API_URL = "http://localhost:8083/auth";

const axiosInstance = axios.create({
  baseURL: API_URL,
});

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  id: string;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  accessToken: string;
  refreshToken: string;
}

export interface RegistrationRequest {
  fullName: string;
  email: string;
  password: string;
  isAdmin: boolean;
}

class AuthService {
  setAccessToken(token: string): void {
    localStorage.setItem("access-token", token);
  }

  getAccessToken(): string | null {
    return localStorage.getItem("access-token");
  }

  setRefreshToken(token: string): void {
    localStorage.setItem("refresh-token", token);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem("refresh-token");
  }

  async register(userRegistrationInfo: RegistrationRequest): Promise<object> {
    try {
      const response = await axiosInstance.post(
        "/register",
        userRegistrationInfo
      );
      return response.data;
    } catch (error) {
      console.error("Error registering user:", error);
      throw error;
    }
  }

  async login(userLoginInfo: LoginRequest): Promise<LoginResponse> {
    try {
      const response = await axiosInstance.post<LoginResponse>(
        "/token",
        userLoginInfo
      );
      const { accessToken } = response.data;

      this.setAccessToken(accessToken); // Store token after login
      return response.data;
    } catch (error) {
      console.error("Error during login:", error);
      throw error;
    }
  }

  async logout(): Promise<void> {
    try {
      await axiosInstance.post("/logout");
      localStorage.removeItem("access-token"); // Clear token on logout
    } catch (error) {
      console.error("Error during logout:", error);
      throw error;
    }
  }
}

export default new AuthService();
