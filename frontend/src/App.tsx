import { Routes, Route, Navigate } from 'react-router-dom'
import AdminLayout from '@/layouts/AdminLayout'
import LoginPage from '@/pages/Login'
import DashboardPage from '@/pages/Dashboard'
import UserManagementPage from '@/pages/UserManagement'
import RoleManagementPage from '@/pages/RoleManagement'
import ProfilePage from '@/pages/Profile'
import NotFoundPage from '@/pages/NotFound'
import ProtectedRoute from '@/components/ProtectedRoute'

export default function App() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <AdminLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/dashboard" replace />} />
        <Route path="dashboard"        element={<DashboardPage />} />
        <Route path="users"            element={<UserManagementPage />} />
        <Route path="roles"            element={<RoleManagementPage />} />
        <Route path="profile"          element={<ProfilePage />} />
      </Route>
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  )
}
