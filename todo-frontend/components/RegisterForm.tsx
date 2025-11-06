import React, { useState } from 'react'
import { useRouter } from 'next/navigation'
import { Box, TextField, Button, Typography, Alert } from '@mui/material'
import { Link as MuiLink } from '@mui/material'
import Link from 'next/link'
import api from '../lib/api'

export default function RegisterForm() {
  const router = useRouter()
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [success, setSuccess] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [confirmPassword, setConfirmPassword] = useState('')

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    if (password !== confirmPassword) {
      setError('Mật khẩu xác nhận không khớp')
      return
    }
    setLoading(true)
    try {
      await api.post('/api/accounts/auth/register', {
        username,
        email,
        password,
      })
      setSuccess('Đăng ký thành công!')
      setUsername('')
      setEmail('')
      setPassword('')
      setConfirmPassword('')
      setTimeout(() => {
        router.push('/login')
      }, 1200)
    } catch (err) {
      if (
        err &&
        typeof err === 'object' &&
        'response' in err &&
        err.response &&
        typeof err.response === 'object' &&
        'data' in err.response &&
        err.response.data &&
        typeof err.response.data === 'object' &&
        'message' in err.response.data
      ) {
        setError(
          (err.response.data as { message?: string }).message ||
            'Đăng ký thất bại'
        )
      } else {
        setError('Đăng ký thất bại')
      }
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
        background:
          'linear-gradient(135deg, #e0eafc 0%, #b6c6d9 60%, #b9e6ff 100%)',
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
          Đăng ký tài khoản
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
          label="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          fullWidth
          required
          margin="normal"
          type="email"
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
        <TextField
          label="Xác nhận mật khẩu"
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
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
          {loading ? 'Đang đăng ký...' : 'Đăng ký'}
        </Button>
        <Box
          sx={{
            width: '100%',
            mt: 2,
            display: 'flex',
            justifyContent: 'flex-end',
            alignItems: 'center',
          }}
        >
          <MuiLink
            component={Link}
            href="/login"
            underline="hover"
            color="primary"
            fontSize={15}
            fontWeight={500}
          >
            Đã có tài khoản? Đăng nhập
          </MuiLink>
        </Box>
      </Box>
    </Box>
  )
}
