// ─── Common Response Types ───────────────────────────────────────────────────
export interface Result<T> {
  code: number
  message: string
  data: T
}

export interface PageResult<T> {
  records: T[]
  total: number
  page: number
  pageSize: number
}

// ─── Auth ────────────────────────────────────────────────────────────────────
export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  tokenType: string
  user: UserInfo
}

// ─── User ────────────────────────────────────────────────────────────────────
export interface UserInfo {
  id: number
  username: string
  nickname: string
  email: string
  phone: string
  avatar: string
  status: number
  roles: string[]
  createdAt: string
  updatedAt: string
}

export interface UserCreateRequest {
  username: string
  password: string
  nickname: string
  email?: string
  phone?: string
  status?: number
  roles?: string[]
}

export interface UserUpdateRequest {
  nickname: string
  email?: string
  phone?: string
  status?: number
  roles?: string[]
}

export interface UserQuery {
  page?: number
  pageSize?: number
  keyword?: string
  status?: number
}

// ─── Role ────────────────────────────────────────────────────────────────────
export interface Role {
  id: number
  name: string
  code: string
  description: string
  status: number
  createdAt: string
  updatedAt: string
}

// ─── Dashboard ───────────────────────────────────────────────────────────────
export interface DashboardStats {
  totalUsers: number
  activeUsers: number
  disabledUsers: number
  userTrend: Array<{ date: string; count: number }>
}
