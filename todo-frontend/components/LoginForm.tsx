import React, { useState } from 'react'
import {
  Box,
  TextField,
  Button,
  Typography,
  Alert,
  Link as MuiLink,
} from '@mui/material'
import Link from 'next/link'
import api from '../lib/api'

type Account = {
  id: number
  username: string
  email: string
  passwordHash: string
}

export default function LoginForm() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const [loading, setLoading] = useState(false)

  // Lưu ý: backend hiện chưa có API login thực sự, chỉ demo gọi API get account
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    setLoading(true)
    try {
      // Giả lập login: tìm account theo username (hoặc email)
      const resp = await api.post<Account[]>('/api/accounts/all')
      const accounts: Account[] = Array.isArray(resp.data) ? resp.data : []
      const found = accounts.find(
        (acc) => acc.username === username && acc.passwordHash === password
      )
      if (found) {
        setSuccess('Đăng nhập thành công!')
        // TODO: lưu session/token nếu backend có
      } else {
        setError('Sai username hoặc password')
      }
    } catch (err) {
      setError('Đăng nhập thất bại')
    } finally {
      setLoading(false)
    }
  }
  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        background: 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)',
      }}
    >
      <Box
        component="form"
        onSubmit={handleSubmit}
        sx={{
          width: 520,
          bgcolor: 'rgba(255,255,255,0.97)',
          p: 6,
          borderRadius: 6,
          boxShadow: '0 8px 32px 0 rgba(31,38,135,0.37)',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
        }}
      >
        <Typography
          variant="h4"
          mb={3}
          fontWeight={800}
          color="#1976d2"
          letterSpacing={1}
        >
          Todo App
        </Typography>
        {success && (
          <Alert severity="success" sx={{ width: '100%', mb: 1 }}>
            {success}
          </Alert>
        )}
        {error && (
          <Alert severity="error" sx={{ width: '100%', mb: 1 }}>
            {error}
          </Alert>
        )}
        <TextField
          label="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          fullWidth
          required
          margin="normal"
          sx={{ background: '#e3eafc', borderRadius: 2, fontSize: 18, mb: 2 }}
          InputProps={{ style: { fontSize: 18, fontWeight: 500 } }}
          InputLabelProps={{ style: { fontSize: 16 } }}
        />
        <TextField
          label="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          fullWidth
          required
          margin="normal"
          type="password"
          sx={{ background: '#e3eafc', borderRadius: 2, fontSize: 18, mb: 2 }}
          InputProps={{ style: { fontSize: 18, fontWeight: 500 } }}
          InputLabelProps={{ style: { fontSize: 16 } }}
        />
        <Button
          type="submit"
          variant="contained"
          fullWidth
          disabled={loading}
          sx={{
            mt: 2,
            fontWeight: 700,
            fontSize: 18,
            py: 1.5,
            boxShadow: '0 4px 16px 0 #1976d2aa',
            background: 'linear-gradient(90deg, #1976d2 60%, #2980b9 100%)',
          }}
        >
          {loading ? 'Đang đăng nhập...' : 'Đăng nhập'}
        </Button>
        <Box
          sx={{
            width: '100%',
            mt: 2,
            display: 'flex',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
          <MuiLink
            component={Link}
            href="#"
            underline="hover"
            color="primary"
            fontSize={15}
            fontWeight={500}
          >
            Quên mật khẩu?
          </MuiLink>
          <MuiLink
            component={Link}
            href="/register"
            underline="hover"
            color="primary"
            fontSize={15}
            fontWeight={500}
          >
            Đăng ký
          </MuiLink>
        </Box>
      </Box>
    </Box>
  )
}
