import React from 'react'
import {
  Box,
  Typography,
  Button,
  List,
  ListItem,
  ListItemText,
} from '@mui/material'

export default function HomePage() {
  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        bgcolor:
          'linear-gradient(135deg, #e0eafc 0%, #b6c6d9 60%, #b9e6ff 100%)',
        py: 8,
      }}
    >
      <Typography variant="h3" fontWeight={800} color="#1976d2" mb={4}>
        Todo App - Home
      </Typography>
      <List
        sx={{
          width: 500,
          bgcolor: 'rgba(255,255,255,0.97)',
          borderRadius: 4,
          boxShadow: '0 4px 16px 0 #1976d2aa',
          p: 3,
        }}
      >
        <ListItem>
          <ListItemText primary="✅ Thêm việc cần làm" />
        </ListItem>
        <ListItem>
          <ListItemText primary="📝 Sửa việc" />
        </ListItem>
        <ListItem>
          <ListItemText primary="🗑️ Xóa việc" />
        </ListItem>
        <ListItem>
          <ListItemText primary="☑️ Đánh dấu hoàn thành" />
        </ListItem>
        <ListItem>
          <ListItemText primary="📅 Gắn ngày hoặc deadline" />
        </ListItem>
      </List>
      <Box sx={{ mt: 4 }}>
        <Button variant="contained" color="primary" sx={{ mr: 2 }}>
          Thêm việc mới
        </Button>
        <Button variant="outlined" color="primary">
          Đăng xuất
        </Button>
      </Box>
    </Box>
  )
}
