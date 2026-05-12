import 'dotenv/config';
import { EventEmitter } from 'eventemitter3';
import { z } from 'zod';

/**
 * OpenRouter Agent - Headless service
 * Connects to OpenRouter API and manages agent lifecycle
 */

// Health check server
import http from 'http';

const PORT = process.env.PORT || 3000;

// Simple in-memory health state
let isHealthy = true;
let startTime = Date.now();

// Create health check server
const server = http.createServer((req, res) => {
  if (req.url === '/health' && req.method === 'GET') {
    const uptime = Date.now() - startTime;
    res.writeHead(isHealthy ? 200 : 503, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({
      status: isHealthy ? 'healthy' : 'unhealthy',
      timestamp: new Date().toISOString(),
      uptime: `${Math.floor(uptime / 1000)}s`
    }));
  } else {
    res.writeHead(404, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ error: 'Not found' }));
  }
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`🚀 OpenRouter Agent started on port ${PORT}`);
  console.log(`📡 Health check available at http://0.0.0.0:${PORT}/health`);
});

// Graceful shutdown
process.on('SIGTERM', () => {
  console.log('⏸️ SIGTERM received, shutting down gracefully...');
  isHealthy = false;
  server.close(() => {
    console.log('✅ Server closed');
    process.exit(0);
  });
});

process.on('SIGINT', () => {
  console.log('⏸️ SIGINT received, shutting down gracefully...');
  isHealthy = false;
  server.close(() => {
    console.log('✅ Server closed');
    process.exit(0);
  });
});

// Handle unhandled errors
process.on('unhandledRejection', (reason, promise) => {
  console.error('❌ Unhandled Rejection at:', promise, 'reason:', reason);
  isHealthy = false;
});

console.log('🌟 A.U.R.A.K.A.I OpenRouter Agent initialized');
console.log(`📝 Environment: ${process.env.NODE_ENV || 'development'}`);

