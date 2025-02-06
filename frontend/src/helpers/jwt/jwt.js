import { jwtDecode } from 'jwt-decode'

export const getRoleFromToken = (token) => {
  try {
    if (!token) return null
    const decoded = jwtDecode(token)
    return decoded.role || null
  } catch (error) {
    console.error('Invalid token:', error)
    return null
  }
};
