import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080', // Đổi nếu backend chạy port khác
  headers: {
    'Content-Type': 'application/json',
  },
})

export default api
